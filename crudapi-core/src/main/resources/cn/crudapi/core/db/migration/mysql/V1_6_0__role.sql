INSERT INTO `ca_meta_table` (`id`, `caption`, `createPhysicalTable`, `createdDate`, `description`, `engine`, `lastModifiedDate`, `name`, `pluralName`, `tableName`, `systemable`, `readOnly`) VALUES
(1004, '系统配置', b'1', '2024-07-23 16:47:41.924000', '', 'INNODB', '2024-07-23 17:22:35.484000', 'config', 'configs', 'ca_config', b'1', NULL),
(1005, '角色路由行', b'1', '2024-07-23 17:00:49.821000', '', 'INNODB', '2024-07-23 17:00:49.821000', 'roleRouteLine', 'roleRouteLines', 'ca_roleRouteLine', b'1', b'0'),
(1006, '路由', b'1', '2024-07-23 17:00:50.258000', '', 'INNODB', '2024-07-24 11:14:22.215000', 'route', 'routes', 'ca_route', b'1', b'0'),
(1007, '菜单', b'1', '2024-07-24 11:09:45.167000', '', 'INNODB', '2024-07-24 11:14:51.269000', 'menu', 'menus', 'ca_menu', b'1', NULL),
(1008, '角色菜单行', b'1', '2024-07-24 11:11:10.361000', '', 'INNODB', '2024-07-24 11:12:25.235000', 'roleMenuLine', 'roleMenuLines', 'ca_roleMenuLine', b'1', NULL);

INSERT INTO `ca_meta_column` (`id`, `autoIncrement`, `caption`, `createdDate`, `dataType`, `defaultValue`, `description`, `displayOrder`, `indexName`, `indexStorage`, `indexType`, `insertable`, `lastModifiedDate`, `length`, `name`, `nullable`, `precision`, `queryable`, `scale`, `seqId`, `unsigned`, `updatable`, `displayable`, `systemable`, `multipleValue`, `tableId`) VALUES
(1311, b'1', '编号', '2024-07-23 16:47:41.942000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2024-07-23 17:22:35.493000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1004),
(1312, b'0', '名称', '2024-07-23 16:47:41.942000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2024-07-23 17:22:35.493000', 200, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1004),
(1313, b'0', '键', '2024-07-23 16:47:41.942000', 'VARCHAR', NULL, '键', 2, 'uq_config_key', NULL, 'UNIQUE', b'1', '2024-07-23 17:22:35.493000', 200, 'key', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1004),
(1314, b'0', '值', '2024-07-23 16:47:41.942000', 'VARCHAR', NULL, '值', 3, NULL, NULL, NULL, b'1', '2024-07-23 17:22:35.493000', 200, 'value', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1004),
(1315, b'0', '全文索引', '2024-07-23 16:47:41.942000', 'TEXT', NULL, '全文索引', 4, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2024-07-23 17:22:35.493000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1004),
(1316, b'0', '创建时间', '2024-07-23 16:47:41.942000', 'DATETIME', NULL, '创建时间', 5, NULL, NULL, NULL, b'0', '2024-07-23 17:22:35.493000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1004),
(1317, b'0', '修改时间', '2024-07-23 16:47:41.942000', 'DATETIME', NULL, '修改时间', 6, NULL, NULL, NULL, b'0', '2024-07-23 17:22:35.493000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1004),
(1318, b'0', '创建者编号', '2024-07-23 16:47:41.942000', 'BIGINT', NULL, '创建者编号', 7, NULL, NULL, NULL, b'0', '2024-07-23 17:22:35.493000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1004),
(1319, b'0', '修改者编号', '2024-07-23 16:47:41.942000', 'BIGINT', NULL, '修改者编号', 8, NULL, NULL, NULL, b'0', '2024-07-23 17:22:35.493000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1004),
(1320, b'0', '所有者编号', '2024-07-23 16:47:41.942000', 'BIGINT', NULL, '所有者编号', 9, NULL, NULL, NULL, b'1', '2024-07-23 17:22:35.493000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1004),
(1321, b'0', '是否删除', '2024-07-23 16:47:41.942000', 'BOOL', NULL, '是否删除', 10, NULL, NULL, NULL, b'1', '2024-07-23 17:22:35.493000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1004),
(1322, b'1', '编号', '2024-07-23 17:00:49.823000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2024-07-23 17:00:49.823000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1005),
(1323, b'0', '名称', '2024-07-23 17:00:49.823000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2024-07-23 17:00:49.823000', 200, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1005),
(1324, b'0', '角色编号', '2024-07-23 17:00:49.823000', 'BIGINT', NULL, '角色编号', 2, NULL, NULL, NULL, b'1', '2024-07-23 17:00:49.823000', 20, 'roleId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1005),
(1325, b'0', '路由编号', '2024-07-23 17:00:49.823000', 'BIGINT', NULL, '路由编号', 3, NULL, NULL, NULL, b'1', '2024-07-23 17:00:49.823000', 20, 'routeId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1005),
(1326, b'0', '全文索引', '2024-07-23 17:00:49.823000', 'TEXT', NULL, '全文索引', 4, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2024-07-23 17:00:49.823000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1005),
(1327, b'0', '创建时间', '2024-07-23 17:00:49.823000', 'DATETIME', NULL, '创建时间', 5, NULL, NULL, NULL, b'0', '2024-07-23 17:00:49.824000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1005),
(1328, b'0', '修改时间', '2024-07-23 17:00:49.824000', 'DATETIME', NULL, '修改时间', 6, NULL, NULL, NULL, b'0', '2024-07-23 17:00:49.824000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1005),
(1329, b'0', '创建者编号', '2024-07-23 17:00:49.824000', 'BIGINT', NULL, '创建者编号', 7, NULL, NULL, NULL, b'0', '2024-07-23 17:00:49.824000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1005),
(1330, b'0', '修改者编号', '2024-07-23 17:00:49.824000', 'BIGINT', NULL, '修改者编号', 8, NULL, NULL, NULL, b'0', '2024-07-23 17:00:49.824000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1005),
(1331, b'0', '所有者编号', '2024-07-23 17:00:49.824000', 'BIGINT', NULL, '所有者编号', 9, NULL, NULL, NULL, b'1', '2024-07-23 17:00:49.824000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1005),
(1332, b'0', '是否删除', '2024-07-23 17:00:49.824000', 'BOOL', NULL, '是否删除', 10, NULL, NULL, NULL, b'1', '2024-07-23 17:00:49.824000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1005),
(1333, b'1', '编号', '2024-07-23 17:00:50.260000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2024-07-24 11:14:22.225000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1006),
(1334, b'0', '名称', '2024-07-23 17:00:50.260000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2024-07-24 11:14:22.225000', 200, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1006),
(1335, b'0', '编码', '2024-07-23 17:00:50.260000', 'VARCHAR', NULL, '编码', 2, 'uq_route_code', NULL, 'UNIQUE', b'1', '2024-07-24 11:14:22.225000', 200, 'code', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1006),
(1336, b'0', 'URL表达式', '2024-07-23 17:00:50.260000', 'VARCHAR', NULL, 'URL表达式', 3, 'uq_route_url', NULL, 'UNIQUE', b'1', '2024-07-24 11:14:22.225000', 200, 'url', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1006),
(1337, b'0', '备注', '2024-07-23 17:00:50.260000', 'TEXT', NULL, '备注', 4, NULL, NULL, NULL, b'1', '2024-07-24 11:14:22.225000', 200, 'remark', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1006),
(1338, b'0', '全文索引', '2024-07-23 17:00:50.260000', 'TEXT', NULL, '全文索引', 5, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2024-07-24 11:14:22.225000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1006),
(1339, b'0', '创建时间', '2024-07-23 17:00:50.260000', 'DATETIME', NULL, '创建时间', 6, NULL, NULL, NULL, b'0', '2024-07-24 11:14:22.225000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1006),
(1340, b'0', '修改时间', '2024-07-23 17:00:50.260000', 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, b'0', '2024-07-24 11:14:22.225000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1006),
(1341, b'0', '创建者编号', '2024-07-23 17:00:50.260000', 'BIGINT', NULL, '创建者编号', 8, NULL, NULL, NULL, b'0', '2024-07-24 11:14:22.225000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1006),
(1342, b'0', '修改者编号', '2024-07-23 17:00:50.260000', 'BIGINT', NULL, '修改者编号', 9, NULL, NULL, NULL, b'0', '2024-07-24 11:14:22.225000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1006),
(1343, b'0', '所有者编号', '2024-07-23 17:00:50.260000', 'BIGINT', NULL, '所有者编号', 10, NULL, NULL, NULL, b'1', '2024-07-24 11:14:22.225000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1006),
(1344, b'0', '是否删除', '2024-07-23 17:00:50.260000', 'BOOL', NULL, '是否删除', 11, NULL, NULL, NULL, b'1', '2024-07-24 11:14:22.225000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1006),
(1345, b'1', '编号', '2024-07-24 11:09:45.181000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2024-07-24 11:14:51.279000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1007),
(1346, b'0', '名称', '2024-07-24 11:09:45.181000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2024-07-24 11:14:51.279000', 200, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1007),
(1347, b'0', '编码', '2024-07-24 11:09:45.181000', 'VARCHAR', NULL, '编码', 2, 'uq_menu_code', NULL, 'UNIQUE', b'1', '2024-07-24 11:14:51.279000', 200, 'code', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1007),
(1348, b'0', '全文索引', '2024-07-24 11:09:45.181000', 'TEXT', NULL, '全文索引', 3, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2024-07-24 11:14:51.279000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1007),
(1349, b'0', '创建时间', '2024-07-24 11:09:45.181000', 'DATETIME', NULL, '创建时间', 4, NULL, NULL, NULL, b'0', '2024-07-24 11:14:51.279000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1007),
(1350, b'0', '修改时间', '2024-07-24 11:09:45.181000', 'DATETIME', NULL, '修改时间', 5, NULL, NULL, NULL, b'0', '2024-07-24 11:14:51.279000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1007),
(1351, b'0', '创建者编号', '2024-07-24 11:09:45.181000', 'BIGINT', NULL, '创建者编号', 6, NULL, NULL, NULL, b'0', '2024-07-24 11:14:51.279000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1007),
(1352, b'0', '修改者编号', '2024-07-24 11:09:45.181000', 'BIGINT', NULL, '修改者编号', 7, NULL, NULL, NULL, b'0', '2024-07-24 11:14:51.279000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1007),
(1353, b'0', '所有者编号', '2024-07-24 11:09:45.181000', 'BIGINT', NULL, '所有者编号', 8, NULL, NULL, NULL, b'1', '2024-07-24 11:14:51.279000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1007),
(1354, b'0', '是否删除', '2024-07-24 11:09:45.181000', 'BOOL', NULL, '是否删除', 9, NULL, NULL, NULL, b'1', '2024-07-24 11:14:51.279000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1007),
(1355, b'1', '编号', '2024-07-24 11:11:10.365000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2024-07-24 11:12:25.245000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1008),
(1356, b'0', '名称', '2024-07-24 11:11:10.365000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2024-07-24 11:12:25.245000', 200, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1008),
(1357, b'0', '全文索引', '2024-07-24 11:11:10.365000', 'TEXT', NULL, '全文索引', 4, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2024-07-24 11:12:25.245000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1008),
(1358, b'0', '创建时间', '2024-07-24 11:11:10.365000', 'DATETIME', NULL, '创建时间', 5, NULL, NULL, NULL, b'0', '2024-07-24 11:12:25.245000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1008),
(1359, b'0', '修改时间', '2024-07-24 11:11:10.365000', 'DATETIME', NULL, '修改时间', 6, NULL, NULL, NULL, b'0', '2024-07-24 11:12:25.245000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1008),
(1360, b'0', '创建者编号', '2024-07-24 11:11:10.365000', 'BIGINT', NULL, '创建者编号', 7, NULL, NULL, NULL, b'0', '2024-07-24 11:12:25.245000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1008),
(1361, b'0', '修改者编号', '2024-07-24 11:11:10.365000', 'BIGINT', NULL, '修改者编号', 8, NULL, NULL, NULL, b'0', '2024-07-24 11:12:25.245000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1008),
(1362, b'0', '所有者编号', '2024-07-24 11:11:10.365000', 'BIGINT', NULL, '所有者编号', 9, NULL, NULL, NULL, b'1', '2024-07-24 11:12:25.245000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1008),
(1363, b'0', '是否删除', '2024-07-24 11:11:10.365000', 'BOOL', NULL, '是否删除', 10, NULL, NULL, NULL, b'1', '2024-07-24 11:12:25.245000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1008),
(1364, b'0', '菜单编号', '2024-07-24 11:11:10.365000', 'BIGINT', NULL, '菜单编号', 2, NULL, NULL, NULL, b'1', '2024-07-24 11:12:25.245000', 20, 'menuId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1008),
(1365, b'0', '角色编号', '2024-07-24 11:12:03.649000', 'BIGINT', NULL, '角色编号', 3, NULL, NULL, NULL, b'1', '2024-07-24 11:12:25.245000', 20, 'roleId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1008);

INSERT INTO `ca_meta_table_relation` (`id`, `caption`, `createdDate`, `description`, `lastModifiedDate`, `name`, `relationType`, `fromColumnId`, `fromTableId`, `toColumnId`, `toTableId`) VALUES
(1002, '路由', '2024-07-23 17:00:50.946000', NULL, '2024-07-23 17:00:50.946000', 'route', 'ManyToOne', 1325, 1005, 1333, 1006),
(1003, '角色路由行', '2024-07-23 17:12:20.806000', NULL, '2024-07-23 17:12:20.806000', 'roleRouteLines', 'OneToMany', 20, 2, 1324, 1005),
(1004, '角色菜单行', '2024-07-24 11:13:26.310000', NULL, '2024-07-24 11:13:26.310000', 'roleMenuLines', 'OneToMany', 20, 2, 1365, 1008),
(1005, '菜单', '2024-07-24 11:13:58.599000', NULL, '2024-07-24 11:14:05.042000', 'menu', 'ManyToOne', 1364, 1008, 1345, 1007);

COMMIT;

--
-- 表的结构 `ca_config`
--

CREATE TABLE `ca_config` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `key` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '键',
  `value` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '值',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置';


--
-- 表的结构 `ca_menu`
--

CREATE TABLE `ca_menu` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单';

-- --------------------------------------------------------

--
-- 表的结构 `ca_roleMenuLine`
--

CREATE TABLE `ca_roleMenuLine` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `menuId` bigint NOT NULL COMMENT '菜单编号',
  `roleId` bigint NOT NULL COMMENT '角色编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单行';

-- --------------------------------------------------------

--
-- 表的结构 `ca_roleRouteLine`
--

CREATE TABLE `ca_roleRouteLine` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `roleId` bigint NOT NULL COMMENT '角色编号',
  `routeId` bigint NOT NULL COMMENT '路由编号',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色路由行';

-- --------------------------------------------------------

--
-- 表的结构 `ca_route`
--

CREATE TABLE `ca_route` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `url` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'URL表达式',
  `remark` text COLLATE utf8mb4_unicode_ci COMMENT '备注',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='路由';

--
-- 转储表的索引
--

--
-- 表的索引 `ca_config`
--
ALTER TABLE `ca_config`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_config_key` (`key`);
ALTER TABLE `ca_config` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- 表的索引 `ca_menu`
--
ALTER TABLE `ca_menu`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_menu_code` (`code`);
ALTER TABLE `ca_menu` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- 表的索引 `ca_roleMenuLine`
--
ALTER TABLE `ca_roleMenuLine`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_roleMenuLine` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- 表的索引 `ca_roleRouteLine`
--
ALTER TABLE `ca_roleRouteLine`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_roleRouteLine` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- 表的索引 `ca_route`
--
ALTER TABLE `ca_route`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_route_code` (`code`),
  ADD UNIQUE KEY `uq_route_url` (`url`);
ALTER TABLE `ca_route` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);


INSERT INTO `ca_config` (`id`, `name`, `key`, `value`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `createById`, `updateById`, `ownerId`, `isDeleted`) VALUES
(1, '应用名称', 'appName', 'crudapi', '应用名称 appName crudapi 1 1 1 false', '2024-07-23 16:56:28', NULL, 1, 1, 1, b'0'),
(2, '业务数据名称', 'businessName', '业务数据', '业务数据名称 businessName 业务数据 1 1 1 false', '2024-07-23 16:57:20', NULL, 1, 1, 1, b'0');
COMMIT;
-- --------------------------------------------------------

INSERT INTO `ca_menu` (`id`, `name`, `code`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `createById`, `updateById`, `ownerId`, `isDeleted`) VALUES
(1, '业务数据', 'business', '业务数据 business 1 1 1 false', '2024-07-24 12:48:26', NULL, 1, 1, 1, b'0'),
(2, '内置数据', 'systemBusiness', '内置数据 systemBusiness 1 1 1 false', '2024-07-24 12:48:42', NULL, 1, 1, 1, b'0'),
(3, '元数据', 'metadata', '元数据 metadata 1 1 1 false', '2024-07-24 12:48:59', NULL, 1, 1, 1, b'0');
COMMIT;


--
-- 使用表AUTO_INCREMENT `ca_roleMenuLine`
--
ALTER TABLE `ca_roleMenuLine`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号';

--
-- 使用表AUTO_INCREMENT `ca_roleRouteLine`
--
ALTER TABLE `ca_roleRouteLine`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号';

--
-- 使用表AUTO_INCREMENT `ca_route`
--
ALTER TABLE `ca_route`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号';
COMMIT;

--
-- 使用表AUTO_INCREMENT `ca_config`
--
ALTER TABLE `ca_config`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=3;


ALTER TABLE `ca_menu`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=4;
COMMIT;