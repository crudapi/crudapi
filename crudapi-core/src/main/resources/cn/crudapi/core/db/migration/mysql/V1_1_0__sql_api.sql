INSERT INTO `ca_meta_table` (`id`, `caption`, `createPhysicalTable`, `createdDate`, `description`, `engine`, `lastModifiedDate`, `name`, `pluralName`, `tableName`, `systemable`, `readOnly`) VALUES
(1000, 'SQL接口', b'1', '2022-07-04 17:57:16.247000', '', 'INNODB', '2022-07-04 19:45:03.678000', 'sqlapi', 'sqlapis', 'ca_sqlapi', true, NULL);

INSERT INTO `ca_meta_column` (`id`, `autoIncrement`, `caption`, `createdDate`, `dataType`, `defaultValue`, `description`, `displayOrder`, `indexName`, `indexStorage`, `indexType`, `insertable`, `lastModifiedDate`, `length`, `name`, `nullable`, `precision`, `queryable`, `scale`, `seqId`, `unsigned`, `updatable`, `displayable`, `systemable`, `multipleValue`, `tableId`) VALUES
(1000, b'1', '编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '主键', 0, 'primary_key', NULL, 'PRIMARY', b'0', '2022-07-04 19:45:03.681000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', b'0', 1000),
(1001, b'0', '名称', '2022-07-04 17:57:16.260000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2022-07-04 19:45:03.681000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', b'0', 1000),
(1002, b'0', '分组', '2022-07-04 17:57:16.260000', 'VARCHAR', NULL, '分组', 2, NULL, NULL, NULL, b'1', '2022-07-04 19:45:03.681000', 200, 'group', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1000),
(1003, b'0', '数据SQL', '2022-07-04 17:57:16.260000', 'VARCHAR', NULL, '数据SQL', 3, NULL, NULL, NULL, b'1', '2022-07-04 19:45:03.681000', 4000, 'dataSql', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1000),
(1004, b'0', '全文索引', '2022-07-04 17:57:16.260000', 'TEXT', NULL, '全文索引', 5, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2022-07-04 19:45:03.681000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1000),
(1005, b'0', '创建时间', '2022-07-04 17:57:16.260000', 'DATETIME', NULL, '创建时间', 6, NULL, NULL, NULL, b'0', '2022-07-04 19:45:03.681000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1000),
(1006, b'0', '修改时间', '2022-07-04 17:57:16.260000', 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, b'0', '2022-07-04 19:45:03.681000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1000),
(1007, b'0', '创建者编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '创建者编号', 8, NULL, NULL, NULL, b'0', '2022-07-04 19:45:03.681000', 20, 'createById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1000),
(1008, b'0', '修改者编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '修改者编号', 9, NULL, NULL, NULL, b'0', '2022-07-04 19:45:03.681000', 20, 'updateById', b'1', NULL, b'1', NULL, NULL, b'0', b'0', b'0', b'0', b'0', 1000),
(1009, b'0', '所有者编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '所有者编号', 10, NULL, NULL, NULL, b'1', '2022-07-04 19:45:03.681000', 20, 'ownerId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1000),
(1010, b'0', '是否删除', '2022-07-04 17:57:16.260000', 'BOOL', NULL, '是否删除', 11, NULL, NULL, NULL, b'1', '2022-07-04 19:45:03.681000', NULL, 'isDeleted', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1000),
(1011, b'0', '个数SQL', '2022-07-04 19:44:11.138000', 'VARCHAR', NULL, '个数SQL', 4, NULL, NULL, NULL, b'1', '2022-07-04 19:45:03.681000', 4000, 'countSql', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', b'0', 1000);


COMMIT;


-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jul 04, 2022 at 01:05 PM
-- Server version: 8.0.27
-- PHP Version: 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `crudapi`
--

-- --------------------------------------------------------

--
-- Table structure for table `ca_sqlapi`
--

CREATE TABLE `ca_sqlapi` (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分组',
  `dataSql` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据SQL',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `createById` bigint DEFAULT NULL COMMENT '创建者编号',
  `updateById` bigint DEFAULT NULL COMMENT '修改者编号',
  `ownerId` bigint DEFAULT NULL COMMENT '所有者编号',
  `isDeleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `countSql` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '个数SQL'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='SQL接口';

--
-- Dumping data for table `ca_sqlapi`
--

INSERT INTO `ca_sqlapi` (`id`, `name`, `group`, `dataSql`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `createById`, `updateById`, `ownerId`, `isDeleted`, `countSql`) VALUES
(1, 'user', 'ums', 'SELECT * FROM spring_user', 'user ums SELECT * FROM spring_user SELECT count(*) FROM spring_user 1 1 1 false', '2022-07-04 18:01:29', '2022-07-04 20:54:40', 1, 1, 1, b'0', 'SELECT count(*) FROM spring_user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ca_sqlapi`
--
ALTER TABLE `ca_sqlapi`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_sqlapi` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ca_sqlapi`
--
ALTER TABLE `ca_sqlapi`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=2;
COMMIT;
