INSERT INTO USERS (FULL_NAME, EMAIL, PASSWORD) VALUES
  ('Григорий Кислин', 'gkislin@yandex.ru', '$2a$10$tgEdY6aPPUVdaHUTehckdOyCTc6DCAicSjZEF5X73aPMIZQc.dyV6'),
  ('Java_Online_Projects', 'admin@javaops.ru', '$2a$10$tgEdY6aPPUVdaHUTehckdOyCTc6DCAicSjZEF5X73aPMIZQc.dyV6');

INSERT INTO PROJECT (NAME) VALUES ('topjava');

INSERT INTO GROUPS (NAME, TYPE, PROJECT_ID) VALUES
  ('test', null, null),
  ('topjava_register', 'REGISTERED', 1),
  ('topjava06', 'CURRENT', 1),
  ('topjava05', 'FINISHED', 1);


INSERT INTO USER_GROUP (USER_ID, GROUP_ID) VALUES (1, 4);
