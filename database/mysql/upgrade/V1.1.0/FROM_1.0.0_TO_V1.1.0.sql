ALTER TABLE `ca_meta_table` ADD `readOnly` BIT(1) NULL DEFAULT NULL;

UPDATE `ca_meta_table` SET `systemable` = b'1' 
WHERE `ca_meta_table`.`name` in ('province','city','district','file','folder','weixinConfig','smsConfig','smsTemplate','table','moduleLine','module');

UPDATE `ca_meta_table` SET `readOnly` = b'0';

UPDATE `ca_meta_table` SET `readOnly` = b'1' 
WHERE `ca_meta_table`.`name` in ('table');

ALTER TABLE `ca_meta_column` ADD `displayable` BIT(1) NULL DEFAULT b'0';
