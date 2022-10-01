DROP TABLE IF EXISTS ANIMAL;

CREATE TABLE ANIMAL
(
    id   INT PRIMARY KEY,
    name CHARACTER
);

CREATE TABLE owner
(
    id             INT PRIMARY KEY,
    email          VARCHAR(255),
    name           VARCHAR(255) NOT NULL,
    preferred_name VARCHAR(255),
    CONSTRAINT uc_owner_email UNIQUE (email)
);
