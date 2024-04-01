--liquibase formatted sql
--changeset Dmitry Shved:initial-tables-2
CREATE TABLE product_type
(
    id                SERIAL PRIMARY KEY,
    product_type_name VARCHAR(50) NOT NULL
)

