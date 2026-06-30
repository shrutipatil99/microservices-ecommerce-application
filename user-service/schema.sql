-- ============================================================
-- Schema: micro_user_db
-- Service: user-service
-- Tables: addresses, user_table
-- Source: extracted via SQL Developer DESC on actual Hibernate-
-- generated tables (real schema, not manually written).
-- ============================================================

CREATE TABLE addresses (
    id          NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    city        VARCHAR2(255 CHAR),
    country     VARCHAR2(255 CHAR),
    pincode     VARCHAR2(255 CHAR),
    state       VARCHAR2(255 CHAR),
    street      VARCHAR2(255 CHAR)
);

CREATE TABLE user_table (
    id           NUMBER(19) GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at   TIMESTAMP(6),
    email        VARCHAR2(255 CHAR),
    first_name   VARCHAR2(255 CHAR),
    last_name    VARCHAR2(255 CHAR),
    phone        VARCHAR2(255 CHAR),
    update_at    TIMESTAMP(6),
    address_id   NUMBER(19),
    role         VARCHAR2(255 CHAR),
    CONSTRAINT fk_user_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);
