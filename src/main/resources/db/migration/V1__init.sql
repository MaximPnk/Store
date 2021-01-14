CREATE TABLE products (
    id serial NOT NULL primary key,
    name varchar NULL,
    price float8 NULL
);

INSERT INTO products (name,price) VALUES
    ('Milk',130.0),
    ('Orange',50.0),
    ('Lemon',90.0),
    ('Cucumber',110.0);