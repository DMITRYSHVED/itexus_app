CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(250) NOT NULL,
    password   VARCHAR(50)  NOT NULL,
    role       VARCHAR(50)  NOT NULL
)
