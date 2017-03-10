-- auto Generated on 2017-03-06 17:23:55 
-- DROP TABLE IF EXISTS `comments`; 
CREATE TABLE `comments`(
    `id` VARCHAR (50) NOT NULL ,
    `status` INT (11) NOT NULL ,
    `target` VARCHAR (30) ,
    `content` VARCHAR (15000) NOT NULL,
    `date` DATETIME NOT NULL ,
    `acid` VARCHAR (32) ,
    PRIMARY KEY (`id`),
    constraint acid_fk foreign key(acid) references announce(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`comments`';
