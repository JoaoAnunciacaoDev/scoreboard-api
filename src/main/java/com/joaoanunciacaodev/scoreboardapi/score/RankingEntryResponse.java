package com.joaoanunciacaodev.scoreboardapi.score;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record RankingEntryResponse(
        Long rank,
        String playerId,
        String playerName,
        BigDecimal value,
        OffsetDateTime createdAt
) {

    public static RankingEntryResponse from(ScoreRankingProjection projection) {
        return new RankingEntryResponse(
                projection.getRanking(),
                projection.getPlayerId(),
                projection.getPlayerName(),
                projection.getValue(),
                projection.getCreatedAt().atOffset(ZoneOffset.UTC)
        );
    }

}