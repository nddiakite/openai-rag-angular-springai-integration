--------------------------------------- NDI => Mot de passe encod� -------------------------------------------------
--
--
--         Mot de passe encod� : $2a$10$JP2snIRWn9xQxqD9M3DSoukHOuDlg18ugIsUvQUvFr4eGMzmGEb3e
--         Mot de passe encod� : $2a$10$3V3834hL1MHRAmw4vfCL3uJdBASk3JYuDSJ/iTMz4/HlMRCJZeOvy
--         Mot de passe encod� : $2a$10$61btA3i6HVyjEmuPEmkySeJVVcAnwBS27wRLO.VP9mBdUtXKu49zW
--
--
---------------------------------------------------------------------------------------------------------------------

insert into users (id, username, password, enabled)
values
       (10001,'admin', '{bcrypt}$2a$10$JP2snIRWn9xQxqD9M3DSoukHOuDlg18ugIsUvQUvFr4eGMzmGEb3e', true),
       (10002,'openai', '{bcrypt}$2a$10$3V3834hL1MHRAmw4vfCL3uJdBASk3JYuDSJ/iTMz4/HlMRCJZeOvy', true),
       (10003,'nddiakite', '{bcrypt}$2a$10$61btA3i6HVyjEmuPEmkySeJVVcAnwBS27wRLO.VP9mBdUtXKu49zW', true);

insert into roles (id, name)
values
       (10001,'ADMIN'),
       (10002,'OPENAI'),
       (10003,'MANAGER');

insert into user_roles (user_id, role_id)
values
       (10001,10001),
       (10002,10002),
       (10003,10003);

