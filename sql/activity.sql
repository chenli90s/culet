CREATE TABLE activity(
  `activityId` VARCHAR (32) NOT NULL ,
  `activityType` VARCHAR (10) NOT NULL ,
  `activityTitle` VARCHAR (30) NOT NULL ,
  `activityDesc` VARCHAR (1000) NOT NULL ,
  `activityDate` DATETIME ,
  `activityHot` INT (32) ,
  `activityJoin` VARCHAR (5000),
  `activityLauncher` VARCHAR (32) NOT NULL ,
  `activityAttribute` VARCHAR (15) NOT NULL ,
  `activityCloseDate` DATETIME
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='`activity`'