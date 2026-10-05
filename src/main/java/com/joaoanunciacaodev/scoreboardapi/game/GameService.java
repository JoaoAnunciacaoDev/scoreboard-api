package com.joaoanunciacaodev.scoreboardapi.game;

import com.joaoanunciacaodev.scoreboardapi.user.User;
import com.joaoanunciacaodev.scoreboardapi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class GameService {

    // por enquanto todo Game é criado em nome desse usuário de teste (seed V2).
    private static final Long TEMP_TEST_USER_ID = 1L;

    private static final String KEY_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public GameResponse create(CreateGameRequest request) {
        User user = userRepository.findById(TEMP_TEST_USER_ID)
                .orElseThrow(() -> new EntityNotFoundException("Usuário de teste não encontrado"));

        Game game = new Game(user, request.name(), request.slug(), generatePublicKey());

        return GameResponse.from(gameRepository.save(game));
    }

    public List<GameResponse> findAll() {
        return gameRepository.findAll()
                .stream()
                .map(GameResponse::from)
                .toList();
    }

    public GameResponse findById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado: " + id));

        return GameResponse.from(game);
    }

    private String generatePublicKey() {
        StringBuilder sb = new StringBuilder("pub_");
        for (int i = 0; i < 32; i++) {
            sb.append(KEY_CHARS.charAt(RANDOM.nextInt(KEY_CHARS.length())));
        }
        return sb.toString();
    }

}