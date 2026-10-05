package com.joaoanunciacaodev.scoreboardapi.leaderboard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateLeaderboardRequest(

        @NotBlank
        @Size(max = 120)
        String name,

        @NotBlank
        @Size(max = 120)
        String slug,

        @NotNull
        ValueType valueType,

        @NotNull
        SortOrder sortOrder

) {
}