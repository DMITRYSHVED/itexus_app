--liquibase formatted sql
--changeset Dmitry Shved:initial-tables-5
CREATE TABLE orders
(
    id            SERIAL PRIMARY KEY,
    phone         VARCHAR(20) NOT NULL,
    order_comment VARCHAR(255),
    order_status  VARCHAR(50) NOT NULL,
    sum           DECIMAL     NOT NULL,
    address_id    INT,
    user_id       INT,
    FOREIGN KEY (address_id) REFERENCES address (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
)
