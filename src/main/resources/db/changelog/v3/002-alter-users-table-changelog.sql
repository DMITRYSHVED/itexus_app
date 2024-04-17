--liquibase formatted sql
--changeset Dmitry Shved:increase-password-length
ALTER TABLE users
ALTER COLUMN password TYPE VARCHAR(250);
