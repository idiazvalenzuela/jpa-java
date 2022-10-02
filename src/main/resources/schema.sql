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

CREATE TABLE tree
(
    name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE forest
(
    id   INT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_forest PRIMARY KEY (id)
);

CREATE TABLE tree_forest
(
    forest_id INT          NOT NULL,
    tree_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tree_forest PRIMARY KEY (forest_id, tree_name)
);

ALTER TABLE tree_forest
    ADD CONSTRAINT fk_trefor_on_forest FOREIGN KEY (forest_id) REFERENCES forest (id);

ALTER TABLE tree_forest
    ADD CONSTRAINT fk_trefor_on_tree FOREIGN KEY (tree_name) REFERENCES tree (name);

CREATE TABLE managed_farm
(
    id   INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE foreman
(
    id              INT NOT NULL,
    name            VARCHAR(255),
    managed_farm_id INT,
    CONSTRAINT pk_foreman PRIMARY KEY (id)
);

ALTER TABLE foreman
    ADD CONSTRAINT FK_FOREMAN_ON_MANAGED_FARM FOREIGN KEY (managed_farm_id) REFERENCES managed_farm (id);
