package com.joaoanunciacaodev.scoreboardapi.leaderboard;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/api/games/{gameId}/leaderboards")
    public ResponseEntity<LeaderboardResponse> create(
            @PathVariable Long gameId,
            @Valid @RequestBody CreateLeaderboardRequest request
    ) {
        LeaderboardResponse created = leaderboardService.create(gameId, request);
        return ResponseEntity
                .created(URI.create("/api/games/" + gameId + "/leaderboards/" + created.id()))
                .body(created);
    }

    @GetMapping("/api/games/{gameId}/leaderboards")
    public List<LeaderboardResponse> findAllByGame(@PathVariable Long gameId) {
        return leaderboardService.findAllByGame(gameId);
    }

}