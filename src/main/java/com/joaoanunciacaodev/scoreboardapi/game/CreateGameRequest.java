package com.joaoanunciacaodev.scoreboardapi.game;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGameRequest(

        @NotBlank
        @Size(max = 120)
        String name,

        @NotBlank
        @Size(max = 120)
        String slug

) {
}