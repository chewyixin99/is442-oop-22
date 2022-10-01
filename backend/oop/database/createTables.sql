drop database if exists `oop`;
create database if not exists `oop` default character set utf8 collate utf8_general_ci;
use `oop`;

-- This is done, and follows the ER diagram in the Google Drive
-- Link here: https://app.diagrams.net/#G1TDR1A4IQ3V2OcwaAHy8hvXOS3MokRVjd
create table if not exists `oop`.`emailTemplate` (
    `templateID` int auto_increment not null,
    `template` varchar(1000) not null,
    `defunct` bit not null,
    primary key (`templateID`)
) engine = InnoDB default charset = utf8;

create table if not exists `oop`.`User` (
    `uid` int auto_increment not null,
    `name` varchar(50) not null,
    `password` varchar(50) not null, 
    `email` varchar(50) not null, 
    `contactNumber` varchar(50) not null,
    `status` varchar(50) not null,
    primary key (`uid`)
) engine = InnoDB default charset = utf8;

create table if not exists `oop`.`Passes` (
  `pid` int auto_increment not null,
  `templateID` int,
  `numGuests` int not null,
  `replacementFee` varchar(50),
  `poi` varchar(50),
  `isPhysical` bit not null,
  primary key (`pid`),
  foreign key (`templateID`) references `oop`.`emailTemplate` (`templateID`)
) engine=InnoDB default charset=utf8;

create table if not exists `oop`.`Loans` (
    `lid` int auto_increment not null,
    `uid` int not null,
    `gid` int, 
    `pid` int not null,
    `isCompleted` bit not null, 
    `dateStart` varchar(50),
    `dateEnd` varchar(50),
    primary key (`lid`),
    foreign key (`uid`) references `oop`.`User` (`uid`),
    foreign key (`gid`) references `oop`.`User` (`uid`),
    foreign key (`pid`) references `oop`.`Passes` (`pid`)
) engine = InnoDB default charset = utf8;




