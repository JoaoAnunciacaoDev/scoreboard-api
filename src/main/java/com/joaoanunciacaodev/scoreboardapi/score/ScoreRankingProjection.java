package com.joaoanunciacaodev.scoreboardapi.score;

import java.math.BigDecimal;
import java.time.Instant;

public interface ScoreRankingProjection {
    String getPlayerId();
    String getPlayerName();
    BigDecimal getValue();
    Instant getCreatedAt();
    Long getRanking();
}