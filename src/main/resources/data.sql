TRUNCATE TABLE users_roles CASCADE;
TRUNCATE TABLE invoices CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES
    (1, 'USER'),
    (2, 'BOOK'),
    (3, 'ADMIN');

INSERT INTO users (user_name, name, password,failed_login_attempts)
VALUES ('book', 'Boo Ker', '$2a$10$.C9vZwZzy2uIaN3k77R0oOxQy.WaS8Sh2tXBtJWgDzlrnvYz/iy7S',0);

INSERT INTO users_roles (username, role_id)
VALUES
    ('book', 2),
    ('book', 1);

INSERT INTO bookkeeper (id,username)
VALUES (200, 'book');

INSERT INTO users (user_name, name, bookkeeper_id, password,failed_login_attempts)
VALUES ('admin', 'Ad Min', 200,'$2a$12$z4hjLUToDJu8KItL0q/bdONG2NuC9UPFQ8/J6gBI./Dbt.M6PpokW',0);

INSERT INTO users_roles (username, role_id)
VALUES ('admin', 3);

INSERT INTO users (user_name, name, bookkeeper_id, password,failed_login_attempts)
VALUES ('user', 'Us Ser', 200,'$2a$12$z4hjLUToDJu8KItL0q/bdONG2NuC9UPFQ8/J6gBI./Dbt.M6PpokW',0);

INSERT INTO users_roles (username, role_id)
VALUES ('user', 1);

INSERT INTO bookkeeper_clients(bookkeeper_id, clients_user_name)
VALUES
    (200, 'admin'),
    (200, 'user');

INSERT INTO invoices (id,is_new,user_name,due_date,issue_date,item_name,price)
VALUES
    (1,true,'user','2023-6-28','2023-1-1','Chili', 2000),
    (2,true,'user','2023-3-14','2023-2-2','Surgery', 100000),
    (3,true,'book','2023-3-1','2023-2-17','Hat', 1000);
