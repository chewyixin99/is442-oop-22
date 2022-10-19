drop database if exists `oop`;
create database if not exists `oop` default character set utf8 collate utf8_general_ci;
use `oop`;

-- This is done, and follows the ER diagram in the Google Drive
-- Link here: https://app.diagrams.net/#G1TDR1A4IQ3V2OcwaAHy8hvXOS3MokRVjd
create table if not exists `template` (
                                                `template_id` int auto_increment not null,
                                                `template_name` varchar(25),
                                                `template_data` varchar(8000) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    primary key (`template_id`)
    ) engine = InnoDB default charset = utf8;

INSERT INTO `template` (`template_id`, `template_name`, `template_data`, `defunct`) VALUES
                                                                             (1, 'template1', 'template_data1', 0),
                                                                             (2, 'template2', 'template_data2', 0),
                                                                             (3, 'template3', 'template_data3', 1),
                                                                             (4, 'template4', 'template_data4', 0);
COMMIT;

create table if not exists `user` (
                                      `user_id` int auto_increment not null,
                                      `username` varchar(50) not null,
    `password` varchar(50) not null,
    `email` varchar(50) not null,
    `contact_number` varchar(50) not null,
    `user_type` varchar(50) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    primary key (`user_id`)
    ) engine = InnoDB default charset = utf8;


INSERT INTO `user` (`user_id`, `username`, `password`, `email`, `contact_number`, `user_type`, `defunct`) VALUES
                                                                                                              (1, 'PLACEHOLDER WHEN GOP UNASSIGNED', 'gop', 'gop@mail.com', '1', 'GOP', 0),
                                                                                                              (2, 'yixin', 'yixinpw', 'yixin@mail.com', '91234567', 'BORROWER', 0),
                                                                                                              (3, 'kokwee', 'kokweepw', 'kokwee@mail.com', '91234567', 'ADMIN', 0),
                                                                                                              (4, 'shaan', 'shaanpw', 'shaan@mail.com', '91234567', 'BORROWER', 1),
                                                                                                              (5, 'jianlin', 'jianlinpw', 'jianlin@mail.com', '91234567', 'GOP', 0);
COMMIT;



create table if not exists `pass` (
                                      `pass_id` int auto_increment not null,
                                      `num_guests` int not null,
                                      `replacement_fee` double(10,2) not null,
    `poi` varchar(50),
    `is_physical` bit not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    `pass_status` varchar(50) not null,
    `pass_desc` varchar(255) not null,
    primary key (`pass_id`)
    ) engine=InnoDB default charset=utf8;

INSERT INTO `pass` (`pass_id`, `num_guests`, `replacement_fee`, `poi`, `is_physical`, `defunct`, `pass_status`, `pass_desc`) VALUES
                                                                                                                                 (1, 2, 20.00, 'Gardens By The Bay', 1, 0, 'ONLOAN', 'Pass 1 description'),
                                                                                                                                 (2, 2, 20.00, 'Singapore Zoo', 1, 1, 'AVAILABLE', 'Pass 2 description'),
                                                                                                                                 (3, 2, 20.00, 'Sea Aquarium', 0, 0, 'AVAILABLE', 'Pass 3 description'),
                                                                                                                                 (4, 2, 20.00, 'Sea Aquarium', 0, 0, 'ONLOAN', 'Pass 4 description');
COMMIT;

create table if not exists `loan` (
                                      `loan_id` int auto_increment not null,
                                      `user_id` int not null,
                                      `gop_id` int,
                                      `pass_id` int not null,
                                      `is_completed` bit not null,
                                      `defunct` bit not null,
                                      `start_date` varchar(50),
    `end_date` varchar(50),
    primary key (`loan_id`,`user_id`,`pass_id`),
    foreign key (`user_id`) references `user` (`user_id`),
    foreign key (`gop_id`) references `user` (`user_id`),
    foreign key (`pass_id`) references `pass` (`pass_id`)
    ) engine = InnoDB default charset = utf8;

INSERT INTO `loan` (`loan_id`, `user_id`, `gop_id`, `pass_id`, `is_completed`, `defunct`, `start_date`, `end_date`) VALUES
                                                                                                             (1, 1, 4, 1, 1, 0, '1/10/2022', '2/10/2022'),
                                                                                                             (2, 2, 4, 2, 1, 0, '3/10/2022', '4/10/2022'),
                                                                                                             (3, 3, 4, 3, 0, 0, '5/10/2022', '6/10/2022'),
                                                                                                             (4, 4, 4, 4, 0, 1, '7/10/2022', '8/10/2022');
COMMIT;



