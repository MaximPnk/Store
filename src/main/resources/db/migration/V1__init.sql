CREATE TABLE products (
    id serial NOT NULL primary key,
    title varchar NOT NULL,
    price float8 NOT NULL,
    count int not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users (
    id bigserial,
    username varchar(30) not null,
    password varchar(80) not null,
    email varchar(50) unique,
    phone varchar(50) unique,
    primary key (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table orders (
    id bigserial not null primary key,
    user_id bigint not null,
    price float not null,
    address text not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    foreign key (user_id) references users (id)
);

CREATE TABLE order_items (
    id bigserial primary key,
    product_id serial NOT NULL,
    quantity bigint,
    item_price float,
    price float,
    order_id bigint,
    constraint item_product_fk foreign key (product_id) references products(id),
    foreign key (order_id) references orders (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id serial,
    name varchar(50) not null,
    primary key (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id bigint not null,
    role_id int not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into roles (name) values
    ('ROLE_CUSTOMER'),
    ('ROLE_ADMIN');

insert into users (username, password, email, phone) values
    ('user1', '$2y$04$v/tR4dKWpN/bypQqcvVEE.Lw961vkUkJMN.YVfMT44RKwlOJdnHri', 'user1@gmail.com', '89161234567'),
    ('user2', '$2y$04$v/tR4dKWpN/bypQqcvVEE.Lw961vkUkJMN.YVfMT44RKwlOJdnHri', 'user2@gmail.com', '89167654321'),
    ('admin', '$2y$04$v/tR4dKWpN/bypQqcvVEE.Lw961vkUkJMN.YVfMT44RKwlOJdnHri', 'admin@gmail.com', '89031223344');

insert into users_roles (user_id, role_id) values
    (1, 1),
    (2, 1),
    (3, 2);

INSERT INTO products (title, price, count) VALUES
    ('Milk',130.0,0),
    ('Orange',50.0,235),
    ('Lemon',90.0,456),
    ('Cucumber',110.0,4835),
    ('Bread',30.0,349),
    ('Boots',150.0,120),
    ('Tea',90.0,945),
    ('Jeans',500.0,237),
    ('Hat',870.0,128),
    ('Coffee',600.0,823),
    ('Pork',420.0,8455),
    ('Turkey',99.0,834),
    ('Salmon',199.0,813),
    ('Trout',175.0,183),
    ('Peach',450.0,843),
    ('Raspberry',399.0,183),
    ('Pineapple',999.0,734);