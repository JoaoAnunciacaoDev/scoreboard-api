package com.joaoanunciacaodev.scoreboardapi.leaderboard;

import java.time.OffsetDateTime;

public record LeaderboardResponse(
        Long id,
        String name,
        String slug,
        ValueType valueType,
        SortOrder sortOrder,
        OffsetDateTime createdAt
) {

    public static LeaderboardResponse from(Leaderboard leaderboard) {
        return new LeaderboardResponse(
                leaderboard.getId(),
                leaderboard.getName(),
                leaderboard.getSlug(),
                leaderboard.getValueType(),
                leaderboard.getSortOrder(),
                leaderboard.getCreatedAt()
        );
    }

}