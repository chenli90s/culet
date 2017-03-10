CREATE TABLE vote(
  `voteId` VARCHAR (32) NOT NULL ,
  `voteActivityId` VARCHAR(32) NOT NULL ,
  `voteImgHead` VARCHAR(32) NOT NULL ,
  `voteDesc` VARCHAR(100) NOT NULL ,
  `voteCount` INTEGER
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='`vote`'