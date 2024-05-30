CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(250) NOT NULL,
    password   VARCHAR(250) NOT NULL,
    role       VARCHAR(50)  NOT NULL
);

CREATE TABLE address
(
    id       SERIAL PRIMARY KEY,
    city     VARCHAR(100) NOT NULL,
    street   VARCHAR(255) NOT NULL,
    house    VARCHAR(50)  NOT NULL,
    flat     VARCHAR(50),
    zip_code VARCHAR(20),
    user_id  INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE product_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE product
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(250) NOT NULL,
    description     TEXT         NOT NULL,
    cost            DECIMAL      NOT NULL,
    product_type_id INT,
    image_id        VARCHAR(250),
    FOREIGN KEY (product_type_id) REFERENCES product_type (id)
);

CREATE TABLE sell_position
(
    id         SERIAL PRIMARY KEY,
    product_id INT,
    size       VARCHAR(50),
    quantity   INT     NOT NULL,
    active     BOOLEAN NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE orders
(
    id            SERIAL PRIMARY KEY,
    phone         VARCHAR(20) NOT NULL,
    order_comment TEXT,
    order_status  VARCHAR(50) NOT NULL,
    sum           DECIMAL        NOT NULL,
    address_id    INT,
    user_id       INT,
    FOREIGN KEY (address_id) REFERENCES address (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE position_order
(
    order_id         INT,
    sell_position_id INT,
    quantity         INT NOT NULL CHECK (quantity >= 0),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (sell_position_id) REFERENCES sell_position (id),
    PRIMARY KEY (order_id, sell_position_id)
);