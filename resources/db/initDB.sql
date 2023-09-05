DROP TABLE feedback;
DROP TABLE users;
DROP TABLE actor_movie;
DROP TABLE actor;
DROP TABLE movie;
DROP TABLE genre;
DROP TABLE director;
DROP TABLE country;


CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(150)
);

CREATE TABLE genre
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(50)
);

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
    director_id   INTEGER REFERENCES director (id),
    premiere_date DATE         NOT NULL,
    country_id    INTEGER REFERENCES country (id),
    genre_id      INTEGER REFERENCES genre (id)
);

CREATE TABLE actor
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    date_of_birth DATE         NOT NULL,
    movie_id      INTEGER REFERENCES movie (id) ON DELETE CASCADE
);

CREATE TABLE actor_movie
(
    id       SERIAL PRIMARY KEY,
    actor_id INTEGER REFERENCES actor (id) ON DELETE CASCADE,
    movie_id INTEGER REFERENCES movie (id) ON DELETE CASCADE
);

CREATE TABLE feedback
(
    id         SERIAL PRIMARY KEY,
    movie_id   INT REFERENCES movie (id) ON DELETE CASCADE,
    user_id    INT REFERENCES users (id) ON DELETE CASCADE,
    text       VARCHAR(1024),
    assessment INTEGER CHECK (assessment >= 0 AND assessment <= 10) NOT NULL
);