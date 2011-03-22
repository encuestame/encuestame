--- Account
INSERT INTO account (uid, twitter_consumer_key, twitter_consumer_secret, twitter_account, twitter_password, twitter_pin) VALUES
(1, 'nFboU4T1Zhv8cqMC4cP0ug', 'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0', 'testEncuesta', 'testEncuesta123', 4189783);

--- User Account
------ username: demo
------ password: 12345
INSERT INTO userAccount (uid, name, date_new, invite_code, password, email, status, twitter, username, account_uid, last_ip_logged, last_time_logged) VALUES
(1, 'Demo User', '2010-01-20 12:47:40', '', '6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+', 'demo@demo.com', '', NULL, 'demo', 1, NULL, '2010-09-26 10:07:25');

-- Default Permissions
INSERT INTO userAccount_permission (sec_id_secondary, sec_id_permission) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5)
