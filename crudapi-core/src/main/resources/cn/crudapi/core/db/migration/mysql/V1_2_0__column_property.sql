INSERT INTO `ca_meta_table` (`id`, `caption`, `createPhysicalTable`, `createdDate`, `description`, `engine`, `lastModifiedDate`, `name`, `pluralName`, `tableName`, `systemable`, `readOnly`) VALUES
(1001, '字段扩展属性', b'1', '2022-09-18 21:20:51.146000', '', 'INNODB', '2022-09-18 21:21:56.273000', 'columnExtProperty', 'columnExtPropertys', 'ca_columnExtProperty', true, NULL);

INSERT INTO `ca_meta_column` (`id`, `autoIncrement`, `caption`, `createdDate`, `dataType`, `defaultValue`, `description`, `displayOrder`, `indexName`, `indexStorage`, `indexType`, `insertable`, `lastModifiedDate`, `length`, `name`, `nullable`, `precision`, `queryable`, `scale`, `seqId`, `unsigned`, `updatable`, `displayable`, `systemable`, `multipleValue`, `tableId`) VALUES
(1100, b'1', '编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2022-09-18 21:21:56.279000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1001),
(1101, b'0', '名称', '2022-09-18 21:20:51.172000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', 200, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1001),
(1102, b'0', '全文索引', '2022-09-18 21:20:51.172000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2022-09-18 21:21:56.279000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1001),
(1103, b'0', '创建时间', '2022-09-18 21:20:51.172000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2022-09-18 21:21:56.279000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1001),
(1104, b'0', '修改时间', '2022-09-18 21:20:51.172000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2022-09-18 21:21:56.279000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1001),
(1105, b'0', '创建者编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '创建者编号', 5, NULL, NULL, NULL, b'0', '2022-09-18 21:21:56.279000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1001),
(1106, b'0', '修改者编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '修改者编号', 6, NULL, NULL, NULL, b'0', '2022-09-18 21:21:56.279000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1001),
(1107, b'0', '所有者编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '所有者编号', 7, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1001),
(1108, b'0', '是否删除', '2022-09-18 21:20:51.172000', 'BOOL', NULL, '是否删除', 8, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1001),
(1109, b'0', '表编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '表编号', 9, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', 20, 'tableId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1001),
(1110, b'0', '列编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '列编号', 10, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', 20, 'columnId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1001),
(1111, b'0', '键', '2022-09-18 21:20:51.172000', 'VARCHAR', NULL, '键', 11, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', 200, 'key', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1001),
(1112, b'0', '值', '2022-09-18 21:20:51.172000', 'VARCHAR', NULL, '值', 12, NULL, NULL, NULL, b'1', '2022-09-18 21:21:56.279000', 2000, 'value', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1001);


INSERT INTO `ca_meta_index` (`id`, `caption`, `createdDate`, `description`, `indexStorage`, `indexType`, `lastModifiedDate`, `name`, `tableId`) VALUES
(1000, '表列键唯一索引', '2022-09-18 21:20:51.204000', '表列键唯一索引', NULL, 'UNIQUE', '2022-09-18 21:21:56.320000', 'uq_table_column_key', 1001);

INSERT INTO `ca_meta_index_line` (`id`, `columnId`, `indexId`) VALUES
(1000, 1109, 1000),
(1001, 1110, 1000),
(1002, 1111, 1000);

COMMIT;

-- --------------------------------------------------------

--
-- Table structure for table `ca_columnExtProperty`
--

CREATE TABLE `ca_columnExtProperty` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `tableId` bigint NOT NULL COMMENT '表编号',
  `columnId` bigint NOT NULL COMMENT '列编号',
  `key` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '键',
  `value` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字段扩展属性';

--
-- Dumping data for table `ca_columnExtProperty`
--

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ca_columnExtProperty`
--
ALTER TABLE `ca_columnExtProperty`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_table_column_key` (`tableId`,`columnId`,`key`);
ALTER TABLE `ca_columnExtProperty` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ca_columnExtProperty`
--
ALTER TABLE `ca_columnExtProperty`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=1;
COMMIT;
