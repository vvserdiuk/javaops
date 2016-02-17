INSERT INTO USERS (FULL_NAME, EMAIL, PASSWORD) VALUES
  ('Григорий Кислин', 'gkislin@yandex.ru', '$2a$10$tgEdY6aPPUVdaHUTehckdOyCTc6DCAicSjZEF5X73aPMIZQc.dyV6'),
  ('Java_Online_Projects', 'admin@javaops.ru', '$2a$10$tgEdY6aPPUVdaHUTehckdOyCTc6DCAicSjZEF5X73aPMIZQc.dyV6');


INSERT INTO GROUPS (NAME) VALUES ('test');
INSERT INTO USER_GROUP (GROUP_ID, USER_ID) VALUES (1, 1), (1, 2);
