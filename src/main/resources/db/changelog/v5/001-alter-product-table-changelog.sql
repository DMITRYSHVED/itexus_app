--liquibase formatted sql
--changeset Dmitry Shved:add-imageId-to-product-table
ALTER TABLE product
    ADD COLUMN image_id VARCHAR(250);
