package com.joaoanunciacaodev.scoreboardapi.leaderboard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    Optional<Leaderboard> findBySlug(String slug);

    List<Leaderboard> findByGameId(Long gameId);

}
