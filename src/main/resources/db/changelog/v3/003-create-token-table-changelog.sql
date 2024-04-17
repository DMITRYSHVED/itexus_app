--liquibase formatted sql
--changeset Dmitry Shved:create-token-table
CREATE TABLE token
(
    id                SERIAL PRIMARY KEY,
    token             VARCHAR(255) NOT NULL
);

