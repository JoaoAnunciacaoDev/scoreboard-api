package com.joaoanunciacaodev.scoreboardapi.score;

import com.joaoanunciacaodev.scoreboardapi.leaderboard.Leaderboard;
import com.joaoanunciacaodev.scoreboardapi.leaderboard.LeaderboardRepository;
import com.joaoanunciacaodev.scoreboardapi.leaderboard.SortOrder;
import com.joaoanunciacaodev.scoreboardapi.leaderboard.ValueType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 1000;

    private final ScoreRepository scoreRepository;
    private final LeaderboardRepository leaderboardRepository;

    public ScoreService(ScoreRepository scoreRepository, LeaderboardRepository leaderboardRepository) {
        this.scoreRepository = scoreRepository;
        this.leaderboardRepository = leaderboardRepository;
    }

    public ScoreResponse submit(Long leaderboardId, SubmitScoreRequest request) {
        Leaderboard leaderboard = leaderboardRepository.findById(leaderboardId)
                .orElseThrow(() -> new EntityNotFoundException("Leaderboard não encontrada: " + leaderboardId));

        validateValue(leaderboard.getValueType(), request.value());

        Score score = new Score(leaderboard, request.playerId(), request.playerName(), request.value());

        return ScoreResponse.from(scoreRepository.save(score));
    }

    public List<RankingEntryResponse> getRanking(Long leaderboardId, Integer requestedLimit) {
        Leaderboard leaderboard = leaderboardRepository.findById(leaderboardId)
                .orElseThrow(() -> new EntityNotFoundException("Leaderboard não encontrada: " + leaderboardId));

        int limit = resolveLimit(requestedLimit);

        List<ScoreRankingProjection> ranking = leaderboard.getSortOrder() == SortOrder.DESC
                ? scoreRepository.findRankingDesc(leaderboardId, limit)
                : scoreRepository.findRankingAsc(leaderboardId, limit);

        return ranking.stream()
                .map(RankingEntryResponse::from)
                .toList();
    }

    private void validateValue(ValueType valueType, BigDecimal value) {
        boolean hasDecimals = value.stripTrailingZeros().scale() > 0;

        switch (valueType) {
            case INTEGER -> {
                if (hasDecimals) {
                    throw new IllegalArgumentException("Leaderboard do tipo INTEGER não aceita valores com casas decimais: " + value);
                }
            }
            case DURATION -> {
                if (hasDecimals) {
                    throw new IllegalArgumentException("Leaderboard do tipo DURATION não aceita valores com casas decimais (armazene em ms): " + value);
                }
                if (value.signum() < 0) {
                    throw new IllegalArgumentException("Leaderboard do tipo DURATION não aceita valores negativos: " + value);
                }
            }
            case DECIMAL -> {
                // aceita qualquer valor, inteiro ou fracionário
            }
        }
    }

    public RankingEntryResponse getPlayerRanking(Long leaderboardId, String playerId) {
        Leaderboard leaderboard = leaderboardRepository.findById(leaderboardId)
                .orElseThrow(() -> new EntityNotFoundException("Leaderboard não encontrada: " + leaderboardId));

        Optional<ScoreRankingProjection> projection = leaderboard.getSortOrder() == SortOrder.DESC
                ? scoreRepository.findPlayerRankingDesc(leaderboardId, playerId)
                : scoreRepository.findPlayerRankingAsc(leaderboardId, playerId);

        return projection
                .map(RankingEntryResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Jogador sem score nessa leaderboard: " + playerId));
    }

    private int resolveLimit(Integer requested) {
        if (requested == null || requested <= 0) {
            return DEFAULT_LIMIT;
        }
        return Math.min(requested, MAX_LIMIT);
    }

}