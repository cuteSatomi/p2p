CREATE TABLE `login_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `trade_password` varchar(255) DEFAULT NULL,
  `usable_amount` decimal(18,4) NOT NULL,
  `frozen_amount` decimal(18,4) NOT NULL,
  `borrow_limit` decimal(18,4) NOT NULL,
  `version` int(11) NOT NULL,
  `un_receive_interest` decimal(18,4) NOT NULL,
  `un_receive_principal` decimal(18,4) NOT NULL,
  `un_return_amount` decimal(18,4) NOT NULL,
  `remain_borrow_limit` decimal(18,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `system_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `system_dictionary_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `sequence` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `bit_state` bigint(20) NOT NULL,
  `real_name` varchar(30) DEFAULT NULL,
  `id_number` varchar(30) DEFAULT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `income_grade_id` bigint(20) DEFAULT NULL,
  `marriage_id` bigint(20) DEFAULT NULL,
  `kid_count_id` bigint(20) DEFAULT NULL,
  `education_background_id` bigint(20) DEFAULT NULL,
  `house_condition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ip_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(50) NOT NULL,
  `login_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;