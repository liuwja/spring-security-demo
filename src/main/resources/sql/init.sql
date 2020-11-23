DROP TABLE
IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
	`id` BIGINT (15) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR (255) NOT NULL,
	`password` VARCHAR (255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


DROP TABLE
IF EXISTS `sys_role`;
CREATE table `sys_role`(
	`id` BIGINT(15) NOT NULL,
`name` VARCHAR(255) not NULL,
PRIMARY key (`id`)
)ENGINE = INNODB DEFAULT CHARSET = utf8;


DROP TABLE
IF EXISTS `sys_user_role`;
CREATE table `sys_user_role`(
	`user_id` BIGINT(15) not NULL,
	`role_id` BIGINT(15) NOT NULL,
	PRIMARY KEY (`user_id`,`role_id`),
	KEY `fk_role_id`(`role_id`),
	CONSTRAINT `fk_role_id` FOREIGN KEY(`role_id`) REFERENCES `sys_role`(`id`) on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT `fk_user_id` FOREIGN KEY(`user_id`) REFERENCES `sys_user`(`id`) on DELETE CASCADE on UPDATE CASCADE
 )ENGINE = INNODB DEFAULT CHARSET = utf8;


insert into sys_user VALUES('1','admin','admin');
insert into sys_user VALUES('2','liuwjg','liuwjg');

insert into sys_role VALUES('1','ROLE_ADMIN');
INSERT INTO sys_role VALUES(2,'ROLE_USER');

INSERT INTO sys_user_role VALUES('1','1');
INSERT INTO sys_user_role VALUES(2,2);