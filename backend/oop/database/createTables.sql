drop database if exists `oop`;
create database if not exists `oop` default character set utf8 collate utf8_general_ci;
use `oop`;

-- This is done, and follows the ER diagram in the Google Drive
-- Link here: https://app.diagrams.net/#G1TDR1A4IQ3V2OcwaAHy8hvXOS3MokRVjd
create table if not exists `email_template` (
    `tid` int auto_increment not null,
    `template` varchar(1000) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    primary key (`tid`)
) engine = InnoDB default charset = utf8;

INSERT INTO `email_template` (`tid`, `template`, `defunct`) VALUES
(1, 'template1', 0),
(2, 'template2', 0),
(3, 'template3', 1),
(4, 'template4', 0);
COMMIT;

create table if not exists `user` (
    `uid` int auto_increment not null,
    `username` varchar(50) not null,
    `password` varchar(50) not null, 
    `email` varchar(50) not null, 
    `contact_number` varchar(50) not null,
    `user_type` varchar(50) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    primary key (`uid`)
) engine = InnoDB default charset = utf8;

INSERT INTO `user` (`uid`, `username`, `password`, `email`, `contact_number`, `user_type`, `defunct`) VALUES
(1, 'yixin', 'yixinpw', 'yixin@mail.com', '91234567', 'ADMIN', 0),
(2, 'kokwee', 'kokweepw', 'kokwee@mail.com', '91234567', 'BORROWER', 0),
(3, 'shaan', 'shaanpw', 'shaan@mail.com', '91234567', 'BORROWER', 1),
(4, 'jianlin', 'jianlinpw', 'jianlin@mail.com', '91234567', 'GOP', 0);
COMMIT;

create table if not exists `pass` (
  `pid` int auto_increment not null,
  `tid` int,
  `num_guests` int not null,
  `replacement_fee` int not null,
  `poi` varchar(50),
  `is_physical` bit not null,
  `defunct` bit not null, -- 1 for True and 0 for False
  primary key (`pid`),
  foreign key (`tid`) references `email_template` (`tid`)
) engine=InnoDB default charset=utf8;

INSERT INTO `pass` (`pid`, `tid`, `num_guests`, `replacement_fee`, `poi`, `is_physical`, `defunct`) VALUES
(1, 1, 2, 20.00, 'Gardens By The Bay', 1, 0),
(2, 2, 2, 20.00, 'Singapore Zoo', 1, 1),
(3, 3, 2, 20.00, 'Sea Aquarium', 0, 0),
(4, 4, 2, 20.00, 'Sea Aquarium', 0, 0);
COMMIT;

create table if not exists `loan` (
    `lid` int auto_increment not null,
    `uid` int not null,
    `gid` int, 
    `pid` int not null,
    `is_completed` bit not null, 
    `start_date` varchar(50),
    `end_date` varchar(50),
    primary key (`lid`,`uid`,`pid`),
    foreign key (`uid`) references `user` (`uid`),
    foreign key (`gid`) references `user` (`uid`),
    foreign key (`pid`) references `pass` (`pid`)
) engine = InnoDB default charset = utf8;

INSERT INTO `loan` (`lid`, `uid`, `gid`, `pid`, `is_completed`, `start_date`, `end_date`) VALUES
(1, 1, 4, 1, 1, '1/10/2022', '2/10/2022'),
(2, 2, 4, 2, 1, '3/10/2022', '4/10/2022'),
(3, 3, 4, 3, 0, '5/10/2022', '6/10/2022'),
(4, 4, 4, 4, 0, '7/10/2022', '8/10/2022');
COMMIT;



