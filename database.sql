/* Users */

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(255) NOT NULL,
    status        VARCHAR(255),
    password      VARCHAR(255) NOT NULL,
    token         VARCHAR(255) NULL,
    coins         INT          NOT NULL DEFAULT 20,
    total_battles INT                   DEFAULT 0,
    won_battles   INT                   DEFAULT 0,
    lost_battles  INT                   DEFAULT 0,
    elo           INT                   DEFAULT 100,
    Name          VARCHAR(255),
    Bio           VARCHAR(255),
    Image         VARCHAR(255),
    rank          INT
);

/* Packages */

DROP TABLE IF EXISTS packages CASCADE;
CREATE TABLE packages
(
    id    SERIAL PRIMARY KEY,
    price DECIMAL(6, 2) NOT NULL DEFAULT 5,
);

/* Cards */

DROP TABLE IF EXISTS cards CASCADE;
CREATE TABLE cards
(
    id           VARCHAR(255)  PRIMARY KEY,
    name         VARCHAR(255)  NOT NULL,
    damage       DECIMAL(6, 2) NOT NULL,
    element_type VARCHAR(255)  ,
    card_type    VARCHAR(255)  ,
    package_id   INT,
    user_id      INT,
    in_deck      BOOLEAN DEFAULT FALSE,
    is_locked    BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES packages (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

/*Battle*/

DROP TABLE IF EXISTS battles CASCADE;
CREATE TABLE battles
(
    id       SERIAL PRIMARY KEY,
    p1 INT,
    p2 INT,
    winner   INT,
    finished BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_player_a FOREIGN KEY (player_a) REFERENCES users (id),
    CONSTRAINT fk_player_b FOREIGN KEY (player_b) REFERENCES users (id),
    CONSTRAINT winner FOREIGN KEY (winner) REFERENCES users (id)
);

DROP TABLE IF EXISTS battle_rounds CASCADE;
CREATE TABLE battle_rounds
(
    id          SERIAL PRIMARY KEY,
    battle_id   INT NOT NULL,
    card_1      VARCHAR(255) NOT NULL,
    card_2      VARCHAR(255) NOT NULL,
    winner_card VARCHAR(255),
    CONSTRAINT fk_battle FOREIGN KEY (battle_id) REFERENCES battles (id),
    CONSTRAINT fk_card_a FOREIGN KEY (card_a) REFERENCES cards (id),
    CONSTRAINT fk_card_b FOREIGN KEY (card_b) REFERENCES cards (id),
    CONSTRAINT fk_winner_card FOREIGN KEY (winner_card) REFERENCES cards (id)
);

/* Trades */

DROP TABLE IF EXISTS trades CASCADE;
CREATE TABLE trades
(
    id       SERIAL PRIMARY KEY,
    card_a   VARCHAR(255) NOT NULL,
    card_b   VARCHAR(255),
    coins    INT     DEFAULT 0,
    accepted BOOLEAN DEFAULT NULL,
    CONSTRAINT fk_card_a FOREIGN KEY (card_a) REFERENCES cards (id),
    CONSTRAINT fk_card_b FOREIGN KEY (card_b) REFERENCES cards (id)
);