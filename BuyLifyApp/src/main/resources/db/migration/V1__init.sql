CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE user
(
    id                NUMBER(38, 0) NOT NULL,
    user_name         VARCHAR2(30) NOT NULL,
    password          VARCHAR2(255),
    name              VARCHAR2(30) NOT NULL,
    surname           VARCHAR2(255) NOT NULL,
    phone_number      VARCHAR2(255) NOT NULL,
    e_mail            VARCHAR2(255) NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    is_active         NUMBER(1) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (user_name);