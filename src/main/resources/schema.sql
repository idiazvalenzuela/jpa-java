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
    joined_at      TIMESTAMP,
    CONSTRAINT uc_owner_email UNIQUE (email)
);


CREATE TABLE vegetable
(
    vegetable_type VARCHAR(255) NOT NULL,
    average_weight DOUBLE,
    name           VARCHAR(255) NOT NULL,
    family         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_vegetable PRIMARY KEY (name, family)
);

CREATE TABLE location
(
    id        INT PRIMARY KEY,
    latitude  DOUBLE,
    longitude DOUBLE
);

CREATE TABLE farm
(
    id          INT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    location_id INT
);

ALTER TABLE farm
    ADD CONSTRAINT FK_FARM_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);
