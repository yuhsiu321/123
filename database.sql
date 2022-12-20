/* Users */

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    username      VARCHAR(255) NOT NULL PRIMARY KEY,
    status        VARCHAR(255),
    password      VARCHAR(255) NOT NULL,
    token         VARCHAR(255) NULL,
    coins         INT          NOT NULL DEFAULT 20
);

/* Packages */

DROP TABLE IF EXISTS packages CASCADE;
CREATE TABLE packages
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255)  NOT NULL DEFAULT 'Card Package',
    price DECIMAL(6, 2) NOT NULL DEFAULT 5
);

/* Cards */

DROP TABLE IF EXISTS cards CASCADE;
CREATE TABLE cards
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255)  NOT NULL,
    damage       DECIMAL(6, 2) NOT NULL,
    element_type VARCHAR(255)  NOT NULL,
    card_type    VARCHAR(255)  NOT NULL,
    package_id   INT,
    user_name    VARCHAR,
    in_deck      BOOLEAN DEFAULT FALSE,
    is_locked    BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES packages (id),
    CONSTRAINT fk_user FOREIGN KEY (user_name) REFERENCES users (username)
);