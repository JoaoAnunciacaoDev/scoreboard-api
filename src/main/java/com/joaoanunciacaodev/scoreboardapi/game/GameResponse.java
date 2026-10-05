package com.joaoanunciacaodev.scoreboardapi.game;

import java.time.OffsetDateTime;

public record GameResponse(
        Long id,
        String name,
        String slug,
        String publicKey,
        OffsetDateTime createdAt
) {

    public static GameResponse from(Game game) {
        return new GameResponse(
                game.getId(),
                game.getName(),
                game.getSlug(),
                game.getPublicKey(),
                game.getCreatedAt()
        );
    }

}