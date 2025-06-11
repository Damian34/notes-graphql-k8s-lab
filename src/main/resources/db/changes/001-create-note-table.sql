--liquibase formatted sql

--changeset dwadolowski:001_1
CREATE TABLE notes
(
    id       UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL UNIQUE,
    content  TEXT
);