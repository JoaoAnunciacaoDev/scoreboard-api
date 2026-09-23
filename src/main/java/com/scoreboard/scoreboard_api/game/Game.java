package com.scoreboard.scoreboard_api.game;

import com.scoreboard.scoreboard_api.user.User;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String slug;

    @Column(name = "public_key", nullable = false, unique = true, length = 64)
    private String publicKey;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Game() {
    }

    public Game(User user, String name, String slug, String publicKey) {
        this.user = user;
        this.name = name;
        this.slug = slug;
        this.publicKey = publicKey;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}