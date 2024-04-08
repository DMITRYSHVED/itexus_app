--liquibase formatted sql
--changeset Dmitry Shved:change-product-type-name
ALTER TABLE product_type
    RENAME COLUMN product_type_name TO "name";
