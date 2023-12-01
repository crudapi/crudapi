CREATE TABLE `ca_metadata_sequence` (
  `id` INTEGER CONSTRAINT `pk_id` PRIMARY KEY AUTOINCREMENT NOT NULL,
  `name` VARCHAR(255) CONSTRAINT `uk_name` UNIQUE NOT NULL,
  `caption` VARCHAR(255) CONSTRAINT `uk_caption` UNIQUE NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `display_order` INTEGER NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `update_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `owner_id` INTEGER DEFAULT NULL,
  `create_by_id` INTEGER DEFAULT NULL,
  `update_by_id` INTEGER DEFAULT NULL,
  `status` VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
  `is_deleted` BOOLEAN NOT NULL DEFAULT false,
  `type` varchar(255)  DEFAULT NULL,
  `is_current_time` BOOLEAN NOT NULL DEFAULT false,
  `is_cycle` BOOLEAN NOT NULL DEFAULT false,
  `format` varchar(255) DEFAULT NULL,
  `min_value` bigint DEFAULT NULL,
  `max_value` bigint DEFAULT NULL,
  `increment_by` bigint DEFAULT NULL,
  `next_value` bigint DEFAULT NULL
);

INSERT INTO `ca_metadata_sequence` (`name`, `caption`, `display_order`, `type`, `format`, `min_value`, `max_value`, `increment_by`, `next_value`) VALUES
('ROLE_CODE', '角色编码', 1, 'STRING', 'ROLE_%09d', 1,  999999999, 1, 1),
('RESOURCE_CODE', '资源编码', 2, 'STRING', 'RESOURCE_%09d', 1, 999999999, 1, 1);