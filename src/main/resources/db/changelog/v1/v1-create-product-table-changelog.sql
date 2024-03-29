CREATE TABLE product
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(250) NOT NULL,
    description     TEXT         NOT NULL,
    cost            MONEY        NOT NULL,
    product_type_id INT,
    FOREIGN KEY (product_type_id) REFERENCES product_type (id)
)
