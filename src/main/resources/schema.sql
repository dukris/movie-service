CREATE TABLE IF NOT EXISTS movies
(
    id           bigserial    NOT NULL,
    name         varchar(50)  NOT NULL,
    year         integer      NOT NULL,
    country      varchar(50)  NOT NULL,
    genre        varchar(50)  NOT NULL,
    language     varchar(50)  NOT NULL,
    quality_from integer      NOT NULL,
    quality_to   integer      NOT NULL,
    description  varchar(150) NOT NULL,
    PRIMARY KEY (id)
);