--------------------------------------- NDI => Mot de passe encod� -------------------------------------------------
--
--
--         Mot de passe encod� : $2a$10$JP2snIRWn9xQxqD9M3DSoukHOuDlg18ugIsUvQUvFr4eGMzmGEb3e
--         Mot de passe encod� : $2a$10$3V3834hL1MHRAmw4vfCL3uJdBASk3JYuDSJ/iTMz4/HlMRCJZeOvy
--         Mot de passe encod� : $2a$10$61btA3i6HVyjEmuPEmkySeJVVcAnwBS27wRLO.VP9mBdUtXKu49zW
--
--
---------------------------------------------------------------------------------------------------------------------

insert into users values(10001,'admin', '$2a$10$JP2snIRWn9xQxqD9M3DSoukHOuDlg18ugIsUvQUvFr4eGMzmGEb3e', true);
insert into users values(10002,'openai', '$2a$10$3V3834hL1MHRAmw4vfCL3uJdBASk3JYuDSJ/iTMz4/HlMRCJZeOvy', true);
insert into users values(10003,'nddiakite', '$2a$10$61btA3i6HVyjEmuPEmkySeJVVcAnwBS27wRLO.VP9mBdUtXKu49zW', true);

insert into roles values(10001,'ADMIN');
insert into roles values(10002,'OPENAI');
insert into roles values(10003,'MANAGER');

insert into user_roles values(10001,10001);
insert into user_roles values(10002,10002);
insert into user_roles values(10003,10003);

