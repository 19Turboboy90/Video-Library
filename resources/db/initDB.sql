DROP TABLE feedback;
DROP TABLE users;
DROP TABLE actor;
DROP TABLE movie;



CREATE TABLE movie
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    premiere_date DATE         NOT NULL,
    country       VARCHAR(128) NOT NULL,
    genre         VARCHAR(128) NOT NULL
);

CREATE TABLE actor
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    date_of_birth TIMESTAMP    NOT NULL,
    movie_id      INT REFERENCES movie (id)
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    email    VARCHAR(256) NOT NULL
);

CREATE TABLE feedback
(
    id         SERIAL PRIMARY KEY,
    movie_id   INT REFERENCES movie (id),
    user_id    INT REFERENCES users (id),
    text       VARCHAR(1024),
    assessment INTEGER CHECK ( assessment >= 0 AND assessment <= 10) NOT NULL
);