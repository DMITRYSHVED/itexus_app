--liquibase formatted sql
--changeset Dmitry Shved:initial-tables-4
CREATE TABLE sell_position
(
    id         SERIAL PRIMARY KEY,
    product_id INT,
    size       VARCHAR(50),
    quantity   INT     NOT NULL,
    active     BOOLEAN NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
)

