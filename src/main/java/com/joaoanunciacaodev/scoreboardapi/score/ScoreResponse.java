package com.joaoanunciacaodev.scoreboardapi.score;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ScoreResponse(
        Long id,
        String playerId,
        String playerName,
        BigDecimal value,
        OffsetDateTime createdAt
) {

    public static ScoreResponse from(Score score) {
        return new ScoreResponse(
                score.getId(),
                score.getPlayerId(),
                score.getPlayerName(),
                score.getValue(),
                score.getCreatedAt()
        );
    }

}