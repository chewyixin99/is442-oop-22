drop database if exists `heroku_ad30dd59e74b08c`;
create database if not exists `heroku_ad30dd59e74b08c` default character set utf8 collate utf8_general_ci;
use `heroku_ad30dd59e74b08c`;

-- This is done, and follows the ER diagram in the Google Drive
-- Link here: https://app.diagrams.net/#G1TDR1A4IQ3V2OcwaAHy8hvXOS3MokRVjd
create table if not exists `template` (
    `template_id` int auto_increment not null,
    `template_name` varchar(50),
    `template_subject` varchar(100),
    `template_data` varchar(8000) not null,
    `defunct` bit not null, -- 1 for True and 0 for False
primary key (`template_id`)
) engine = InnoDB default charset = utf8 collate utf8_general_ci;

INSERT INTO `template` (`template_id`, `template_name`, `template_subject`, `template_data`, `defunct`) VALUES
(1, 'Account Registration Template', 'Singapore Sport School - Corp Pass Account Registration', '<h1>Dear %s,</h1><p>Thank you for registering an account. Please click on the link below to verify your account.</p><p><a href="%s">%s</a></p>', 0),
(2, 'Password Reset Template', 'Singapore Sport School - Password Reset', '<h1>Dear %s,</h1><p>You are currently amidst the process of resetting your corp pass account password. Click the link below to continue the process.</p><p><a href="%s">%s</a></p><p>Please call Singapore Sport School help desk if this was an unauthorised transaction.</p>', 0),
(3, 'Loan (E-Pass) Confirmation Template', 'Singapore Sport School - Loan Confirmation', '<h1>Dear %s,</h1><p><br></p><p>We are pleased to inform that your booking to %s is confirmed as follows:&nbsp;</p><p><br></p><p>Date of Visit: %s (1 day only)</p><p>Membership ID :%d</p><p><strong>&nbsp;</strong></p><p>For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.&nbsp;</p><p><br></p><p>Attached is the Corporate Membership letter to %s. Please check that the details are accurate.&nbsp;</p><p><br></p><p>Please take note of the following on the day of your visit to %s:</p><ul><li>Present this email, the attached corporate membership letter and your staff pass at the entrance of %s.&nbsp;</li><li>Entry is strictly based on your details in this email and corporate membership letter.</li><li>Your presence is required on the day of visit. Entry will be denied without staff''s presence.&nbsp;</li><li>Your booking is non-transferable. Entry is strictly based on the details in this email and Corporate Membership letter.&nbsp;</li><li>Visit date is strictly based on the date stated in this email and Corporate Membership letter.&nbsp;</li><li>Staff found abusing the Corporate Membership letter will be subject to disciplinary actions.&nbsp;</li></ul><p>Enjoy your visit to %s!&nbsp;</p><p><br></p><p>Warm regards,</p><p>HR Department </p>', 0),
(4, 'Loan (Physical Pass) Confirmation Template', 'Singapore Sport School - Loan Confirmation', '<h1>Dear %s,</h1><p><br></p><p>We are pleased to inform that your booking to %s is confirmed as follows:&nbsp;</p><p><br></p><p>Date of Visit: %s (1 day only)</p><p>Membership ID :%d</p><p><strong>&nbsp;</strong></p><p>For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.&nbsp;</p><p><br></p><p>Attached is the authorisation letter to %s. Please check that the details are accurate.&nbsp;</p><p><br></p><p>Please take note of the following for your visit to %s:</p><ul><li>Present this email confirmation to the General Office to collect the membership card(s).</li><li>Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit.&nbsp;</li><li>Present the membership card(s), the authorisation letter and your staff pass at the entrance of %s.&nbsp;</li><li>Entry is strictly based on the membership card(s) and the authorisation letter.</li><li>Your presence is required on the day of visit. Entry will be denied without staff’s presence.&nbsp;</li><li>Your booking is non-transferable.&nbsp;</li><li>Visit date is strictly based on the date stated in this email and the authorisation letter.&nbsp;</li><li>Staff found abusing the membership(s) will be subject to disciplinary actions.&nbsp;</li></ul><p><br></p><p>Enjoy your visit to %s!&nbsp;</p><p><br></p><p>Warm regards,</p><p>HR Department </p>', 0);
COMMIT;

create table if not exists `user` (
`user_id` int auto_increment not null,
`username` varchar(50) not null,
`password` varchar(60) not null,
`email` varchar(50) not null,
`contact_number` varchar(50) not null,
`user_type` varchar(50) not null,
`defunct` bit not null, -- 1 for True and 0 for False
`enabled` bit not null,
primary key (`user_id`)
) engine = InnoDB default charset = utf8 collate utf8_general_ci;

create table if not exists `pass` (
    `pass_id` int auto_increment not null,
    `pass_number` int not null,
    `num_guests` int not null,
    `replacement_fee` double(10,2) not null,
    `poi` varchar(50),
    `poi_url` varchar(150),
    `is_physical` bit not null,
    `defunct` bit not null, -- 1 for True and 0 for False
    `pass_status` varchar(50) not null,
    `pass_desc` varchar(255) not null,
    `pass_attachment_name` varchar(150) null,
    `pass_attachment` longblob null,
    `pass_image` longblob null,
primary key (`pass_id`)
) engine=InnoDB default charset=utf8 collate utf8_general_ci;

INSERT INTO `pass` (`pass_id`, `pass_number`, `num_guests`, `replacement_fee`, `poi`, `poi_url`, `is_physical`, `defunct`, `pass_status`, `pass_desc`) VALUES
(1, 12345678, 2, 20.00, 'Gardens By The Bay', 'https://www.gardensbythebay.com.sg/', 1, 0, 'ONLOAN', 'Pass 1 description'),
(2, 12345679, 2, 20.00, 'Singapore Zoo','https://www.mandai.com/en/singapore-zoo.html' , 1, 1, 'AVAILABLE', 'Pass 2 description'),
(3, 12345680, 2, 20.00, 'Sea Aquarium','https://www.rwsentosa.com/en/attractions/sea-aquarium', 0, 0, 'AVAILABLE', 'Pass 3 description'),
(4, 12345681, 2, 20.00, 'Sea Aquarium','https://www.rwsentosa.com/en/attractions/sea-aquarium', 0, 0, 'ONLOAN', 'Pass 4 description');
COMMIT;

create table if not exists `loan` (
`loan_id` int auto_increment not null,
`user_id` int not null,
`gop_id` int,
`pass_id` int not null,
`is_completed` bit not null,
`defunct` bit not null,
`start_date` date,
`end_date` date,
primary key (`loan_id`,`user_id`,`pass_id`),
foreign key (`user_id`) references `user` (`user_id`),
foreign key (`gop_id`) references `user` (`user_id`),
foreign key (`pass_id`) references `pass` (`pass_id`)
) engine = InnoDB default charset = utf8 collate utf8_general_ci;