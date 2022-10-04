drop database if exists `oop`;
create database if not exists `oop` default character set utf8 collate utf8_general_ci;
use `oop`;

-- This is done, and follows the ER diagram in the Google Drive
-- Link here: https://app.diagrams.net/#G1TDR1A4IQ3V2OcwaAHy8hvXOS3MokRVjd
create table if not exists `oop`.`emailTemplate` (
    `templateID` int auto_increment not null,
    `template` varchar(1000) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    primary key (`templateID`)
) engine = InnoDB default charset = utf8;

INSERT INTO `emailTemplate` (`templateId`, `template`, `defunct`) VALUES
(1, 'template1', 0),
(2, 'template2', 0),
(3, 'template3', 1),
(4, 'template4', 0);
COMMIT;

create table if not exists `oop`.`user` (
    `uid` int auto_increment not null,
    `username` varchar(50) not null,
    `password` varchar(50) not null, 
    `email` varchar(50) not null, 
    `contactNumber` varchar(50) not null,
    `status` varchar(50) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    primary key (`uid`)
) engine = InnoDB default charset = utf8;

INSERT INTO `user` (`uid`, `username`, `password`, `email`, `contactNumber`, `status`, `defunct`) VALUES
(1, 'yixin', 'yixinpw', 'yixin@mail.com', '91234567', 'admin', 0),
(2, 'kokwee', 'kokweepw', 'kokwee@mail.com', '91234567', 'borrower', 0),
(3, 'shaan', 'shaanpw', 'shaan@mail.com', '91234567', 'borrower', 1),
(4, 'jianlin', 'jianlinpw', 'jianlin@mail.com', '91234567', 'gop', 0);
COMMIT;

create table if not exists `oop`.`passes` (
  `pid` int auto_increment not null,
  `templateID` int,
  `numGuests` int not null,
  `replacementFee` int not null,
  `poi` varchar(50),
  `isPhysical` bit not null,
  `defunct` bit not null, -- 1 for True and 0 for False
  primary key (`pid`),
  foreign key (`templateID`) references `oop`.`emailTemplate` (`templateID`)
) engine=InnoDB default charset=utf8;

INSERT INTO `passes` (`pid`, `templateID`, `numGuests`, `replacementFee`, `poi`, `isPhysical`, `defunct`) VALUES
(1, 1, 2, 20.00, 'Gardens By The Bay', 1, 0),
(2, 2, 2, 20.00, 'Singapore Zoo', 1, 1),
(3, 3, 2, 20.00, 'Sea Aquarium', 0, 0),
(4, 4, 2, 20.00, 'Sea Aquarium', 0, 0);
COMMIT;

create table if not exists `oop`.`loans` (
    `lid` int auto_increment not null,
    `uid` int not null,
    `gid` int, 
    `pid` int not null,
    `isCompleted` bit not null, 
    `dateStart` varchar(50),
    `dateEnd` varchar(50),
    primary key (`lid`,`uid`,`pid`),
    foreign key (`uid`) references `oop`.`User` (`uid`),
    foreign key (`gid`) references `oop`.`User` (`uid`),
    foreign key (`pid`) references `oop`.`Passes` (`pid`)
) engine = InnoDB default charset = utf8;

INSERT INTO `loans` (`lid`, `uid`, `gid`, `pid`, `isCompleted`, `dateStart`, `dateEnd`) VALUES
(1, 1, 4, 1, 1, '1/10/2022', '2/10/2022'),
(2, 2, 4, 2, 1, '3/10/2022', '4/10/2022'),
(3, 3, 4, 3, 0, '5/10/2022', '6/10/2022'),
(4, 4, 4, 4, 0, '7/10/2022', '8/10/2022');
COMMIT;



