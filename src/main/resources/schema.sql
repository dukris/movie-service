CREATE TABLE IF NOT EXISTS movies
(
    id          bigserial    NOT NULL,
    name        varchar(50)  NOT NULL,
    description varchar(150) NOT NULL,
    year        integer      NOT NULL,
    PRIMARY KEY (id)
);