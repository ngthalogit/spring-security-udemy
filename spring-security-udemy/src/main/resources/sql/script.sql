create database eazybank;
use eazybank;

# CREATE TABLE users
# (
#     id       INT         NOT NULL AUTO_INCREMENT,
#     username VARCHAR(45) NOT NULL,
#     password VARCHAR(45) NOT NULL,
#     enabled  INT         NOT NULL,
#     PRIMARY KEY (id)
# );

CREATE TABLE authorities
(
    id        INT         NOT NULL AUTO_INCREMENT,
    customer_id  INT NOT NULL,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    KEY customer_id (customer_id),
    CONSTRAINT authorities_ibfk FOREIGN KEY (customer_id) REFERENCES customer (id)
);

INSERT INTO authorities (customer_id, name) VALUES (2, 'VIEWDATA1');
INSERT INTO authorities (customer_id, name) VALUES (2, 'VIEWDATA2');
INSERT INTO authorities (customer_id, name) VALUES (2, 'VIEWDATA3');
INSERT INTO authorities (customer_id, name) VALUES (2, 'VIEWDATA4');
INSERT INTO authorities (customer_id, name) VALUES (2, 'VIEWCUSTOMER');

DELETE FROM authorities;

INSERT INTO authorities (customer_id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (customer_id, name) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (customer_id, name) VALUES (1, 'ROLE_USER');

SELECT * FROM authorities;



# INSERT IGNORE INTO users
# VALUES (NULL, 'ngthlo', 'ngthlo', 1);
# INSERT IGNORE INTO authorities
# VALUES (NULL, 'ngthlo', 'write');

CREATE TABLE customer
(
    id    INT          NOT NULL AUTO_INCREMENT,
    email VARCHAR(45)  NOT NULL,
    pwd   VARCHAR(200) NOT NULL,
    role  VARCHAR(45)  NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO customer values (NULL, 'ngthlo.admin@gmail.com', '$2a$10$lM6zWnjKQ72WhvdWXh3R8esFnYgB.9LsZB3PHo/GN3HCtB.xy2ppm', 'admin');
INSERT INTO customer values (NULL, 'ngthlo.user@gmail.com', '$2a$10$lM6zWnjKQ72WhvdWXh3R8esFnYgB.9LsZB3PHo/GN3HCtB.xy2ppm', 'user');

SELECT * FROM customer;
