DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, user_id, calories)
VALUES ('2015-10-20 10:00:00', 'breakfast', 100000, 500),
       ('2016-10-20 10:10:00', 'lunch', 100000, 550),
       ('2017-10-21 10:20:00', 'dinner', 100000, 600),
       ('2019-10-21 10:30:00', 'dinner', 100001, 650);