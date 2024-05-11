--liquibase formatted sql
--changeset Dmitry Shved:change-order-comment-type-1
ALTER TABLE orders
ALTER COLUMN order_comment TYPE TEXT;
