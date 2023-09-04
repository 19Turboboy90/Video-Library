DROP TABLE feedback;
DROP TABLE actor;
DROP TABLE movie;
DROP TABLE director;
DROP TABLE users;



CREATE TABLE director
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    date_of_birth DATE         NOT NULL
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    email    VARCHAR(256) NOT NULL
);

CREATE TABLE movie
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    director_id   INTEGER REFERENCES director (id) ON DELETE CASCADE,
    premiere_date DATE         NOT NULL,
    country       VARCHAR(128) NOT NULL,
    genre         VARCHAR(128) NOT NULL
);

CREATE TABLE actor
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    date_of_birth DATE         NOT NULL,
    movie_id      INTEGER REFERENCES movie (id) ON DELETE CASCADE
);

CREATE TABLE feedback
(
    id         SERIAL PRIMARY KEY,
    movie_id   INT REFERENCES movie (id) ON DELETE CASCADE,
    user_id    INT REFERENCES users (id) ON DELETE CASCADE,
    text       VARCHAR(1024),
    assessment INTEGER CHECK ( assessment >= 0 AND assessment <= 10) NOT NULL
);