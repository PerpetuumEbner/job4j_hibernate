CREATE TABLE candidates
(
    id         SERIAL PRIMARY KEY,
    name       TEXT,
    experience TEXT,
    salary     INTEGER
);

CREATE TABLE brands
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE
);

CREATE TABLE models
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE
);

CREATE TABLE authors
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE
);

CREATE TABLE books
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE
)