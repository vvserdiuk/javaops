INSERT INTO USERS (FULL_NAME, EMAIL, PASSWORD) VALUES
  ('Григорий Кислин', 'gkislin@yandex.ru', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  ('Java_Online_Projects', 'admin@javaops.ru', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju');


INSERT INTO GROUPS (NAME) VALUES ('test');
INSERT INTO USER_GROUP (GROUP_ID, USER_ID) VALUES (1, 1), (1, 2);
