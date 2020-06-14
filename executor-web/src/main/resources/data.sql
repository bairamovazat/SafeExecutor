INSERT INTO executor.user_role (role)
SELECT 'ROLE_USER'
WHERE NOT EXISTS(SELECT role FROM executor.user_role WHERE role = 'ROLE_USER');

INSERT INTO executor.user_role (role)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS(SELECT role FROM executor.user_role WHERE role = 'ROLE_ADMIN');

INSERT INTO executor.user_role (role)
SELECT 'ROLE_CREATOR'
WHERE NOT EXISTS(SELECT role FROM executor.user_role WHERE role = 'ROLE_CREATOR');

INSERT INTO executor.chat_user (email, hash_password, login, name, state)
SELECT 'bairamovazat@gmail.com',
       '$2a$10$.6yt2mHF54B..geG.jkkueY8XmiHeczWErmJ8F2s.OElLRts/tgne',
       'admin',
       'admin',
       'CONFIRMED'
WHERE NOT EXISTS(SELECT name FROM executor.chat_user WHERE name = 'admin');

INSERT INTO executor.users_roles (user_id, role_id)
SELECT (SELECT id FROM executor.chat_user WHERE name = 'admin'),
       (SELECT id FROM executor.user_role WHERE role = 'ROLE_USER')
WHERE NOT EXISTS(
        SELECT users_roles.role_id
        FROM executor.chat_user
                 join executor.users_roles on chat_user.id = users_roles.user_id
                 join executor.user_role on users_roles.role_id = user_role.id
        WHERE chat_user.name = 'admin' and user_role.role = 'ROLE_USER'
    )
ON CONFLICT DO NOTHING;

INSERT INTO executor.users_roles (user_id, role_id)
SELECT (SELECT id FROM executor.chat_user WHERE name = 'admin'),
       (SELECT id FROM executor.user_role WHERE role = 'ROLE_ADMIN')
WHERE NOT EXISTS(
        SELECT users_roles.role_id
        FROM executor.chat_user
                 join executor.users_roles on chat_user.id = users_roles.user_id
                 join executor.user_role on users_roles.role_id = user_role.id
        WHERE chat_user.name = 'admin' and user_role.role = 'ROLE_ADMIN'
    )
ON CONFLICT DO NOTHING;

INSERT INTO executor.users_roles (user_id, role_id)
SELECT (SELECT id FROM executor.chat_user WHERE name = 'admin'),
       (SELECT id FROM executor.user_role WHERE role = 'ROLE_CREATOR')
WHERE NOT EXISTS(
        SELECT users_roles.role_id
        FROM executor.chat_user
                 join executor.users_roles on chat_user.id = users_roles.user_id
                 join executor.user_role on users_roles.role_id = user_role.id
        WHERE chat_user.name = 'admin' and user_role.role = 'ROLE_CREATOR'
    )
ON CONFLICT DO NOTHING;