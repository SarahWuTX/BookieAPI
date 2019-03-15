DROP TABLE IF EXISTS `t_book_category`;
DROP TABLE IF EXISTS `t_history`;
DROP TABLE IF EXISTS `t_order`;
DROP TABLE IF EXISTS `t_cart`;
DROP TABLE IF EXISTS `t_delivery`;
DROP TABLE IF EXISTS `t_category`;
DROP TABLE IF EXISTS `t_book`;
DROP TABLE IF EXISTS `t_user`;


CREATE TABLE `t_book` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`isbn` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL,
`price` numeric(10,2) NOT NULL,
`cover_url` varchar(255) NULL,
`discount` numeric(10,2) NULL,
`author` varchar(255) NULL,
`publisher` varchar(255) NULL,
`discription` varchar(255) NULL,
PRIMARY KEY (`id`) ,
INDEX `book_name` (`name` ASC) USING BTREE,
INDEX `book_author` (`author` ASC) USING BTREE,
UNIQUE INDEX `book_isbn` (`isbn` ASC) USING HASH
);
CREATE TABLE `t_user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`wx_id` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL,
`phone` varchar(11) NULL,
`role` int(3) NOT NULL DEFAULT 0 COMMENT '普通用户是0，管理员是1',
PRIMARY KEY (`id`),
UNIQUE INDEX `user_wx` (`wx_id` ASC) USING HASH
);
CREATE TABLE `t_cart` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) NOT NULL,
`book_id` int(11) NOT NULL,
`count` int(11) NOT NULL DEFAULT 1,
`time` timestamp NOT NULL DEFAULT now(),
PRIMARY KEY (`id`) 
);
CREATE TABLE `t_category` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
PRIMARY KEY (`id`) ,
UNIQUE INDEX `category_name` (`name` ASC) USING HASH
);
CREATE TABLE `t_book_category` (
`book_id` int(11) NOT NULL,
`category_id` int(11) NOT NULL,
PRIMARY KEY (`book_id`, `category_id`) 
);
CREATE TABLE `t_delivery` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`province` varchar(255) NOT NULL,
`delivery_fee` numeric(10,2) NOT NULL DEFAULT 0,
`delivery_time` int(11) NOT NULL,
PRIMARY KEY (`id`) ,
UNIQUE INDEX `delivery_province` (`province` ASC) USING HASH
);
CREATE TABLE `t_order` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) NOT NULL,
`total_price` numeric(10,2) NOT NULL,
`order_time` timestamp NOT NULL DEFAULT now(),
`status` int(3) NOT NULL DEFAULT 0 COMMENT '下单后是0，支付后是1，确认收货后是2，退回是3',
`receiver_name` varchar(255) NOT NULL,
`receiver_number` varchar(11) NOT NULL,
`receiver_province` varchar(255) NOT NULL,
`receiver_location` varchar(255) NOT NULL,
PRIMARY KEY (`id`) 
);
CREATE TABLE `t_history` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) NOT NULL,
`book_id` int(11) NOT NULL,
`count` int(11) NOT NULL DEFAULT 1,
`order_id` int(11) NOT NULL,
PRIMARY KEY (`id`) 
);

ALTER TABLE `t_book_category` ADD CONSTRAINT `fk_book_category_b` FOREIGN KEY (`book_id`) REFERENCES `t_book` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `t_book_category` ADD CONSTRAINT `fk_book_category_c` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `t_cart` ADD CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `t_cart` ADD CONSTRAINT `fk_cart_book` FOREIGN KEY (`book_id`) REFERENCES `t_book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `t_order` ADD CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `t_history` ADD CONSTRAINT `fk_history_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `t_history` ADD CONSTRAINT `fk_history_book` FOREIGN KEY (`book_id`) REFERENCES `t_book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `t_history` ADD CONSTRAINT `fk_history_order` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

