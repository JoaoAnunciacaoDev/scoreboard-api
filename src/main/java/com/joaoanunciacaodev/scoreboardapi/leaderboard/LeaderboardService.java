package com.joaoanunciacaodev.scoreboardapi.leaderboard;

import com.joaoanunciacaodev.scoreboardapi.game.Game;
import com.joaoanunciacaodev.scoreboardapi.game.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final GameRepository gameRepository;

    public LeaderboardService(LeaderboardRepository leaderboardRepository, GameRepository gameRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.gameRepository = gameRepository;
    }

    public LeaderboardResponse create(Long gameId, CreateLeaderboardRequest request) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado: " + gameId));

        Leaderboard leaderboard = new Leaderboard(
                game,
                request.name(),
                request.slug(),
                request.valueType(),
                request.sortOrder()
        );

        return LeaderboardResponse.from(leaderboardRepository.save(leaderboard));
    }

    public List<LeaderboardResponse> findAllByGame(Long gameId) {
        if (!gameRepository.existsById(gameId)) {
            throw new EntityNotFoundException("Jogo não encontrado: " + gameId);
        }

        return leaderboardRepository.findByGameId(gameId)
                .stream()
                .map(LeaderboardResponse::from)
                .toList();
    }

}