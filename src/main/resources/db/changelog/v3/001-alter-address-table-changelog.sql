--liquibase formatted sql
--changeset Dmitry Shved:add-addressId-column
ALTER TABLE address
    ADD COLUMN user_id INT,
    ADD CONSTRAINT fk_address_users_id FOREIGN KEY (user_id) REFERENCES users(id);


