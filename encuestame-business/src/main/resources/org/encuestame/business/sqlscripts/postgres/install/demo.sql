--- Account
INSERT INTO account (uid, account_created_date) VALUES
(1, '2010-01-20 12:47:40');

--- User Account
------ username: admin
------ password: 12345
INSERT INTO useraccount (
uid,
name,
date_new,
invite_code,
last_ip_logged,
last_time_logged,
password,
email,
userprofilepicture,
status,
twitter,
username,
account_uid,
groupid)
VALUES
(1,
'Demo User',
'2010-01-20 12:47:40',
'',
NULL,
'2011-03-22 15:51:08.389',
'6xAX8siGWDJXfkJUVxWLqsk0rz8U+aG6Y8yA1IokxuhEIZ8+RugleJtLUYbdGxc+',
'demo@demo.org',
NULL,
true,
NULL,
'admin',
1,
NULL);
-- Default Permissions

INSERT INTO userAccount_permission (sec_id_secondary, sec_id_permission) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5)

-- Questions

INSERT INTO questions VALUES (1, NULL, 21321312, 'd23das', 'What was the happiest moment of your life? The saddest?', true, 1, NULL);
INSERT INTO questions VALUES (2, NULL, 231321, '231312dsa', 'Who was the most important person in your life? ', true, 1, NULL);
INSERT INTO questions VALUES (3, NULL, 231321, 'd21321ds', 'Who has been the biggest influence on your life? ', true, 1, NULL);
INSERT INTO questions VALUES (4, NULL, 231321, 'dsaddsa', 'Who has been the kindest to you in your life?', true, 1, NULL);
INSERT INTO questions VALUES (5, NULL, 231321, '54gfddf', 'Can you tell me about him or her?', true, 1, NULL);
INSERT INTO questions VALUES (6, NULL, 231321, '676hg', 'What lessons did that person teach you?', true, 1, NULL);
INSERT INTO questions VALUES (7, NULL, 231321, '1fdsfs', 'What are the most important lessons you’ve learned in life?', true, 1, NULL);
INSERT INTO questions VALUES (8, NULL, 231321, '2fdsf', 'What is your earliest memory?', true, 1, NULL);
INSERT INTO questions VALUES (9, NULL, 231321, '2fdgfd', 'What are you proudest of in your life?', true, 1, NULL);
INSERT INTO questions VALUES (10, NULL, 231321, '3dfsd', 'Do you have any regrets?', true, 1, NULL);
INSERT INTO questions VALUES (11, NULL, 231321, '23fds', 'What does your future hold?', true, 1, NULL);
INSERT INTO questions VALUES (12, NULL, 231321, 'dsaddsa', 'Is there anything that you’ve never told me but want to tell me now?', true, 1, NULL);
INSERT INTO questions VALUES (13, NULL, 231321, 'dsaddsa', 'Is there something about me that you’ve always wanted to know but have never asked?', true, 1, NULL);
INSERT INTO questions VALUES (14, NULL, 231321, '6564as', 'What is your first memory of me?', true, 1, NULL);
INSERT INTO questions VALUES (15, NULL, 231321, '4fdsfs', 'Was there a time when you didn’t like me?', true, 1, NULL);
INSERT INTO questions VALUES (16, NULL, 231321, 'dsaddsa', 'Where did you grow up?', true, 1, NULL);
INSERT INTO questions VALUES (17, NULL, 231321, '3fasa', 'Do you think we’ll ever lose touch with each other?', true, 1, NULL);
INSERT INTO questions VALUES (18, NULL, 231321, '126gfd', 'Where will we be in 10 years? 20 years?', true, 1, NULL);







