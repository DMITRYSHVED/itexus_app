--liquibase formatted sql
--changeset Dmitry Shved:initial-tables-6
CREATE TABLE position_order
(
    order_id         INT,
    sell_position_id INT,
    quantity         INT NOT NULL CHECK (quantity >= 0),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (sell_position_id) REFERENCES sell_position (id),
    PRIMARY KEY (order_id, sell_position_id)
)

