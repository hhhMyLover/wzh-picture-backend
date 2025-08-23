use wzh_picture;

-- 用户表
insert into user(userAccount, userPassword, userName, userAvatar, userProfile, userRole)
values ('admin', 'admin', 'admin', null, null, 'admin'),
       ('user', 'user', 'user', null, null, 'user'),
       ('test', 'test', 'test', null, null, 'user');