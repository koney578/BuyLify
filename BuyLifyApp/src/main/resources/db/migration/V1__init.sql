CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE users
(
    id                NUMBER(38, 0) NOT NULL,
    username          VARCHAR2(30) NOT NULL,
    password          VARCHAR2(255),
    name              VARCHAR2(30) NOT NULL,
    surname           VARCHAR2(255) NOT NULL,
    phone_number      VARCHAR2(255) NOT NULL,
    email             VARCHAR2(255),
    registration_date TIMESTAMP,
    is_active         NUMBER(1) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);