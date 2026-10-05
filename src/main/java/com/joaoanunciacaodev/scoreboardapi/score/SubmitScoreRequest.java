package com.joaoanunciacaodev.scoreboardapi.score;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record SubmitScoreRequest(

        @NotBlank
        @Size(max = 120)
        String playerId,

        @NotBlank
        @Size(max = 120)
        String playerName,

        @NotNull
        BigDecimal value

) {
}