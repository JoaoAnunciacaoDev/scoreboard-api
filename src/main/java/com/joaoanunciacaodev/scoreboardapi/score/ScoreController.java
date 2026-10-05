package com.joaoanunciacaodev.scoreboardapi.score;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/leaderboards/{leaderboardId}/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<ScoreResponse> submit(
            @PathVariable Long leaderboardId,
            @Valid @RequestBody SubmitScoreRequest request
    ) {
        ScoreResponse created = scoreService.submit(leaderboardId, request);
        return ResponseEntity
                .created(URI.create("/v1/leaderboards/" + leaderboardId + "/scores/" + created.id()))
                .body(created);
    }

    @GetMapping
    public List<RankingEntryResponse> getRanking(
            @PathVariable Long leaderboardId,
            @RequestParam(required = false) Integer limit
    ) {
        return scoreService.getRanking(leaderboardId, limit);
    }

    @GetMapping("/{playerId}")
    public RankingEntryResponse getPlayerRanking(
            @PathVariable Long leaderboardId,
            @PathVariable String playerId
    ) {
        return scoreService.getPlayerRanking(leaderboardId, playerId);
    }

}