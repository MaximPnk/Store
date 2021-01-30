CREATE TABLE products (
    id serial NOT NULL primary key,
    title varchar NOT NULL,
    price float8 NOT NULL,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE order_items (
    id bigserial primary key,
    product_id serial NOT NULL,
    quantity bigint,
    item_price float,
    price float,
    constraint item_product_fk foreign key (product_id) references products(id)
);

INSERT INTO products (title,price) VALUES
    ('Milk',130.0),
    ('Orange',50.0),
    ('Lemon',90.0),
    ('Cucumber',110.0),
    ('Bread',30.0),
    ('Boots',150.0),
    ('Tea',90.0),
    ('Jeans',500.0),
    ('Hat',870.0),
    ('Coffee',600.0),
    ('Pork',420.0),
    ('Turkey',99.0),
    ('Salmon',199.0),
    ('Trout',175.0),
    ('Peach',450.0),
    ('Raspberry',399.0),
    ('Pineapple',999.0);