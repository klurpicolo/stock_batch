-- DROP TABLE people IF EXISTS;
--
-- CREATE TABLE people  (
--                          person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
--                          first_name VARCHAR(20),
--                          last_name VARCHAR(20)
-- );

DROP TABLE stock IF EXISTS;
DROP TABLE stock_list IF EXISTS;


CREATE TABLE stock  (
                        stock_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
                        symbol VARCHAR(20),
                        name VARCHAR(150),
                        currency VARCHAR(10),
                        stock_exchange VARCHAR(50),
                        ask double,
                        bid double
);

CREATE TABLE stock_list  (
                        stock_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
                        symbol VARCHAR(20)
);
