--liquibase formatted sql
--changeset Dmitry Shved:change-cost-column-type
ALTER TABLE product
    ALTER COLUMN cost SET DATA TYPE DECIMAL,
ALTER COLUMN cost SET NOT NULL;



--liquibase formatted sql
--changeset Dmitry Shved:change-sum-column-type
ALTER TABLE orders
    ALTER COLUMN sum SET DATA TYPE DECIMAL,
ALTER COLUMN sum SET NOT NULL;

