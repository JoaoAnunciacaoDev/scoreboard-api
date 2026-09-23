CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE games (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL REFERENCES users(id),
                       name VARCHAR(120) NOT NULL,
                       slug VARCHAR(120) NOT NULL,
                       public_key VARCHAR(64) NOT NULL UNIQUE,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       CONSTRAINT uq_games_user_slug UNIQUE (user_id, slug)
);

CREATE TABLE leaderboards (
                              id BIGSERIAL PRIMARY KEY,
                              game_id BIGINT NOT NULL REFERENCES games(id),
                              name VARCHAR(120) NOT NULL,
                              slug VARCHAR(120) NOT NULL,
                              value_type VARCHAR(20) NOT NULL CHECK (value_type IN ('INTEGER', 'DECIMAL', 'DURATION')),
                              sort_order VARCHAR(4) NOT NULL CHECK (sort_order IN ('ASC', 'DESC')),
                              created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                              updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                              CONSTRAINT uq_leaderboards_game_slug UNIQUE (game_id, slug)
);

CREATE TABLE scores (
                        id BIGSERIAL PRIMARY KEY,
                        leaderboard_id BIGINT NOT NULL REFERENCES leaderboards(id),
                        player_id VARCHAR(120) NOT NULL,
                        player_name VARCHAR(120) NOT NULL,
                        value NUMERIC NOT NULL,
                        created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_scores_leaderboard_player_created
    ON scores (leaderboard_id, player_id, created_at);