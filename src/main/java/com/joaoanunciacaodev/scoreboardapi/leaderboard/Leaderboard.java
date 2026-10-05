package com.joaoanunciacaodev.scoreboardapi.leaderboard;

import com.joaoanunciacaodev.scoreboardapi.game.Game;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "leaderboards")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 20)
    private ValueType valueType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sort_order", nullable = false, length = 4)
    private SortOrder sortOrder;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Leaderboard() {
    }

    public Leaderboard(Game game, String name, String slug,
                       ValueType valueType, SortOrder sortOrder) {
        this.game = game;
        this.name = name;
        this.slug = slug;
        this.valueType = valueType;
        this.sortOrder = sortOrder;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
