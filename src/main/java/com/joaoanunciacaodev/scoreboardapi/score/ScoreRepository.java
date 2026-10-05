package com.joaoanunciacaodev.scoreboardapi.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = """
            WITH best AS (
                SELECT DISTINCT ON (player_id) player_id, player_name, value, created_at
                FROM scores
                WHERE leaderboard_id = :leaderboardId
                ORDER BY player_id, value DESC, created_at ASC
            )
            SELECT
                player_id   AS playerId,
                player_name AS playerName,
                value       AS value,
                created_at  AS createdAt,
                RANK() OVER (ORDER BY value DESC, created_at ASC) AS ranking
            FROM best
            ORDER BY ranking
            LIMIT :limit
            """, nativeQuery = true)
    List<ScoreRankingProjection> findRankingDesc(
            @Param("leaderboardId") Long leaderboardId, @Param("limit") int limit);

    @Query(value = """
            WITH best AS (
                SELECT DISTINCT ON (player_id) player_id, player_name, value, created_at
                FROM scores
                WHERE leaderboard_id = :leaderboardId
                ORDER BY player_id, value ASC, created_at ASC
            )
            SELECT
                player_id   AS playerId,
                player_name AS playerName,
                value       AS value,
                created_at  AS createdAt,
                RANK() OVER (ORDER BY value ASC, created_at ASC) AS ranking
            FROM best
            ORDER BY ranking
            LIMIT :limit
            """, nativeQuery = true)
    List<ScoreRankingProjection> findRankingAsc(
            @Param("leaderboardId") Long leaderboardId, @Param("limit") int limit);

}