-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Nov 03, 2021 at 03:23 AM
-- Server version: 8.0.23
-- PHP Version: 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `crudapi`
--

-- --------------------------------------------------------

--
-- Table structure for table `ca_city`
--

CREATE TABLE `ca_city` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `provinceId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ca_district`
--

CREATE TABLE `ca_district` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cityId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ca_file`
--

CREATE TABLE `ca_file` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `md5` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `folderId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ca_folder`
--

CREATE TABLE `ca_folder` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `parentFolderId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ca_meta_column`
--

CREATE TABLE `ca_meta_column` (
  `id` bigint NOT NULL,
  `autoIncrement` bit(1) DEFAULT NULL,
  `caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `dataType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `defaultValue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `displayOrder` int DEFAULT NULL,
  `indexName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `indexStorage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `indexType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `insertable` bit(1) DEFAULT NULL,
  `lastModifiedDate` datetime(6) DEFAULT NULL,
  `length` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nullable` bit(1) DEFAULT NULL,
  `precision` int DEFAULT NULL,
  `queryable` bit(1) DEFAULT NULL,
  `scale` int DEFAULT NULL,
  `seqId` bigint DEFAULT NULL,
  `unsigned` bit(1) DEFAULT NULL,
  `updatable` bit(1) DEFAULT NULL,
  `displayable` bit(1) DEFAULT NULL,
  `systemable` bit(1) DEFAULT NULL,
  `multipleValue` bit(1) NULL DEFAULT b'0',
  `tableId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_meta_column`
--

INSERT INTO `ca_meta_column` (`id`, `autoIncrement`, `caption`, `createdDate`, `dataType`, `defaultValue`, `description`, `displayOrder`, `indexName`, `indexStorage`, `indexType`, `insertable`, `lastModifiedDate`, `length`, `name`, `nullable`, `precision`, `queryable`, `scale`, `seqId`, `unsigned`, `updatable`, `displayable`, `systemable`, `tableId`) VALUES
(1, b'1', '编号', '2020-12-23 17:06:29.211000', 'BIGINT', NULL, '编号', 0, NULL, NULL, 'PRIMARY', b'0', '2021-08-05 10:52:53.967000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'1', 1),
(2, b'0', '名称', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 1),
(3, b'0', '全文索引', '2020-12-23 17:06:29.211000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-08-05 10:52:53.967000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 1),
(4, b'0', '创建时间', '2020-12-23 17:06:29.211000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-08-05 10:52:53.967000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 1),
(5, b'0', '修改时间', '2020-12-23 17:06:29.211000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-08-05 10:52:53.967000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 1),
(6, b'0', 'OPENID', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, 'OPENID', 5, 'uq_spring_user_openid', NULL, 'UNIQUE', b'1', '2021-08-05 10:52:53.967000', 50, 'openId', b'1', NULL, b'0', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(7, b'0', '用户名', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, '用户名', 6, 'uq_spring_user_username', NULL, 'UNIQUE', b'1', '2021-08-05 10:52:53.967000', 50, 'username', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(8, b'0', '真实姓名', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, '真实姓名', 7, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 200, 'realname', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(9, b'0', '手机', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, '手机', 8, 'uq_spring_user_mobile', NULL, 'UNIQUE', b'1', '2021-08-05 10:52:53.967000', 20, 'mobile', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(10, b'0', '邮箱', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, '邮箱', 9, 'uq_spring_user_email', NULL, 'UNIQUE', b'1', '2021-08-05 10:52:53.967000', 200, 'email', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(11, b'0', '密码', '2020-12-23 17:06:29.211000', 'PASSWORD', NULL, '密码', 10, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 500, 'password', b'0', NULL, b'0', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(12, b'0', '明文密码', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, '明文密码', 11, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 200, 'cleartextPwd', b'1', NULL, b'0', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(13, b'0', 'TOKEN', '2020-12-23 17:06:29.211000', 'VARCHAR', NULL, 'TOKEN', 12, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 200, 'token', b'1', NULL, b'0', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(14, b'0', '启用', '2020-12-23 17:06:29.211000', 'BOOL', 'true', '启用', 13, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 10, 'enabled', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(15, b'0', '账号没有过期', '2020-12-23 17:06:29.211000', 'BOOL', 'true', '账号没有过期', 14, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 10, 'accountNonExpired', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(16, b'0', '账号没有锁定', '2020-12-23 17:06:29.211000', 'BOOL', 'true', '账号没有锁定', 15, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 10, 'accountNonLocked', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(17, b'0', '凭证没有过期', '2020-12-23 17:06:29.211000', 'BOOL', 'true', '凭证没有过期', 16, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 10, 'credentialsNonExpired', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(20, b'1', '编号', '2021-02-01 10:51:21.334000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-08-05 10:53:53.431000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'1', 2),
(21, b'0', '名称', '2021-02-01 10:51:21.334000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-08-05 10:53:53.431000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 2),
(22, b'0', '全文索引', '2021-02-01 10:51:21.334000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-08-05 10:53:53.431000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 2),
(23, b'0', '创建时间', '2021-02-01 10:51:21.334000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-08-05 10:53:53.431000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 2),
(24, b'0', '修改时间', '2021-02-01 10:51:21.334000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-08-05 10:53:53.431000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 2),
(25, b'0', '编码', '2021-02-01 10:51:21.334000', 'VARCHAR', NULL, '编码', 5, 'uq_spring_role_code', NULL, 'UNIQUE', b'1', '2021-08-05 10:53:53.431000', 200, 'code', b'0', NULL, b'1', NULL, 1, b'0', b'1', b'0', b'0', 2),
(26, b'0', '备注', '2021-02-01 10:51:21.334000', 'TEXT', NULL, '备注', 6, NULL, NULL, NULL, b'1', '2021-08-05 10:53:53.431000', NULL, 'remark', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 2),
(27, b'1', '编号', '2021-02-01 10:54:05.671000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-05 17:52:19.036000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 3),
(28, b'0', '名称', '2021-02-01 10:54:05.671000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-05 17:52:19.036000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 3),
(29, b'0', '全文索引', '2021-02-01 10:54:05.671000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-05 17:52:19.036000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 3),
(30, b'0', '创建时间', '2021-02-01 10:54:05.671000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-05 17:52:19.036000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 3),
(31, b'0', '修改时间', '2021-02-01 10:54:05.671000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-05 17:52:19.036000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 3),
(33, b'0', 'URL表达式', '2021-02-01 10:54:05.671000', 'VARCHAR', NULL, 'URL表达式', 6, 'uq_spring_resource_url', NULL, 'UNIQUE', b'1', '2021-02-05 17:52:19.036000', 200, 'url', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 3),
(34, b'0', '备注', '2021-02-01 10:54:05.671000', 'TEXT', NULL, '备注', 8, NULL, NULL, NULL, b'1', '2021-02-05 17:52:19.036000', NULL, 'remark', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 3),
(35, b'1', '编号', '2021-02-01 11:11:15.313000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-01 11:11:15.313000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 4),
(36, b'0', '名称', '2021-02-01 11:11:15.313000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-01 11:11:15.313000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 4),
(37, b'0', '全文索引', '2021-02-01 11:11:15.313000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-01 11:11:15.313000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 4),
(38, b'0', '创建时间', '2021-02-01 11:11:15.313000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-01 11:11:15.313000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 4),
(39, b'0', '修改时间', '2021-02-01 11:11:15.313000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-01 11:11:15.313000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 4),
(40, b'0', '用户编号', '2021-02-01 11:11:15.313000', 'BIGINT', NULL, '用户编号', 5, NULL, NULL, NULL, b'1', '2021-02-01 11:11:15.313000', 20, 'userId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 4),
(41, b'0', '角色编号', '2021-02-01 11:11:15.313000', 'BIGINT', NULL, '角色编号', 6, NULL, NULL, NULL, b'1', '2021-02-01 11:11:15.313000', 20, 'roleId', b'0', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 4),
(42, b'1', '编号', '2021-02-01 11:27:24.851000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-03 16:43:36.326000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 5),
(43, b'0', '名称', '2021-02-01 11:27:24.851000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-03 16:43:36.326000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 5),
(44, b'0', '全文索引', '2021-02-01 11:27:24.851000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-03 16:43:36.326000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 5),
(45, b'0', '创建时间', '2021-02-01 11:27:24.851000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-03 16:43:36.326000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 5),
(46, b'0', '修改时间', '2021-02-01 11:27:24.851000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-03 16:43:36.326000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 5),
(47, b'0', '角色编号', '2021-02-01 11:27:24.851000', 'BIGINT', NULL, '角色编号', 5, NULL, NULL, NULL, b'1', '2021-02-03 16:43:36.326000', 20, 'roleId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 5),
(48, b'0', '资源编号', '2021-02-01 11:27:24.851000', 'BIGINT', NULL, '资源编号', 6, NULL, NULL, NULL, b'1', '2021-02-03 16:43:36.326000', 20, 'resourceId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 5),
(56, b'0', '编码', '2021-02-02 10:06:53.176000', 'VARCHAR', NULL, '编码', 5, 'uq_spring_resource_code', NULL, 'UNIQUE', b'1', '2021-02-05 17:52:19.036000', 200, 'code', b'1', NULL, b'1', NULL, 2, b'0', b'1', NULL, b'0', 3),
(58, b'0', '操作', '2021-02-03 16:44:10.071000', 'VARCHAR', NULL, '操作', 7, NULL, NULL, NULL, b'1', '2021-02-05 17:52:19.036000', 200, 'action', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 3),
(155, b'1', '编号', '2021-02-07 11:57:10.131000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-07 15:25:57.167000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 19),
(156, b'0', '名称', '2021-02-07 11:57:10.131000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-07 15:25:57.167000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 19),
(157, b'0', '全文索引', '2021-02-07 11:57:10.131000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-07 15:25:57.167000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 19),
(158, b'0', '创建时间', '2021-02-07 11:57:10.131000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-07 15:25:57.167000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 19),
(159, b'0', '修改时间', '2021-02-07 11:57:10.131000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-07 15:25:57.167000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 19),
(160, b'0', '编码', '2021-02-07 11:57:10.131000', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, b'1', '2021-02-07 15:25:57.167000', 200, 'code', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 19),
(161, b'1', '编号', '2021-02-07 11:59:06.948000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-08 16:21:31.011000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 20),
(162, b'0', '名称', '2021-02-07 11:59:06.948000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-08 16:21:31.011000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 20),
(163, b'0', '全文索引', '2021-02-07 11:59:06.948000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-08 16:21:31.011000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 20),
(164, b'0', '创建时间', '2021-02-07 11:59:06.948000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-08 16:21:31.011000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 20),
(165, b'0', '修改时间', '2021-02-07 11:59:06.948000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-08 16:21:31.011000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 20),
(166, b'0', '编码', '2021-02-07 11:59:06.948000', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, b'1', '2021-02-08 16:21:31.011000', 200, 'code', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 20),
(167, b'0', '省编号', '2021-02-07 11:59:06.948000', 'BIGINT', NULL, '省编号', 6, NULL, NULL, NULL, b'1', '2021-02-08 16:21:31.011000', 20, 'provinceId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 20),
(176, b'1', '编号', '2021-02-07 15:25:23.718000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-08-05 10:38:57.087000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'1', 22),
(177, b'0', '名称', '2021-02-07 15:25:23.718000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-08-05 10:38:57.087000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 22),
(178, b'0', '全文索引', '2021-02-07 15:25:23.718000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-08-05 10:38:57.087000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 22),
(179, b'0', '创建时间', '2021-02-07 15:25:23.718000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-08-05 10:38:57.087000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 22),
(180, b'0', '修改时间', '2021-02-07 15:25:23.718000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-08-05 10:38:57.087000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'1', 22),
(181, b'0', '编码', '2021-02-07 15:25:23.718000', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, b'1', '2021-08-05 10:38:57.087000', 200, 'code', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 22),
(182, b'0', '市编号', '2021-02-07 15:25:23.718000', 'BIGINT', NULL, '市编号', 6, NULL, NULL, NULL, b'1', '2021-08-05 10:38:57.087000', 20, 'cityId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 22),
(192, b'1', '编号', '2021-02-08 11:09:32.062000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-09 14:33:24.642000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 24),
(193, b'0', '名称', '2021-02-08 11:09:32.062000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-09 14:33:24.642000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 24),
(194, b'0', '全文索引', '2021-02-08 11:09:32.062000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-09 14:33:24.642000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 24),
(195, b'0', '创建时间', '2021-02-08 11:09:32.062000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-09 14:33:24.642000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 24),
(196, b'0', '修改时间', '2021-02-08 11:09:32.062000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-09 14:33:24.642000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 24),
(197, b'0', '大小', '2021-02-08 11:09:32.062000', 'BIGINT', NULL, '大小', 6, NULL, NULL, NULL, b'1', '2021-02-09 14:33:24.642000', 20, 'size', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 24),
(198, b'0', 'MD5', '2021-02-08 11:09:32.062000', 'VARCHAR', NULL, 'MD5', 7, NULL, NULL, NULL, b'1', '2021-02-09 14:33:24.642000', 200, 'md5', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 24),
(199, b'0', '类型', '2021-02-08 11:09:32.062000', 'VARCHAR', NULL, '类型', 8, NULL, NULL, NULL, b'1', '2021-02-09 14:33:24.642000', 200, 'type', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 24),
(218, b'0', '链接', '2021-02-08 16:17:56.520000', 'ATTACHMENT', NULL, '链接', 5, NULL, NULL, NULL, b'1', '2021-02-09 14:33:24.642000', 4000, 'url', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 24),
(219, b'0', '头像文件编号', '2021-02-08 16:27:37.689000', 'BIGINT', NULL, '头像文件编号', 17, NULL, NULL, NULL, b'1', '2021-08-05 10:52:53.967000', 20, 'fileId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 1),
(220, b'1', '编号', '2021-02-09 14:21:40.999000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-02-09 14:21:40.999000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 34),
(221, b'0', '名称', '2021-02-09 14:21:40.999000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-02-09 14:21:40.999000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 34),
(222, b'0', '全文索引', '2021-02-09 14:21:40.999000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-02-09 14:21:40.999000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 34),
(223, b'0', '创建时间', '2021-02-09 14:21:40.999000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-02-09 14:21:40.999000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 34),
(224, b'0', '修改时间', '2021-02-09 14:21:40.999000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-02-09 14:21:40.999000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 34),
(225, b'0', '父目录编号', '2021-02-09 14:21:40.999000', 'BIGINT', NULL, '父目录编号', 5, NULL, NULL, NULL, b'1', '2021-02-09 14:21:40.999000', 20, 'parentFolderId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 34),
(226, b'0', '目录编号', '2021-02-09 14:33:24.642000', 'BIGINT', NULL, '目录编号', 9, NULL, NULL, NULL, b'1', '2021-02-09 14:33:24.642000', 20, 'folderId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 24),
(244, b'1', '编号', '2021-07-25 16:41:11.900000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-07-25 16:41:37.922000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 37),
(245, b'0', '名称', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 37),
(246, b'0', '全文索引', '2021-07-25 16:41:11.900000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-07-25 16:41:37.922000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 37),
(247, b'0', '创建时间', '2021-07-25 16:41:11.900000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-07-25 16:41:37.922000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 37),
(248, b'0', '修改时间', '2021-07-25 16:41:11.900000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-07-25 16:41:37.922000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 37),
(249, b'0', 'AppId', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, 'AppId', 5, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'appId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(250, b'0', 'AppSecret', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, 'AppSecret', 6, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'appSecret', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(251, b'0', 'Token', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, 'Token', 7, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'token', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(252, b'0', 'EncodingAESKey', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, 'EncodingAESKey', 8, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'encodingAESKey', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(253, b'0', 'AccessToken', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, 'AccessToken', 9, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'accessToken', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(254, b'0', 'ExpiresIn', '2021-07-25 16:41:11.900000', 'DATETIME', NULL, 'ExpiresIn', 10, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 200, 'expiresIn', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(255, b'0', 'Enable', '2021-07-25 16:41:11.900000', 'INT', NULL, 'Enable', 11, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 10, 'enable', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(256, b'0', 'Log', '2021-07-25 16:41:11.900000', 'TEXT', NULL, 'Log', 12, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', NULL, 'log', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(257, b'0', 'JsapiTicket', '2021-07-25 16:41:11.900000', 'VARCHAR', NULL, 'JsapiTicket', 13, NULL, NULL, NULL, b'1', '2021-07-25 16:41:37.922000', 400, 'jsapiTicket', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 37),
(293, b'1', '编号', '2021-07-27 15:46:57.240000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-07-27 15:46:57.240000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 42),
(294, b'0', '名称', '2021-07-27 15:46:57.240000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-07-27 15:46:57.240000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 42),
(295, b'0', '全文索引', '2021-07-27 15:46:57.240000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-07-27 15:46:57.240000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 42),
(296, b'0', '创建时间', '2021-07-27 15:46:57.240000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-07-27 15:46:57.240000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 42),
(297, b'0', '修改时间', '2021-07-27 15:46:57.240000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-07-27 15:46:57.240000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 42),
(298, b'0', 'Url', '2021-07-27 15:46:57.240000', 'VARCHAR', NULL, 'Url', 5, NULL, NULL, NULL, b'1', '2021-07-27 15:46:57.240000', 200, 'url', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 42),
(299, b'0', '用户名', '2021-07-27 15:46:57.240000', 'VARCHAR', NULL, '用户名', 6, NULL, NULL, NULL, b'1', '2021-07-27 15:46:57.240000', 200, 'username', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 42),
(300, b'0', '密码', '2021-07-27 15:46:57.240000', 'VARCHAR', NULL, '密码', 7, NULL, NULL, NULL, b'1', '2021-07-27 15:46:57.240000', 200, 'password', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 42),
(301, b'1', '编号', '2021-07-28 10:56:47.092000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-07-28 10:56:47.092000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', NULL, b'1', 43),
(302, b'0', '名称', '2021-07-28 10:56:47.092000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-07-28 10:56:47.092000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'1', 43),
(303, b'0', '全文索引', '2021-07-28 10:56:47.092000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-07-28 10:56:47.092000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 43),
(304, b'0', '创建时间', '2021-07-28 10:56:47.092000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-07-28 10:56:47.092000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 43),
(305, b'0', '修改时间', '2021-07-28 10:56:47.092000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-07-28 10:56:47.092000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', NULL, b'1', 43),
(306, b'0', '签名', '2021-07-28 10:56:47.092000', 'VARCHAR', NULL, '签名', 5, NULL, NULL, NULL, b'1', '2021-07-28 10:56:47.092000', 200, 'sign', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 43),
(307, b'0', '模板', '2021-07-28 10:56:47.092000', 'VARCHAR', NULL, '模板', 6, NULL, NULL, NULL, b'1', '2021-07-28 10:56:47.092000', 200, 'template', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 43),
(308, b'0', '短信配置编号', '2021-07-28 10:56:47.092000', 'BIGINT', NULL, '短信配置编号', 7, NULL, NULL, NULL, b'1', '2021-07-28 10:56:47.092000', 20, 'smsConfigId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', NULL, b'0', 43),
(432, b'1', '编号', '2021-09-02 15:30:37.086000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-09-06 17:30:59.446000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', 62),
(433, b'0', '名称', '2021-09-02 15:30:37.086000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-09-06 17:30:59.446000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', 62),
(434, b'0', '全文索引', '2021-09-02 15:30:37.086000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-09-06 17:30:59.446000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 62),
(435, b'0', '创建时间', '2021-09-02 15:30:37.086000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-09-06 17:30:59.446000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 62),
(436, b'0', '修改时间', '2021-09-02 15:30:37.086000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-09-06 17:30:59.446000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 62),
(437, b'1', '编号', '2021-09-02 15:34:04.947000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-09-02 15:34:04.947000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', 63),
(438, b'0', '名称', '2021-09-02 15:34:04.947000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-09-02 15:34:04.947000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', 63),
(439, b'0', '全文索引', '2021-09-02 15:34:04.947000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-09-02 15:34:04.947000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 63),
(440, b'0', '创建时间', '2021-09-02 15:34:04.947000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-09-02 15:34:04.947000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 63),
(441, b'0', '修改时间', '2021-09-02 15:34:04.947000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-09-02 15:34:04.947000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 63),
(442, b'0', '模块编号', '2021-09-02 15:34:04.947000', 'BIGINT', NULL, '模块编号', 5, NULL, NULL, NULL, b'1', '2021-09-02 15:34:04.947000', 20, 'moduleId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 63),
(443, b'0', '表编号', '2021-09-02 15:34:04.947000', 'BIGINT', NULL, '表编号', 6, NULL, NULL, NULL, b'1', '2021-09-02 15:34:04.947000', 20, 'tableId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 63),
(461, b'1', '编号', '2021-09-02 15:59:07.090000', 'BIGINT', NULL, '编号', 0, NULL, NULL, 'PRIMARY', b'1', '2021-09-06 16:20:28.739000', NULL, 'id', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(462, b'0', '中文名称', '2021-09-02 15:59:07.090000', 'VARCHAR', NULL, '中文名称', 2, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', 255, 'caption', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', 66),
(463, b'0', '是否创建物理表', '2021-09-02 15:59:07.090000', 'BOOL', NULL, '是否创建物理表', 3, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', NULL, 'createPhysicalTable', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(464, b'0', '创建时间', '2021-09-02 15:59:07.090000', 'DATETIME', NULL, '创建时间', 4, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', 6, 'createdDate', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(465, b'0', '描述', '2021-09-02 15:59:07.090000', 'VARCHAR', NULL, '描述', 5, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', 255, 'description', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(466, b'0', '引擎', '2021-09-02 15:59:07.090000', 'VARCHAR', NULL, '引擎', 6, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', 255, 'engine', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(467, b'0', '修改时间', '2021-09-02 15:59:07.090000', 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', 6, 'lastModifiedDate', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(468, b'0', '英文名称', '2021-09-02 15:59:07.090000', 'VARCHAR', NULL, '英文名称', 1, 'uq_bsm_table_name', 'BTREE', 'UNIQUE', b'1', '2021-09-06 16:20:28.739000', 255, 'name', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', 66),
(469, b'0', '英文复数', '2021-09-02 15:59:07.090000', 'VARCHAR', NULL, '英文复数', 8, 'uq_bsm_table_plural_name', 'BTREE', 'UNIQUE', b'1', '2021-09-06 16:20:28.739000', 255, 'pluralName', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(470, b'0', '物理表名称', '2021-09-02 15:59:07.090000', 'VARCHAR', NULL, '物理表名称', 9, 'uq_bsm_table_table_name', 'BTREE', 'UNIQUE', b'1', '2021-09-06 16:20:28.739000', 255, 'tableName', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(471, b'0', '是否系统表', '2021-09-02 15:59:07.090000', 'BOOL', NULL, '是否系统表', 10, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', NULL, 'systemable', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(473, b'0', '是否只读', '2021-09-06 16:10:31.028000', 'BOOL', NULL, '是否只读', 11, NULL, NULL, NULL, b'1', '2021-09-06 16:20:28.739000', NULL, 'readOnly', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 66),
(489, b'1', '编号', '2021-11-03 11:23:02.177000', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', b'0', '2021-11-03 11:23:02.177000', 20, 'id', b'0', NULL, b'0', NULL, NULL, b'1', b'0', b'0', b'0', 69),
(490, b'0', '名称', '2021-11-03 11:23:02.177000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, b'1', '2021-11-03 11:23:02.177000', 200, 'name', b'0', NULL, b'1', NULL, NULL, b'0', b'1', b'1', b'0', 69),
(491, b'0', '全文索引', '2021-11-03 11:23:02.177000', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', b'0', '2021-11-03 11:23:02.177000', NULL, 'fullTextBody', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 69),
(492, b'0', '创建时间', '2021-11-03 11:23:02.177000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, b'0', '2021-11-03 11:23:02.177000', NULL, 'createdDate', b'0', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 69),
(493, b'0', '修改时间', '2021-11-03 11:23:02.177000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, b'0', '2021-11-03 11:23:02.177000', NULL, 'lastModifiedDate', b'1', NULL, b'0', NULL, NULL, b'0', b'0', b'0', b'0', 69),
(494, b'0', '类型', '2021-11-03 11:23:02.177000', 'VARCHAR', NULL, '类型', 5, NULL, NULL, NULL, b'1', '2021-11-03 11:23:02.177000', 200, 'type', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 69),
(495, b'0', '设备', '2021-11-03 11:23:02.177000', 'VARCHAR', NULL, '设备', 6, NULL, NULL, NULL, b'1', '2021-11-03 11:23:02.177000', 200, 'device', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 69),
(496, b'0', '内容', '2021-11-03 11:23:02.177000', 'LONGTEXT', NULL, '内容', 7, NULL, NULL, NULL, b'1', '2021-11-03 11:23:02.177000', NULL, 'body', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 69),
(497, b'0', '表编号', '2021-11-03 11:23:02.177000', 'BIGINT', NULL, '表编号', 8, NULL, NULL, NULL, b'1', '2021-11-03 11:23:02.177000', 20, 'tableId', b'1', NULL, b'1', NULL, NULL, b'0', b'1', b'0', b'0', 69);

-- --------------------------------------------------------

--
-- Table structure for table `ca_meta_index`
--

CREATE TABLE `ca_meta_index` (
  `id` bigint NOT NULL,
  `caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `indexStorage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `indexType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastModifiedDate` datetime(6) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tableId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_meta_index`
--

INSERT INTO `ca_meta_index` (`id`, `caption`, `createdDate`, `description`, `indexStorage`, `indexType`, `lastModifiedDate`, `name`, `tableId`) VALUES
(12, '表页面类型', '2021-11-03 11:23:02.222000', '表页面类型唯一', 'BTREE', 'UNIQUE', '2021-11-03 11:23:02.222000', 'tableTypeIndex', 69);

-- --------------------------------------------------------

--
-- Table structure for table `ca_meta_index_line`
--

CREATE TABLE `ca_meta_index_line` (
  `id` bigint NOT NULL,
  `columnId` bigint DEFAULT NULL,
  `indexId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_meta_index_line`
--

INSERT INTO `ca_meta_index_line` (`id`, `columnId`, `indexId`) VALUES
(20, 494, 12),
(21, 497, 12);

-- --------------------------------------------------------

--
-- Table structure for table `ca_meta_sequence`
--

CREATE TABLE `ca_meta_sequence` (
  `id` bigint NOT NULL,
  `caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `currentTime` bit(1) DEFAULT NULL,
  `cycle` bit(1) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `format` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `incrementBy` bigint DEFAULT NULL,
  `lastModifiedDate` datetime(6) DEFAULT NULL,
  `maxValue` bigint DEFAULT NULL,
  `minValue` bigint DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nextValue` bigint DEFAULT NULL,
  `sequenceType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_meta_sequence`
--

INSERT INTO `ca_meta_sequence` (`id`, `caption`, `createdDate`, `currentTime`, `cycle`, `description`, `format`, `incrementBy`, `lastModifiedDate`, `maxValue`, `minValue`, `name`, `nextValue`, `sequenceType`) VALUES
(1, '角色编码', '2021-02-01 11:17:34.807000', b'0', NULL, NULL, 'ROLE_%09d', 1, '2021-02-05 17:53:05.432000', 999999999, 1, 'roleCode', 8, 'STRING'),
(2, '资源编码', '2021-02-02 10:06:15.140000', b'0', NULL, NULL, 'RESOURCE_%09d', 1, '2021-02-02 10:06:15.140000', 999999999, 1, 'resourceCode', 8, 'STRING'),
(3, '会员流水号', '2021-07-25 21:24:48.973000', b'0', NULL, NULL, '%018d', 1, '2021-07-25 21:28:07.858000', 9999999999, 1, 'membershipCode', 17, 'STRING');

-- --------------------------------------------------------

--
-- Table structure for table `ca_meta_table`
--

CREATE TABLE `ca_meta_table` (
  `id` bigint NOT NULL,
  `caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createPhysicalTable` bit(1) DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `engine` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastModifiedDate` datetime(6) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pluralName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tableName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `systemable` bit(1) DEFAULT NULL,
  `readOnly` bit(1) DEFAULT NULL COMMENT '是否只读'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_meta_table`
--

INSERT INTO `ca_meta_table` (`id`, `caption`, `createPhysicalTable`, `createdDate`, `description`, `engine`, `lastModifiedDate`, `name`, `pluralName`, `tableName`, `systemable`, `readOnly`) VALUES
(1, '用户', b'1', '2020-12-23 17:06:29.161000', '用户', 'INNODB', '2021-08-05 10:52:53.955000', 'user', 'users', 'spring_user', b'1', b'0'),
(2, '角色', b'1', '2021-02-01 10:51:21.324000', '角色', 'INNODB', '2021-08-05 10:53:53.426000', 'role', 'roles', 'spring_role', b'1', b'0'),
(3, '资源', b'1', '2021-02-01 10:54:05.668000', '资源', 'INNODB', '2021-02-05 17:52:19.025000', 'resource', 'resources', 'spring_resource', b'1', b'0'),
(4, '用户角色行', b'1', '2021-02-01 11:11:15.312000', '用户角色行', 'INNODB', '2021-02-01 11:11:15.312000', 'userRoleLine', 'userRoleLines', 'ca_userRoleLine', b'1', b'0'),
(5, '角色资源行', b'1', '2021-02-01 11:27:24.848000', '角色资源行', 'INNODB', '2021-02-03 16:43:36.315000', 'roleResourceLine', 'roleResourceLines', 'ca_roleResourceLine', b'1', b'0'),
(19, '省', b'1', '2021-02-07 11:57:10.121000', '', 'INNODB', '2021-02-07 15:25:57.159000', 'province', 'provinces', 'ca_province', b'1', b'0'),
(20, '市', b'1', '2021-02-07 11:59:06.946000', '', 'INNODB', '2021-02-08 16:21:31.003000', 'city', 'cities', 'ca_city', b'1', b'0'),
(22, '区', b'1', '2021-02-07 15:25:23.709000', '', 'INNODB', '2021-08-05 10:38:57.082000', 'district', 'districts', 'ca_district', b'1', b'0'),
(24, '文件', b'1', '2021-02-08 11:09:32.053000', '', 'INNODB', '2021-02-09 14:33:24.635000', 'file', 'files', 'ca_file', b'1', b'0'),
(34, '目录', b'1', '2021-02-09 14:21:40.991000', '', 'INNODB', '2021-02-09 14:21:40.991000', 'folder', 'folders', 'ca_folder', b'1', b'0'),
(37, '微信配置', b'1', '2021-07-25 16:41:11.888000', '', 'INNODB', '2021-07-25 16:41:37.914000', 'weixinConfig', 'weixinConfigs', 'ca_weixinConfig', b'1', b'0'),
(42, '短信配置', b'1', '2021-07-27 15:46:57.227000', '', 'INNODB', '2021-07-27 15:46:57.227000', 'smsConfig', 'smsConfigs', 'ca_smsConfig', b'1', b'0'),
(43, '短信模板', b'1', '2021-07-28 10:56:47.080000', '', 'INNODB', '2021-07-28 10:56:47.080000', 'smsTemplate', 'smsTemplates', 'ca_smsTemplate', b'1', b'0'),
(62, '模块', b'1', '2021-09-02 15:30:37.082000', '', 'INNODB', '2021-09-06 17:30:59.436000', 'module', 'modules', 'ca_sys_module', b'1', b'0'),
(63, '模块行', b'1', '2021-09-02 15:34:04.944000', '', 'INNODB', '2021-09-02 15:34:04.944000', 'moduleLine', 'moduleLines', 'ca_sys_moduleLine', b'1', b'0'),
(66, '表', b'1', '2021-09-02 15:59:07.087000', '', 'INNODB', '2021-09-06 16:20:28.729000', 'table', 'tables', 'ca_meta_table', b'1', b'1'),
(69, '表页面', b'1', '2021-11-03 11:23:02.166000', '', 'INNODB', '2021-11-03 11:23:02.166000', 'tableFormBuilder', 'tableFormBuilders', 'ca_tableFormBuilder', b'1', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `ca_meta_table_relation`
--

CREATE TABLE `ca_meta_table_relation` (
  `id` bigint NOT NULL,
  `caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdDate` datetime(6) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastModifiedDate` datetime(6) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `relationType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fromColumnId` bigint DEFAULT NULL,
  `fromTableId` bigint DEFAULT NULL,
  `toColumnId` bigint DEFAULT NULL,
  `toTableId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_meta_table_relation`
--

INSERT INTO `ca_meta_table_relation` (`id`, `caption`, `createdDate`, `description`, `lastModifiedDate`, `name`, `relationType`, `fromColumnId`, `fromTableId`, `toColumnId`, `toTableId`) VALUES
(1, '角色行', '2021-02-01 11:11:44.048000', '角色行', '2021-02-01 11:13:05.487000', 'roleLines', 'OneToMany', 1, 1, 40, 4),
(2, '角色', '2021-02-01 11:12:22.421000', '角色', '2021-02-01 11:12:22.421000', 'role', 'ManyToOne', 41, 4, 20, 2),
(3, '资源行', '2021-02-01 11:29:19.889000', '资源行', '2021-02-01 11:30:11.780000', 'resourceLines', 'OneToMany', 20, 2, 47, 5),
(4, '资源', '2021-02-01 11:30:41.473000', '资源', '2021-02-01 11:30:41.473000', 'resource', 'ManyToOne', 48, 5, 27, 3),
(5, '城市', '2021-02-07 11:59:30.554000', NULL, '2021-02-07 11:59:30.554000', 'cities', 'OneToMany', 155, 19, 167, 20),
(6, '区', '2021-02-07 15:26:36.647000', NULL, '2021-02-07 15:26:36.647000', 'districts', 'OneToMany', 161, 20, 182, 22),
(7, '头像', '2021-02-08 16:29:32.448000', NULL, '2021-02-08 16:29:32.448000', 'avatar', 'ManyToOne', 219, 1, 192, 24),
(8, '子目录', '2021-02-09 14:23:10.748000', NULL, '2021-02-09 14:23:10.748000', 'childrenFolders', 'OneToMany', 220, 34, 225, 34),
(13, '文件', '2021-02-09 14:33:59.185000', NULL, '2021-02-09 14:33:59.185000', 'files', 'OneToMany', 220, 34, 226, 24),
(21, '模板', '2021-07-28 10:57:17.116000', NULL, '2021-07-28 11:00:32.970000', 'templates', 'OneToMany', 293, 42, 308, 43),
(22, '模块行', '2021-09-02 16:03:43.184000', NULL, '2021-09-02 16:03:43.184000', 'moduleLines', 'OneToMany', 432, 62, 442, 63),
(23, '表', '2021-09-02 16:04:27.912000', NULL, '2021-09-02 16:04:27.912000', 'table', 'ManyToOne', 443, 63, 461, 66);

-- --------------------------------------------------------

--
-- Table structure for table `ca_province`
--

CREATE TABLE `ca_province` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ca_roleResourceLine`
--

CREATE TABLE `ca_roleResourceLine` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `roleId` bigint DEFAULT NULL,
  `resourceId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_roleResourceLine`
--

INSERT INTO `ca_roleResourceLine` (`id`, `name`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `roleId`, `resourceId`) VALUES
(11, '业务数据资源', NULL, '2021-07-27 16:57:16', NULL, 20, 12),
(12, '业务数据数字资源', NULL, '2021-07-27 16:57:16', NULL, 20, 13);

-- --------------------------------------------------------

--
-- Table structure for table `ca_smsConfig`
--

CREATE TABLE `ca_smsConfig` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Url',
  `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信配置';

-- --------------------------------------------------------

--
-- Table structure for table `ca_smsTemplate`
--

CREATE TABLE `ca_smsTemplate` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `sign` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签名',
  `template` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模板',
  `smsConfigId` bigint DEFAULT NULL COMMENT '短信配置编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信模板';

-- --------------------------------------------------------

--
-- Table structure for table `ca_sys_module`
--

CREATE TABLE `ca_sys_module` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模块';

--
-- Dumping data for table `ca_sys_module`
--

INSERT INTO `ca_sys_module` (`id`, `name`, `fullTextBody`, `createdDate`, `lastModifiedDate`) VALUES
(1, '字典', '字典 bg-teal', '2021-09-02 16:05:41', '2021-09-03 16:51:05'),
(2, '文件', '文件 bg-primary', '2021-09-02 16:07:22', '2021-09-03 16:50:40'),
(3, '用户管理', '用户管理 bg-purple', '2021-09-03 16:47:49', '2021-09-03 16:49:51'),
(4, '微信', '微信', '2021-09-03 17:21:09', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ca_sys_moduleLine`
--

CREATE TABLE `ca_sys_moduleLine` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `moduleId` bigint DEFAULT NULL COMMENT '模块编号',
  `tableId` bigint DEFAULT NULL COMMENT '表编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模块行';

--
-- Dumping data for table `ca_sys_moduleLine`
--

INSERT INTO `ca_sys_moduleLine` (`id`, `name`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `moduleId`, `tableId`) VALUES
(1, '省', '省 1 19', '2021-09-02 16:05:42', '2021-09-03 16:51:05', 1, 19),
(2, '市', '市 1 20', '2021-09-02 16:05:42', '2021-09-03 16:51:05', 1, 20),
(3, '区', '区 1 22', '2021-09-02 16:05:42', '2021-09-03 16:51:05', 1, 22),
(4, '文件', '文件 2 24', '2021-09-02 16:07:22', '2021-09-03 16:50:40', 2, 24),
(5, '目录', '目录 2 34', '2021-09-02 16:07:22', '2021-09-03 16:50:40', 2, 34),
(6, '用户', '用户 3 1', '2021-09-03 16:47:49', '2021-09-03 16:49:51', 3, 1),
(7, '角色', '角色 3 2', '2021-09-03 16:47:49', '2021-09-06 16:30:46', 3, 2),
(8, '资源', '资源 3 3', '2021-09-03 16:47:49', '2021-09-03 16:49:51', 3, 3),
(9, '用户角色行', '用户角色行 3 4', '2021-09-03 16:47:49', '2021-09-03 16:49:51', 3, 4),
(10, '角色资源行', '角色资源行 3 5', '2021-09-03 16:47:49', '2021-09-03 16:49:51', 3, 5),
(11, '微信配置', '微信配置 4 37', '2021-09-03 17:21:09', NULL, 4, 37);

-- --------------------------------------------------------

--
-- Table structure for table `ca_tableFormBuilder`
--

CREATE TABLE `ca_tableFormBuilder` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `device` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备',
  `body` longtext COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `tableId` bigint DEFAULT NULL COMMENT '表编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='表页面';

-- --------------------------------------------------------

--
-- Table structure for table `ca_userRoleLine`
--

CREATE TABLE `ca_userRoleLine` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `userId` bigint NOT NULL,
  `roleId` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ca_userRoleLine`
--

INSERT INTO `ca_userRoleLine` (`id`, `name`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `userId`, `roleId`) VALUES
(15, '超级管理员', NULL, '2021-07-27 15:53:21', NULL, 1, 11);

-- --------------------------------------------------------

--
-- Table structure for table `ca_weixinConfig`
--

CREATE TABLE `ca_weixinConfig` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '全文索引',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `appId` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'AppId',
  `appSecret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'AppSecret',
  `token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Token',
  `encodingAESKey` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'EncodingAESKey',
  `accessToken` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'AccessToken',
  `expiresIn` datetime DEFAULT NULL COMMENT 'ExpiresIn',
  `enable` int DEFAULT NULL COMMENT 'Enable',
  `log` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Log',
  `jsapiTicket` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JsapiTicket'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新建表10';

-- --------------------------------------------------------

--
-- Table structure for table `persistent_logins`
--

CREATE TABLE `persistent_logins` (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_used` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `spring_resource`
--

CREATE TABLE `spring_resource` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `action` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `spring_resource`
--

INSERT INTO `spring_resource` (`id`, `name`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `url`, `remark`, `code`, `action`) VALUES
(12, '业务数据', '业务数据 RESOURCE_BUSINESS /api/business/** *', '2021-07-27 16:02:17', '2021-07-27 16:51:43', '/api/business/**', NULL, 'RESOURCE_BUSINESS', '*'),
(13, '业务数据数字', '业务数据数字 RESOURCE_BUSINESS_COUNT /api/business/**/count *', '2021-07-27 16:52:29', '2021-07-27 16:52:41', '/api/business/**/count', NULL, 'RESOURCE_BUSINESS_COUNT', '*'),
(14, '用户', '用户 RESOURCE_USER /api/business/user** *', '2021-07-27 16:58:59', '2021-07-27 17:02:03', '/api/business/user**', NULL, 'RESOURCE_USER', '*'),
(15, '角色资源', '角色资源 RESOURCE_ROLE /api/business/role** *', '2021-07-27 17:26:46', '2021-07-27 17:27:24', '/api/business/role**', NULL, 'RESOURCE_ROLE', '*'),
(16, '资源资源', '资源资源 RESOURCE_RESOURCE /api/business/resource** *', '2021-07-27 17:28:06', '2021-07-27 17:29:03', '/api/business/resource**', NULL, 'RESOURCE_RESOURCE', '*');

-- --------------------------------------------------------

--
-- Table structure for table `spring_role`
--

CREATE TABLE `spring_role` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `createdDate` datetime NOT NULL,
  `lastModifiedDate` datetime DEFAULT NULL,
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `spring_role`
--

INSERT INTO `spring_role` (`id`, `name`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `code`, `remark`) VALUES
(11, '超级管理员', '超级管理员 ROLE_SUPER_ADMIN 超级管理员', '2021-02-01 13:28:37', '2021-06-07 16:50:45', 'ROLE_SUPER_ADMIN', '超级管理员'),
(20, '业务数据角色', '业务数据角色 ROLE_BUSINESS 业务数据用户', '2021-07-27 16:48:56', '2021-07-27 16:57:15', 'ROLE_BUSINESS', '业务数据用户');

-- --------------------------------------------------------

--
-- Table structure for table `SPRING_SESSION`
--

CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `SPRING_SESSION`
--

-- --------------------------------------------------------

--
-- Table structure for table `SPRING_SESSION_ATTRIBUTES`
--

CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `SPRING_SESSION_ATTRIBUTES`
--


-- --------------------------------------------------------

--
-- Table structure for table `spring_user`
--

CREATE TABLE `spring_user` (
  `id` bigint NOT NULL,
  `openId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `realname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cleartextPwd` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `accountNonExpired` bit(1) NOT NULL DEFAULT b'1',
  `accountNonLocked` bit(1) NOT NULL DEFAULT b'1',
  `credentialsNonExpired` bit(1) NOT NULL DEFAULT b'1',
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullTextBody` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `fileId` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `spring_user`
--

INSERT INTO `spring_user` (`id`, `openId`, `username`, `realname`, `mobile`, `email`, `password`, `cleartextPwd`, `token`, `enabled`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`, `createdDate`, `lastModifiedDate`, `name`, `fullTextBody`, `fileId`) VALUES
(1, NULL, 'superadmin', '超级管理员', '11111111111', NULL, '$2a$10$R9HWotqpXzmLOU0aYlFkquxcs0jq7f1raKHHnsbfT51gM9TF.2q7q', NULL, '1094a2c5e57c4c7fa3922d69b012cfa7', b'1', b'1', b'1', b'1', '2020-07-21 15:00:24', '2021-07-27 15:53:21', '超级管理员', '超级管理员 superadmin 超级管理员 11111111111 true true true true', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ca_city`
--
ALTER TABLE `ca_city`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_city` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_district`
--
ALTER TABLE `ca_district`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_district` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_file`
--
ALTER TABLE `ca_file`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_file` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_folder`
--
ALTER TABLE `ca_folder`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_folder` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_meta_column`
--
ALTER TABLE `ca_meta_column`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_bsm_column_name` (`tableId`,`name`),
  ADD UNIQUE KEY `uq_bsm_column_index_name` (`tableId`,`indexName`),
  ADD KEY `fk_bsm_column_seq_id` (`seqId`);

--
-- Indexes for table `ca_meta_index`
--
ALTER TABLE `ca_meta_index`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_bsm_index_name` (`tableId`,`name`);

--
-- Indexes for table `ca_meta_index_line`
--
ALTER TABLE `ca_meta_index_line`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_bsm_index_line_column_id` (`columnId`),
  ADD KEY `fk_bsm_index_line_index_id` (`indexId`);

--
-- Indexes for table `ca_meta_sequence`
--
ALTER TABLE `ca_meta_sequence`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_bsm_sequence_name` (`name`);

--
-- Indexes for table `ca_meta_table`
--
ALTER TABLE `ca_meta_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_bsm_table_name` (`name`),
  ADD UNIQUE KEY `uq_bsm_table_plural_name` (`pluralName`),
  ADD UNIQUE KEY `uq_bsm_table_table_name` (`tableName`);

--
-- Indexes for table `ca_meta_table_relation`
--
ALTER TABLE `ca_meta_table_relation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_ca_table_relation` (`fromTableId`,`toTableId`,`relationType`,`fromColumnId`,`toColumnId`) USING BTREE,
  ADD KEY `fk_bsm_table_relation_from_table_id` (`fromTableId`),
  ADD KEY `fk_bsm_table_relation_to_table_id` (`toTableId`),
  ADD KEY `fk_bsm_table_relation_from_column_id` (`fromColumnId`),
  ADD KEY `fk_bsm_table_relation_to_column_id` (`toColumnId`);

--
-- Indexes for table `ca_province`
--
ALTER TABLE `ca_province`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_province` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_roleResourceLine`
--
ALTER TABLE `ca_roleResourceLine`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_roleResourceLine` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_smsConfig`
--
ALTER TABLE `ca_smsConfig`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_smsConfig` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_smsTemplate`
--
ALTER TABLE `ca_smsTemplate`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_smsTemplate` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_sys_module`
--
ALTER TABLE `ca_sys_module`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_sys_module` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_sys_moduleLine`
--
ALTER TABLE `ca_sys_moduleLine`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_sys_moduleLine` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_tableFormBuilder`
--
ALTER TABLE `ca_tableFormBuilder`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tableTypeIndex` (`type`,`tableId`) USING BTREE COMMENT '表页面类型';
ALTER TABLE `ca_tableFormBuilder` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_userRoleLine`
--
ALTER TABLE `ca_userRoleLine`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_userRoleLine` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `ca_weixinConfig`
--
ALTER TABLE `ca_weixinConfig`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `ca_weixinConfig` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `persistent_logins`
--
ALTER TABLE `persistent_logins`
  ADD PRIMARY KEY (`series`);

--
-- Indexes for table `spring_resource`
--
ALTER TABLE `spring_resource`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_spring_resource_code` (`code`),
  ADD UNIQUE KEY `uq_spring_resource_url` (`url`);
ALTER TABLE `spring_resource` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `spring_role`
--
ALTER TABLE `spring_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_spring_role_code` (`code`);
ALTER TABLE `spring_role` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- Indexes for table `SPRING_SESSION`
--
ALTER TABLE `SPRING_SESSION`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  ADD KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  ADD KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`);

--
-- Indexes for table `SPRING_SESSION_ATTRIBUTES`
--
ALTER TABLE `SPRING_SESSION_ATTRIBUTES`
  ADD PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`);

--
-- Indexes for table `spring_user`
--
ALTER TABLE `spring_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_spring_user_username` (`username`),
  ADD UNIQUE KEY `uq_spring_user_openid` (`openId`),
  ADD UNIQUE KEY `uq_spring_user_mobile` (`mobile`),
  ADD UNIQUE KEY `uq_spring_user_email` (`email`);
ALTER TABLE `spring_user` ADD FULLTEXT KEY `ft_fulltext_body` (`fullTextBody`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ca_city`
--
ALTER TABLE `ca_city`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_district`
--
ALTER TABLE `ca_district`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_file`
--
ALTER TABLE `ca_file`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_folder`
--
ALTER TABLE `ca_folder`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_meta_column`
--
ALTER TABLE `ca_meta_column`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_meta_index`
--
ALTER TABLE `ca_meta_index`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_meta_index_line`
--
ALTER TABLE `ca_meta_index_line`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_meta_sequence`
--
ALTER TABLE `ca_meta_sequence`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_meta_table`
--
ALTER TABLE `ca_meta_table`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_meta_table_relation`
--
ALTER TABLE `ca_meta_table_relation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_province`
--
ALTER TABLE `ca_province`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_roleResourceLine`
--
ALTER TABLE `ca_roleResourceLine`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_smsConfig`
--
ALTER TABLE `ca_smsConfig`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_smsTemplate`
--
ALTER TABLE `ca_smsTemplate`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_sys_module`
--
ALTER TABLE `ca_sys_module`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_sys_moduleLine`
--
ALTER TABLE `ca_sys_moduleLine`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_tableFormBuilder`
--
ALTER TABLE `ca_tableFormBuilder`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号';

--
-- AUTO_INCREMENT for table `ca_userRoleLine`
--
ALTER TABLE `ca_userRoleLine`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `ca_weixinConfig`
--
ALTER TABLE `ca_weixinConfig`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号', AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `spring_resource`
--
ALTER TABLE `spring_resource`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `spring_role`
--
ALTER TABLE `spring_role`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT for table `spring_user`
--
ALTER TABLE `spring_user`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ca_meta_column`
--
ALTER TABLE `ca_meta_column`
  ADD CONSTRAINT `fk_bsm_column_seq_id` FOREIGN KEY (`seqId`) REFERENCES `ca_meta_sequence` (`id`),
  ADD CONSTRAINT `fk_bsm_column_table_id` FOREIGN KEY (`tableId`) REFERENCES `ca_meta_table` (`id`);

--
-- Constraints for table `ca_meta_index`
--
ALTER TABLE `ca_meta_index`
  ADD CONSTRAINT `fk_bsm_index_table_id` FOREIGN KEY (`tableId`) REFERENCES `ca_meta_table` (`id`);

--
-- Constraints for table `ca_meta_index_line`
--
ALTER TABLE `ca_meta_index_line`
  ADD CONSTRAINT `fk_bsm_index_line_column_id` FOREIGN KEY (`columnId`) REFERENCES `ca_meta_column` (`id`),
  ADD CONSTRAINT `fk_bsm_index_line_index_id` FOREIGN KEY (`indexId`) REFERENCES `ca_meta_index` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `ca_meta_table_relation`
--
ALTER TABLE `ca_meta_table_relation`
  ADD CONSTRAINT `fk_bsm_table_relation_from_column_id` FOREIGN KEY (`fromColumnId`) REFERENCES `ca_meta_column` (`id`),
  ADD CONSTRAINT `fk_bsm_table_relation_from_table_id` FOREIGN KEY (`fromTableId`) REFERENCES `ca_meta_table` (`id`),
  ADD CONSTRAINT `fk_bsm_table_relation_to_column_id` FOREIGN KEY (`toColumnId`) REFERENCES `ca_meta_column` (`id`),
  ADD CONSTRAINT `fk_bsm_table_relation_to_table_id` FOREIGN KEY (`toTableId`) REFERENCES `ca_meta_table` (`id`);

--
-- Constraints for table `SPRING_SESSION_ATTRIBUTES`
--
ALTER TABLE `SPRING_SESSION_ATTRIBUTES`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
