-- ============================================================
-- Schema: micro_order_db
-- Service: order-service
-- Tables: cart_item, orders, order_item
-- Source: extracted via SQL Developer DESC on actual Hibernate-
-- generated tables (real schema, not manually written).
--
-- Note: user_id and product_id are reference-only IDs (NOT real
-- foreign keys) since User and Product live in separate schemas
-- (micro_user_db, micro_product_db). See README ER diagram notes.
-- ============================================================

CREATE TABLE cart_item (
    id           NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at   TIMESTAMP(6),
    price        NUMBER(38,2),
    product_id   NUMBER(19),
    quantity     NUMBER(10),
    updated_at   TIMESTAMP(6),
    user_id      VARCHAR2(255 CHAR)
);

CREATE TABLE orders (
    id            NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at    TIMESTAMP(6),
    status        VARCHAR2(255 CHAR),
    total_amount  NUMBER(38,2),
    updated_at    TIMESTAMP(6),
    user_id       VARCHAR2(255 CHAR)
);

CREATE TABLE order_item (
    id           NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    price        NUMBER(38,2),
    product_id   NUMBER(19),
    quantity     NUMBER(10),
    order_id     NUMBER(19) NOT NULL,
    CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
