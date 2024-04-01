--liquibase formatted sql
--changeset Dmitry Shved:initial-tables-0
CREATE TABLE address
(
    id       SERIAL PRIMARY KEY,
    city     VARCHAR(100) NOT NULL,
    street   VARCHAR(255) NOT NULL,
    house    VARCHAR(50)  NOT NULL,
    flat     VARCHAR(50),
    zip_code VARCHAR(20)
)

