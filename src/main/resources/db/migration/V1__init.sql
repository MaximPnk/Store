CREATE TABLE products (
    id serial NOT NULL primary key,
    title varchar NOT NULL,
    price float8 NOT NULL,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table products_in_basket(
    id serial NOT NULL primary key,
    user_id int not null,
    product_id int not null,
    count int not null,
    constraint basket_product_fk foreign key (product_id) references products(id)
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

insert into products_in_basket (user_id, product_id, count) values
    (1, 1, 1),
    (1, 2, 3),
    (1, 3, 5),
    (1, 4, 12);