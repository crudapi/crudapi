INSERT INTO `ca_meta_table` (`id`, `caption`, `createPhysicalTable`, `createdDate`, `description`, `engine`, `lastModifiedDate`, `name`, `pluralName`, `tableName`, `systemable`, `readOnly`) VALUES
(1003, '角色表权限行', b'1', '2022-11-11 17:34:53.708000', '', 'INNODB', '2022-11-11 17:34:53.709000', 'roleTablePermissionLine', 'roleTablePermissionLines', 'ca_roleTablePermissionLine', b'1', b'0');

INSERT INTO `ca_meta_column` (`id`, `autoIncrement`, `caption`, `createdDate`, `dataType`, `defaultValue`, `description`, `displayOrder`, `indexName`, `indexStorage`, `indexType`, `insertable`, `lastModifiedDate`, `length`, `name`, `nullable`, `precision`, `queryable`, `scale`, `seqId`, `unsigned`, `updatable`, `displayable`, `systemable`, `multipleValue`, `tableId`) VALUES
(1300, b'1', '编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2022-11-11 17:34:53.729000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1003),
(1301, b'0', '名称', '2022-11-11 17:34:53.729000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2022-11-11 17:34:53.729000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1003),
(1302, b'0', '全文索引', '2022-11-11 17:34:53.729000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2022-11-11 17:34:53.729000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1003),
(1303, b'0', '创建时间', '2022-11-11 17:34:53.729000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2022-11-11 17:34:53.729000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1003),
(1304, b'0', '修改时间', '2022-11-11 17:34:53.729000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2022-11-11 17:34:53.729000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', NULL, 1003),
(1305, b'0', '创建者编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '创建者编号', 5, NULL, NULL, NULL, b'0', '2022-11-11 17:34:53.729000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1003),
(1306, b'0', '修改者编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '修改者编号', 6, NULL, NULL, NULL, b'0', '2022-11-11 17:34:53.729000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1003),
(1307, b'0', '所有者编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '所有者编号', 7, NULL, NULL, NULL, b'1', '2022-11-11 17:34:53.729000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1003),
(1308, b'0', '是否删除', '2022-11-11 17:34:53.729000', 'BOOL', NULL, '是否删除', 8, NULL, NULL, NULL, b'1', '2022-11-11 17:34:53.729000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1003),
(1309, b'0', '角色编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '角色编号', 9, NULL, NULL, NULL, b'1', '2022-11-11 17:34:53.729000', 20, 'roleId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1003),
(1310, b'0', '表权限编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '表权限编号', 10, NULL, NULL, NULL, b'1', '2022-11-11 17:34:53.729000', 20, 'tablePermissionId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1003);

CREATE TABLE `ca_roleTablePermissionLine` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `roleId` bigint DEFAULT NULL COMMENT '角色编号',
  `tablePermissionId` bigint DEFAULT NULL COMMENT '表权限编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表权限行';

--
-- Dumping data for table `ca_roleTablePermissionLine`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ca_roleTablePermissionLine`
--
ALTER TABLE `ca_roleTablePermissionLine`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_roleTablePermissionLine` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ca_roleTablePermissionLine`
--
ALTER TABLE `ca_roleTablePermissionLine`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10001;
COMMIT;



