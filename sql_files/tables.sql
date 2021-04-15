DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  	`room_id` int(11) NOT NULL,
  	`floor_no` int(11) NOT NULL,
  	`room_type_name` varchar(255) NOT NULL,
  	`cost` int(10) NOT NULL,
  	PRIMARY KEY (`room_id`),
  	KEY `room_type_name` (`room_type_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `bookings`;
CREATE TABLE IF NOT EXISTS `bookings` (
  	`b_id` int(11) NOT NULL AUTO_INCREMENT,
  	`room_id` int(11) NOT NULL,
  	`check_in` date NOT NULL,
  	`check_out` date NOT NULL,
  	PRIMARY KEY (`b_id`),
  	KEY `room_id` (`room_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
 	`id` int(11) NOT NULL AUTO_INCREMENT,
 	`fullname` varchar(100) NOT NULL,
 	`username` varchar(50) NOT NULL,
 	`password` varchar(50) NOT NULL,
 	`email` varchar(100) NOT NULL,
 	PRIMARY KEY (`id`),
 	UNIQUE KEY `username` (`username`),
 	UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
 	`username` varchar(50) NOT NULL,
 	`service` varchar(50) NOT NULL,
 	PRIMARY KEY (`username`,`service`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

DROP TABLE IF EXISTS `user_book`;
CREATE TABLE `user_book` (
 	`username` varchar(50) NOT NULL,
 	`b_id` int NOT NULL,
 	PRIMARY KEY (`username`,`b_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci