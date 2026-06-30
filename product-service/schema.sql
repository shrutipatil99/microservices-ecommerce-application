-- ============================================================
-- Schema: micro_product_db
-- Service: product-service
-- Tables: products
-- Source: extracted via SQL Developer DESC on actual Hibernate-
-- generated table (real schema, not manually written).
-- ============================================================

CREATE TABLE products (
    id              NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    active          NUMBER(1),
    category        VARCHAR2(255 CHAR),
    created_at      TIMESTAMP(6),
    description     VARCHAR2(255 CHAR),
    image_url       VARCHAR2(255 CHAR),
    name            VARCHAR2(255 CHAR),
    price           NUMBER(38,2),
    stock_quantity  NUMBER(10),
    updated_at      TIMESTAMP(6)
);
