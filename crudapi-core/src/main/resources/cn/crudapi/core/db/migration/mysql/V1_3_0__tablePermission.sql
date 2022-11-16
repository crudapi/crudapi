INSERT INTO `ca_meta_table` (`id`, `caption`, `createPhysicalTable`, `createdDate`, `description`, `engine`, `lastModifiedDate`, `name`, `pluralName`, `tableName`, `systemable`, `readOnly`) VALUES
(1002, '表权限', b'1', '2022-11-11 16:44:22.257000', '', 'INNODB', '2022-11-12 16:04:26.649000', 'tablePermission', 'ca_tablePermissions', 'ca_tablePermission', b'1', b'0');


INSERT INTO `ca_meta_column` (`id`, `autoIncrement`, `caption`, `createdDate`, `dataType`, `defaultValue`, `description`, `displayOrder`, `indexName`, `indexStorage`, `indexType`, `insertable`, `lastModifiedDate`, `length`, `name`, `nullable`, `precision`, `queryable`, `scale`, `seqId`, `unsigned`, `updatable`, `displayable`, `systemable`, `multipleValue`, `tableId`) VALUES
(1200, b'1', '编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2022-11-12 16:04:26.658000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1002),
(1201, b'0', '名称', '2022-11-11 16:44:22.276000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2022-11-12 16:04:26.658000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1002),
(1202, b'0', '全文索引', '2022-11-11 16:44:22.276000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2022-11-12 16:04:26.658000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1002),
(1203, b'0', '创建时间', '2022-11-11 16:44:22.276000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2022-11-12 16:04:26.658000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1002),
(1204, b'0', '修改时间', '2022-11-11 16:44:22.276000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2022-11-12 16:04:26.658000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', NULL, 1002),
(1205, b'0', '创建者编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '创建者编号', 5, NULL, NULL, NULL, b'0', '2022-11-12 16:04:26.658000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1002),
(1206, b'0', '修改者编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '修改者编号', 6, NULL, NULL, NULL, b'0', '2022-11-12 16:04:26.658000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1002),
(1207, b'0', '所有者编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '所有者编号', 7, NULL, NULL, NULL, b'1', '2022-11-12 16:04:26.658000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1002),
(1208, b'0', '是否删除', '2022-11-11 16:44:22.276000', 'BOOL', NULL, '是否删除', 8, NULL, NULL, NULL, b'1', '2022-11-12 16:04:26.658000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1002),
(1209, b'0', '表编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '表编号', 9, NULL, NULL, NULL, b'1', '2022-11-12 16:04:26.658000', 20, 'tableId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1002),
(1210, b'0', '值', '2022-11-11 16:44:22.276000', 'VARCHAR', NULL, '值', 10, NULL, NULL, NULL, b'1', '2022-11-12 16:04:26.658000', 4000, 'value', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1002);

INSERT INTO `ca_meta_index` (`id`, `caption`, `createdDate`, `description`, `indexStorage`, `indexType`, `lastModifiedDate`, `name`, `tableId`) VALUES
(1100, '名称唯一性索引', '2022-11-12 16:04:26.716000', '名称唯一性索引', NULL, 'UNIQUE', '2022-11-12 16:04:26.716000', 'uq_tableId_name', 1002);

INSERT INTO `ca_meta_index_line` (`id`, `columnId`, `indexId`) VALUES
(1100, 1201, 1100),
(1101, 1209, 1100);

COMMIT;

CREATE TABLE `ca_tablePermission` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `tableId` bigint DEFAULT NULL COMMENT '表编号',
  `value` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='表权限';

--
-- Dumping data for table `ca_tablePermission`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ca_tablePermission`
--
ALTER TABLE `ca_tablePermission`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_tableId_name` (`name`,`tableId`);
ALTER TABLE `ca_tablePermission` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ca_tablePermission`
--
ALTER TABLE `ca_tablePermission`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10001;
COMMIT;

