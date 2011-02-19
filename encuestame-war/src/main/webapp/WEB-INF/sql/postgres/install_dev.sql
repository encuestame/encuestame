INSERT INTO account (
uid,
twitter_consumer_key,
twitter_consumer_secret,
twitter_account,
twitter_password,
twitter_pin) VALUES
(1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 'testEncuesta', 'testEncuesta123', 4189783);

INSERT INTO userAccount (
uid,
name,
date_new,
invite_code,
password,
email,
status,
twitter,
username,
account_uid,
last_ip_logged,
last_time_logged) VALUES (1, 'Juan Carlos Picado', '2010-01-20 12:47:40', '', '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', 'juanpicado19@gmail.com', true, NULL, 'admin', 1, NULL, '2010-09-26 10:07:25');

INSERT INTO permission (id_permission, permission, description) VALUES
(2, 'ENCUESTAME_ADMIN', 'ENCUESTAME_ADMIN'),
(3, 'ENCUESTAME_OWNER', 'ENCUESTAME_OWNER'),
(4, 'ENCUESTAME_PUBLISHER', 'ENCUESTAME_PUBLISHER'),
(5, 'ENCUESTAME_EDITOR', 'ENCUESTAME_EDITOR'),
(6, 'ENCUESTAME_USER', 'ENCUESTAME_USER');

INSERT INTO userAccount_permission (sec_id_secondary, sec_id_permission) VALUES
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6);
