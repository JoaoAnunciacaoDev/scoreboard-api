package com.joaoanunciacaodev.scoreboardapi.score;

import com.joaoanunciacaodev.scoreboardapi.leaderboard.Leaderboard;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaderboard_id", nullable = false)
    private Leaderboard leaderboard;

    @Column(name = "player_id", nullable = false, length = 120)
    private String playerId;

    @Column(name = "player_name", nullable = false, length = 120)
    private String playerName;

    @Column(nullable = false, precision = 20, scale = 4)
    private BigDecimal value;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    protected Score() {
    }

    public Score(Leaderboard leaderboard, String playerId, String playerName, BigDecimal value) {
        this.leaderboard = leaderboard;
        this.playerId = playerId;
        this.playerName = playerName;
        this.value = value;
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}