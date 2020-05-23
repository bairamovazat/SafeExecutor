INSERT INTO executor.user_role (role)
SELECT 'USER'
WHERE NOT EXISTS(SELECT role FROM executor.user_role WHERE role = 'USER');

INSERT INTO executor.user_role (role)
SELECT 'ADMIN'
WHERE NOT EXISTS(SELECT role FROM executor.user_role WHERE role = 'ADMIN');

INSERT INTO executor.user_role (role)
SELECT 'CREATOR'
WHERE NOT EXISTS(SELECT role FROM executor.user_role WHERE role = 'CREATOR');

INSERT INTO executor.chat_user (email, hash_password, login, name, state)
SELECT 'bairamovazat@gmail.com', '$2a$10$.6yt2mHF54B..geG.jkkueY8XmiHeczWErmJ8F2s.OElLRts/tgne', 'admin', 'admin', 'CONFIRMED'
WHERE NOT EXISTS(SELECT name FROM executor.chat_user WHERE name = 'admin');

INSERT INTO executor.users_roles (user_id, role_id)
SELECT (SELECT id FROM executor.chat_user WHERE name = 'admin'), (SELECT id FROM executor.user_role WHERE role = 'ADMIN')
WHERE EXISTS(SELECT id FROM executor.chat_user WHERE name = 'admin');

INSERT INTO executor.users_roles (user_id, role_id)
SELECT (SELECT id FROM executor.chat_user WHERE name = 'admin'), (SELECT id FROM executor.user_role WHERE role = 'CREATOR')
WHERE EXISTS(SELECT id FROM executor.chat_user WHERE name = 'admin');

INSERT INTO executor.users_roles (user_id, role_id)
SELECT (SELECT id FROM executor.chat_user WHERE name = 'admin'), (SELECT id FROM executor.user_role WHERE role = 'USER')
WHERE EXISTS(SELECT id FROM executor.chat_user WHERE name = 'admin');