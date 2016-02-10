INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES
  ('Григорий', 'Кислин', 'gkislin@yandex.ru', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  ('Java Online Projects', '', 'admin@javaops.ru', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  ('GeKis', '', 'gekis@yandex.ru', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju');


INSERT INTO GROUPS (NAME) VALUES ('test');
INSERT INTO USER_GROUP (GROUP_ID, USER_ID) VALUES (1, 1), (1, 2), (1, 3);
