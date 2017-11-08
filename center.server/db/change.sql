CREATE SCHEMA `xapxinh` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

-- Import database
-- Update database

ALTER TABLE `center`.`players` 
DROP COLUMN `accept_request`,
DROP COLUMN `license_key`,
CHANGE COLUMN `port` `port` INT(11) NULL DEFAULT NULL AFTER `ip`;

ALTER TABLE `center`.`players` ADD COLUMN `status_` BIT NULL  AFTER `mac` ;