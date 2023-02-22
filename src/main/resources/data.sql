TRUNCATE TABLE users_roles CASCADE;
TRUNCATE TABLE invoices CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES (1, 'USER');

INSERT INTO role (id, name)
VALUES (2, 'BOOK');

INSERT INTO role (id, name)
VALUES (3, 'ADMIN');

INSERT INTO users (user_name, name, password)
VALUES ('admin', 'Ad Min', '$2a$12$z4hjLUToDJu8KItL0q/bdONG2NuC9UPFQ8/J6gBI./Dbt.M6PpokW');

INSERT INTO users_roles (username, role_id)
VALUES ('admin', 3);

INSERT INTO users (user_name, name, password)
VALUES ('book', 'Boo Ker', '$2a$12$z4hjLUToDJu8KItL0q/bdONG2NuC9UPFQ8/J6gBI./Dbt.M6PpokW');

INSERT INTO users_roles (username, role_id)
VALUES ('book', 2);
INSERT INTO users_roles (username, role_id)
VALUES ('book', 1);

INSERT INTO users (user_name, name, password)
VALUES ('user', 'Us Ser', '$2a$12$z4hjLUToDJu8KItL0q/bdONG2NuC9UPFQ8/J6gBI./Dbt.M6PpokW');

INSERT INTO users_roles (username, role_id)
VALUES ('user', 1);

INSERT INTO invoices (id,buyer_name,due_date,issue_date,item_name,price)
VALUES (1,'Eric Cartman','2023-6-28','2023-1-1','Chili', 2000);

INSERT INTO invoices (id,buyer_name,due_date,issue_date,item_name,price)
VALUES (2,'Kyle Broflovski','2023-3-14','2023-2-2','Surgery', 100000);

INSERT INTO invoices (id,buyer_name,due_date,issue_date,item_name,price)
VALUES (3,'Stan Marsch','2023-3-1','2023-2-17','Hat', 1000);
