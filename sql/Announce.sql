-- auto Generated on 2017-03-06 10:10:23 
-- DROP TABLE IF EXISTS `announce`; 
CREATE TABLE `announce`(
    `id` VARCHAR (32) UNIQUE ,
    `status` VARCHAR (15) ,
    `content` VARCHAR (15000) ,
    `date` DATETIME ,
    `hot` INT (32)  ,
    `attribute` VARCHAR (15) ,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`announce`';
