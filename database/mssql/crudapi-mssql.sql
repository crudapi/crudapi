DROP TABLE IF EXISTS "ca_meta_table_relation";
DROP TABLE IF EXISTS "ca_meta_index_line";
DROP TABLE IF EXISTS "ca_meta_index";
DROP TABLE IF EXISTS "ca_meta_column";
DROP TABLE IF EXISTS "ca_meta_table";
DROP TABLE IF EXISTS "ca_meta_sequence";

DROP TABLE IF EXISTS "ca_userRoleLine";
DROP TABLE IF EXISTS "ca_roleResourceLine";
DROP TABLE IF EXISTS "spring_role";
DROP TABLE IF EXISTS "spring_resource";
DROP TABLE IF EXISTS "spring_user";
DROP TABLE IF EXISTS "persistent_logins";
DROP TABLE IF EXISTS SPRING_SESSION_ATTRIBUTES;
DROP TABLE IF EXISTS SPRING_SESSION;

DROP TABLE IF EXISTS "ca_weixinConfig";
DROP TABLE IF EXISTS "ca_smsTemplate";
DROP TABLE IF EXISTS "ca_smsConfig";
DROP TABLE IF EXISTS "ca_folder";
DROP TABLE IF EXISTS "ca_file";
DROP TABLE IF EXISTS "ca_district";
DROP TABLE IF EXISTS "ca_city";
DROP TABLE IF EXISTS "ca_province";
DROP TABLE IF EXISTS "ca_tableFormBuilder";
DROP TABLE IF EXISTS "ca_sys_moduleLine";
DROP TABLE IF EXISTS "ca_sys_module";

CREATE TABLE "ca_meta_sequence" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "caption" NVARCHAR(255) DEFAULT NULL,
  "createdDate" DATETIME DEFAULT NULL,
  "currentTime" BIT DEFAULT NULL,
  "cycle" BIT DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "format" VARCHAR(255) DEFAULT NULL,
  "incrementBy" BIGINT DEFAULT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "maxValue" BIGINT DEFAULT NULL,
  "minValue" BIGINT DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "nextValue" BIGINT DEFAULT NULL,
  "sequenceType" NVARCHAR(255) DEFAULT NULL
);

ALTER TABLE "ca_meta_sequence" ADD CONSTRAINT "uq_bsm_sequence_name" UNIQUE("name");

SET IDENTITY_INSERT "ca_meta_sequence" ON;
INSERT INTO "ca_meta_sequence" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(1, '角色编码', '2021-02-01 11:17:34', 0, NULL, NULL, 'ROLE_%09d', 1, '2021-02-05 17:53:05', 999999999, 1, 'roleCode', 8, 'STRING'),
(2, '资源编码', '2021-02-02 10:06:15', 0, NULL, NULL, 'RESOURCE_%09d', 1, '2021-02-02 10:06:15', 999999999, 1, 'resourceCode', 8, 'STRING'),
(3, '会员流水号', '2021-07-25 21:24:48', 0, NULL, NULL, '%018d', 1, '2021-07-25 21:28:07', 9999999999, 1, 'membershipCode', 17, 'STRING');
SET IDENTITY_INSERT "ca_meta_sequence" OFF;

CREATE TABLE "ca_meta_table" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createPhysicalTable" BIT DEFAULT NULL,
  "createdDate" DATETIME DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "engine" VARCHAR(255) DEFAULT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "pluralName" VARCHAR(255) DEFAULT NULL,
  "tableName" VARCHAR(255) DEFAULT NULL,
  "systemable" BIT DEFAULT NULL,
  "readOnly" BIT DEFAULT NULL
);

ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_name" UNIQUE("name");
ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_plural_name" UNIQUE("pluralName");
ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_table_name" UNIQUE("tableName");

SET IDENTITY_INSERT "ca_meta_table" ON;
INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1, '用户', 1, '2020-12-23 17:06:29', '用户', 'INNODB', '2021-08-05 10:52:53', 'user', 'users', 'spring_user', 1, 0),
(2, '角色', 1, '2021-02-01 10:51:21', '角色', 'INNODB', '2021-08-05 10:53:53', 'role', 'roles', 'spring_role', 1, 0),
(3, '资源', 1, '2021-02-01 10:54:05', '资源', 'INNODB', '2021-02-05 17:52:19', 'resource', 'resources', 'spring_resource', 1, 0),
(4, '用户角色行', 1, '2021-02-01 11:11:15', '用户角色行', 'INNODB', '2021-02-01 11:11:15', 'userRoleLine', 'userRoleLines', 'ca_userRoleLine', 1, 0),
(5, '角色资源行', 1, '2021-02-01 11:27:24', '角色资源行', 'INNODB', '2021-02-03 16:43:36', 'roleResourceLine', 'roleResourceLines', 'ca_roleResourceLine', 1, 0),
(19, '省', 1, '2021-02-07 11:57:10', '', 'INNODB', '2021-02-07 15:25:57', 'province', 'provinces', 'ca_province', 1, 0),
(20, '市', 1, '2021-02-07 11:59:06', '', 'INNODB', '2021-02-08 16:21:31', 'city', 'cities', 'ca_city', 1, 0),
(22, '区', 1, '2021-02-07 15:25:23', '', 'INNODB', '2021-08-05 10:38:57', 'district', 'districts', 'ca_district', 1, 0),
(24, '文件', 1, '2021-02-08 11:09:32', '', 'INNODB', '2021-02-09 14:33:24', 'file', 'files', 'ca_file', 1, 0),
(34, '目录', 1, '2021-02-09 14:21:40', '', 'INNODB', '2021-02-09 14:21:40', 'folder', 'folders', 'ca_folder', 1, 0),
(37, '微信配置', 1, '2021-07-25 16:41:11', '', 'INNODB', '2021-07-25 16:41:37', 'weixinConfig', 'weixinConfigs', 'ca_weixinConfig', 1, 0),
(42, '短信配置', 1, '2021-07-27 15:46:57', '', 'INNODB', '2021-07-27 15:46:57', 'smsConfig', 'smsConfigs', 'ca_smsConfig', 1, 0),
(43, '短信模板', 1, '2021-07-28 10:56:47', '', 'INNODB', '2021-07-28 10:56:47', 'smsTemplate', 'smsTemplates', 'ca_smsTemplate', 1, 0),
(62, '模块', 1, '2021-09-02 15:30:37', '', 'INNODB', '2021-09-06 17:30:59', 'module', 'modules', 'ca_sys_module', 1, 0),
(63, '模块行', 1, '2021-09-02 15:34:04', '', 'INNODB', '2021-09-02 15:34:04', 'moduleLine', 'moduleLines', 'ca_sys_moduleLine', 1, 0),
(66, '表', 1, '2021-09-02 15:59:07', '', 'INNODB', '2021-09-06 16:20:28', 'table', 'tables', 'ca_meta_table', 1, 1),
(69, '表页面', 1, '2021-11-03 11:23:02', '', 'INNODB', '2021-11-03 11:23:02', 'tableFormBuilder', 'tableFormBuilders', 'ca_tableFormBuilder', 1, 0);
SET IDENTITY_INSERT "ca_meta_table" OFF;

CREATE TABLE "ca_meta_column" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "autoIncrement" BIT DEFAULT NULL,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATETIME DEFAULT NULL,
  "dataType" VARCHAR(255) DEFAULT NULL,
  "defaultValue" VARCHAR(255) DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "displayOrder" INT DEFAULT NULL,
  "indexName" VARCHAR(255) DEFAULT NULL,
  "indexStorage" VARCHAR(255) DEFAULT NULL,
  "indexType" VARCHAR(255) DEFAULT NULL,
  "insertable" BIT DEFAULT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "length" INT DEFAULT NULL,
  "name" VARCHAR(255)  DEFAULT NULL,
  "nullable" BIT DEFAULT NULL,
  "precision" INT DEFAULT NULL,
  "queryable" BIT DEFAULT NULL,
  "scale" INT DEFAULT NULL,
  "seqId" BIGINT DEFAULT NULL,
  "unsigned" BIT DEFAULT NULL,
  "updatable" BIT DEFAULT NULL,
  "displayable" BIT DEFAULT NULL,
  "systemable" BIT DEFAULT NULL,
  "tableId" BIGINT DEFAULT NULL,
  CONSTRAINT "fk_bsm_column_seq_id" FOREIGN KEY ("seqId") REFERENCES "ca_meta_sequence" ("id"),
  CONSTRAINT "fk_bsm_column_table_id" FOREIGN KEY ("tableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_name" UNIQUE("tableId","name");
--ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_index_name" UNIQUE("tableId","indexName");
 
SET IDENTITY_INSERT "ca_meta_column" ON;
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES
(1, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '编号', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 1, 1),
(2, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 1),
(3, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 1),
(4, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 1, 1),
(5, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 1),
(6, 0, 'OPENID', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'OPENID', 5, 'uq_spring_user_openid', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 50, 'openId', 1, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1),
(7, 0, '用户名', '2022-02-28 17:06:29', 'VARCHAR', NULL, '用户名', 6, 'uq_spring_user_username', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 50, 'username', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(8, 0, '真实姓名', '2022-02-28 17:06:29', 'VARCHAR', NULL, '真实姓名', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'realname', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(9, 0, '手机', '2022-02-28 17:06:29', 'VARCHAR', NULL, '手机', 8, 'uq_spring_user_mobile', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 20, 'mobile', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(10, 0, '邮箱', '2022-02-28 17:06:29', 'VARCHAR', NULL, '邮箱', 9, 'uq_spring_user_email', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 200, 'email', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(11, 0, '密码', '2022-02-28 17:06:29', 'PASSWORD', NULL, '密码', 10, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 500, 'password', 0, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1),
(12, 0, '明文密码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '明文密码', 11, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'cleartextPwd', 1, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1),
(13, 0, 'TOKEN', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'TOKEN', 12, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'token', 1, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1),
(14, 0, '启用', '2022-02-28 17:06:29', 'BOOL', 'true', '启用', 13, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 10, 'enabled', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(15, 0, '账号没有过期', '2022-02-28 17:06:29', 'BOOL', 'true', '账号没有过期', 14, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 10, 'accountNonExpired', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(16, 0, '账号没有锁定', '2022-02-28 17:06:29', 'BOOL', 'true', '账号没有锁定', 15, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 10, 'accountNonLocked', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(17, 0, '凭证没有过期', '2022-02-28 17:06:29', 'BOOL', 'true', '凭证没有过期', 16, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 10, 'credentialsNonExpired', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(20, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 1, 2),
(21, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 2),
(22, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 2),
(23, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 1, 2),
(24, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 2),
(25, 0, '编码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '编码', 5, 'uq_spring_role_code', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 200, 'code', 0, NULL, 1, NULL, 1, 0, 1, 0, 0, 2),
(26, 0, '备注', '2022-02-28 17:06:29', 'TEXT', NULL, '备注', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'remark', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 2),
(27, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 3),
(28, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 3),
(29, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 3),
(30, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 3),
(31, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 3),
(33, 0, 'URL表达式', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'URL表达式', 6, 'uq_spring_resource_url', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 200, 'url', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 3),
(34, 0, '备注', '2022-02-28 17:06:29', 'TEXT', NULL, '备注', 8, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'remark', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 3),
(35, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 4),
(36, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 4),
(37, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 4),
(38, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 4),
(39, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 4),
(40, 0, '用户编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '用户编号', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'userId', 0, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 4),
(41, 0, '角色编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '角色编号', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'roleId', 0, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 4),
(42, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 5),
(43, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 5),
(44, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 5),
(45, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 5),
(46, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 5),
(47, 0, '角色编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '角色编号', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'roleId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 5),
(48, 0, '资源编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '资源编号', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'resourceId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 5),
(56, 0, '编码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '编码', 5, 'uq_spring_resource_code', NULL, 'UNIQUE', 1, '2022-02-28 17:06:29', 200, 'code', 1, NULL, 1, NULL, 2, 0, 1, NULL, 0, 3),
(58, 0, '操作', '2022-02-28 17:06:29', 'VARCHAR', NULL, '操作', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'action', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 3),
(155, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 19),
(156, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 19),
(157, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 19),
(158, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 19),
(159, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 19),
(160, 0, '编码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'code', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 19),
(161, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 20),
(162, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 20),
(163, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 20),
(164, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 20),
(165, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 20),
(166, 0, '编码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'code', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 20),
(167, 0, '省编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '省编号', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'provinceId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 20),
(176, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 1, 22),
(177, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 22),
(178, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 22),
(179, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 1, 22),
(180, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 22),
(181, 0, '编码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'code', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 22),
(182, 0, '市编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '市编号', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'cityId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 22),
(192, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 24),
(193, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 24),
(194, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 24),
(195, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 24),
(196, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 24),
(197, 0, '大小', '2022-02-28 17:06:29', 'BIGINT', NULL, '大小', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'size', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24),
(198, 0, 'MD5', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'MD5', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'md5', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24),
(199, 0, '类型', '2022-02-28 17:06:29', 'VARCHAR', NULL, '类型', 8, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'type', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24),
(218, 0, '链接', '2022-02-28 17:06:29', 'ATTACHMENT', NULL, '链接', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 4000, 'url', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24),
(219, 0, '头像文件编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '头像文件编号', 17, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'fileId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1),
(220, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 34),
(221, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 34),
(222, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 34),
(223, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 34),
(224, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 34),
(225, 0, '父目录编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '父目录编号', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'parentFolderId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 34),
(226, 0, '目录编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '目录编号', 9, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'folderId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24),
(244, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 37),
(245, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 37),
(246, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 37),
(247, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 37),
(248, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 37),
(249, 0, 'AppId', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'AppId', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'appId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(250, 0, 'AppSecret', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'AppSecret', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'appSecret', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(251, 0, 'Token', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'Token', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'token', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(252, 0, 'EncodingAESKey', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'EncodingAESKey', 8, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'encodingAESKey', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(253, 0, 'AccessToken', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'AccessToken', 9, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'accessToken', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(254, 0, 'ExpiresIn', '2022-02-28 17:06:29', 'DATETIME', NULL, 'ExpiresIn', 10, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'expiresIn', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(255, 0, 'Enable', '2022-02-28 17:06:29', 'INT', NULL, 'Enable', 11, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 10, 'enable', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(256, 0, 'Log', '2022-02-28 17:06:29', 'TEXT', NULL, 'Log', 12, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'log', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(257, 0, 'JsapiTicket', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'JsapiTicket', 13, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 400, 'jsapiTicket', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37),
(293, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 42),
(294, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 42),
(295, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 42),
(296, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 42),
(297, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 42),
(298, 0, 'Url', '2022-02-28 17:06:29', 'VARCHAR', NULL, 'Url', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'url', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 42),
(299, 0, '用户名', '2022-02-28 17:06:29', 'VARCHAR', NULL, '用户名', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'username', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 42),
(300, 0, '密码', '2022-02-28 17:06:29', 'VARCHAR', NULL, '密码', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'password', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 42),
(301, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 43),
(302, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 43),
(303, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 43),
(304, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 43),
(305, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 43),
(306, 0, '签名', '2022-02-28 17:06:29', 'VARCHAR', NULL, '签名', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'sign', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 43),
(307, 0, '模板', '2022-02-28 17:06:29', 'VARCHAR', NULL, '模板', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'template', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 43),
(308, 0, '短信配置编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '短信配置编号', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'smsConfigId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 43),
(432, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 0, 62),
(433, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 0, 62),
(434, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 62),
(435, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 0, 62),
(436, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 62),
(437, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 0, 63),
(438, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 0, 63),
(439, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 63),
(440, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 0, 63),
(441, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 63),
(442, 0, '模块编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '模块编号', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'moduleId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 63),
(443, 0, '表编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '表编号', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'tableId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 63),
(461, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '编号', 0, NULL, NULL, 'PRIMARY', 1, '2022-02-28 17:06:29', NULL, 'id', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(462, 0, '中文名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '中文名称', 2, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 255, 'caption', 1, NULL, 1, NULL, NULL, 0, 1, 1, 0, 66),
(463, 0, '是否创建物理表', '2022-02-28 17:06:29', 'BOOL', NULL, '是否创建物理表', 3, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'createPhysicalTable', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(464, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 4, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 6, 'createdDate', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(465, 0, '描述', '2022-02-28 17:06:29', 'VARCHAR', NULL, '描述', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 255, 'description', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(466, 0, '引擎', '2022-02-28 17:06:29', 'VARCHAR', NULL, '引擎', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 255, 'engine', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(467, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 6, 'lastModifiedDate', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(468, 0, '英文名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '英文名称', 1, 'uq_bsm_table_name', 'BTREE', 'UNIQUE', 1, '2022-02-28 17:06:29', 255, 'name', 1, NULL, 1, NULL, NULL, 0, 1, 1, 0, 66),
(469, 0, '英文复数', '2022-02-28 17:06:29', 'VARCHAR', NULL, '英文复数', 8, 'uq_bsm_table_plural_name', 'BTREE', 'UNIQUE', 1, '2022-02-28 17:06:29', 255, 'pluralName', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(470, 0, '物理表名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '物理表名称', 9, 'uq_bsm_table_table_name', 'BTREE', 'UNIQUE', 1, '2022-02-28 17:06:29', 255, 'tableName', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(471, 0, '是否系统表', '2022-02-28 17:06:29', 'BOOL', NULL, '是否系统表', 10, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'systemable', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(473, 0, '是否只读', '2022-02-28 17:06:29', 'BOOL', NULL, '是否只读', 11, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'readOnly', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66),
(489, 1, '编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, '2022-02-28 17:06:29', 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 0, 69),
(490, 0, '名称', '2022-02-28 17:06:29', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 0, 69),
(491, 0, '全文索引', '2022-02-28 17:06:29', 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, '2022-02-28 17:06:29', NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 69),
(492, 0, '创建时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 0, 69),
(493, 0, '修改时间', '2022-02-28 17:06:29', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, '2022-02-28 17:06:29', NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 69),
(494, 0, '类型', '2022-02-28 17:06:29', 'VARCHAR', NULL, '类型', 5, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'type', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69),
(495, 0, '设备', '2022-02-28 17:06:29', 'VARCHAR', NULL, '设备', 6, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 200, 'device', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69),
(496, 0, '内容', '2022-02-28 17:06:29', 'LONGTEXT', NULL, '内容', 7, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', NULL, 'body', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69),
(497, 0, '表编号', '2022-02-28 17:06:29', 'BIGINT', NULL, '表编号', 8, NULL, NULL, NULL, 1, '2022-02-28 17:06:29', 20, 'tableId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69);
SET IDENTITY_INSERT "ca_meta_column" OFF;

CREATE TABLE "ca_meta_index" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATETIME DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "indexStorage" VARCHAR(255) DEFAULT NULL,
  "indexType" VARCHAR(255) DEFAULT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "tableId" BIGINT DEFAULT NULL,
  CONSTRAINT "fk_bsm_index_table_id" FOREIGN KEY ("tableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_index" ADD CONSTRAINT "uq_bsm_index_name" UNIQUE("tableId","name");

SET IDENTITY_INSERT "ca_meta_index" ON;
INSERT INTO "ca_meta_index" ("id", "caption", "createdDate", "description", "indexStorage", "indexType", "lastModifiedDate", "name", "tableId") VALUES
(12, '表页面类型', '2021-11-03 11:23:02', '表页面类型唯一', 'BTREE', 'UNIQUE', '2021-11-03 11:23:02', 'tableTypeIndex', 69);
SET IDENTITY_INSERT "ca_meta_index" OFF;

CREATE TABLE "ca_meta_index_line" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "columnId" BIGINT DEFAULT NULL,
  "indexId" BIGINT DEFAULT NULL,
  CONSTRAINT "fk_bsm_index_line_index_id" FOREIGN KEY ("indexId") REFERENCES "ca_meta_index" ("id") ON DELETE CASCADE
);

SET IDENTITY_INSERT "ca_meta_index_line" ON;
INSERT INTO "ca_meta_index_line" ("id", "columnId", "indexId") VALUES
(20, 494, 12),
(21, 497, 12);
SET IDENTITY_INSERT "ca_meta_index_line" OFF;

CREATE TABLE "ca_meta_table_relation" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATETIME DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "relationType" VARCHAR(255) DEFAULT NULL,
  "fromColumnId" BIGINT DEFAULT NULL,
  "fromTableId" BIGINT DEFAULT NULL,
  "toColumnId" BIGINT DEFAULT NULL,
  "toTableId" BIGINT DEFAULT NULL,
  CONSTRAINT "fk_bsm_table_relation_from_column_id" FOREIGN KEY ("fromColumnId") REFERENCES "ca_meta_column" ("id"),
  CONSTRAINT "fk_bsm_table_relation_from_table_id" FOREIGN KEY ("fromTableId") REFERENCES "ca_meta_table" ("id"),
  CONSTRAINT "fk_bsm_table_relation_to_column_id" FOREIGN KEY ("toColumnId") REFERENCES "ca_meta_column" ("id"),
  CONSTRAINT "fk_bsm_table_relation_to_table_id" FOREIGN KEY ("toTableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_table_relation" ADD CONSTRAINT "uq_ca_table_relation" UNIQUE("fromTableId","toTableId","relationType","fromColumnId","toColumnId");

SET IDENTITY_INSERT "ca_meta_table_relation" ON;
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES
(1, '角色行', '2021-02-01 11:11:44', '角色行', '2021-02-01 11:13:05', 'roleLines', 'OneToMany', 1, 1, 40, 4),
(2, '角色', '2021-02-01 11:12:22', '角色', '2021-02-01 11:12:22', 'role', 'ManyToOne', 41, 4, 20, 2),
(3, '资源行', '2021-02-01 11:29:19', '资源行', '2021-02-01 11:30:11', 'resourceLines', 'OneToMany', 20, 2, 47, 5),
(4, '资源', '2021-02-01 11:30:41', '资源', '2021-02-01 11:30:41', 'resource', 'ManyToOne', 48, 5, 27, 3),
(5, '城市', '2021-02-07 11:59:30', NULL, '2021-02-07 11:59:30', 'cities', 'OneToMany', 155, 19, 167, 20),
(6, '区', '2021-02-07 15:26:36', NULL, '2021-02-07 15:26:36', 'districts', 'OneToMany', 161, 20, 182, 22),
(7, '头像', '2021-02-08 16:29:32', NULL, '2021-02-08 16:29:32', 'avatar', 'ManyToOne', 219, 1, 192, 24),
(8, '子目录', '2021-02-09 14:23:10', NULL, '2021-02-09 14:23:10', 'childrenFolders', 'OneToMany', 220, 34, 225, 34),
(13, '文件', '2021-02-09 14:33:59', NULL, '2021-02-09 14:33:59', 'files', 'OneToMany', 220, 34, 226, 24),
(21, '模板', '2021-07-28 10:57:17', NULL, '2021-07-28 11:00:32', 'templates', 'OneToMany', 293, 42, 308, 43),
(22, '模块行', '2021-09-02 16:03:43', NULL, '2021-09-02 16:03:43', 'moduleLines', 'OneToMany', 432, 62, 442, 63),
(23, '表', '2021-09-02 16:04:27', NULL, '2021-09-02 16:04:27', 'table', 'ManyToOne', 443, 63, 461, 66);
SET IDENTITY_INSERT "ca_meta_table_relation" OFF;

CREATE TABLE "spring_resource" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "url" VARCHAR(200) DEFAULT NULL,
  "remark" TEXT,
  "code" VARCHAR(200) DEFAULT NULL,
  "action" VARCHAR(200) DEFAULT NULL
);

ALTER TABLE "spring_resource" ADD CONSTRAINT "uq_spring_resource_code" UNIQUE("code");
ALTER TABLE "spring_resource" ADD CONSTRAINT "uq_spring_resource_url" UNIQUE("url");

SET IDENTITY_INSERT "spring_resource" ON;
INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(12, '业务数据', '业务数据 RESOURCE_BUSINESS /api/business/** *', '2021-07-27 16:02:17', '2021-07-27 16:51:43', '/api/business/**', NULL, 'RESOURCE_BUSINESS', '*'),
(13, '业务数据数字', '业务数据数字 RESOURCE_BUSINESS_COUNT /api/business/**/count *', '2021-07-27 16:52:29', '2021-07-27 16:52:41', '/api/business/**/count', NULL, 'RESOURCE_BUSINESS_COUNT', '*'),
(14, '用户', '用户 RESOURCE_USER /api/business/user** *', '2021-07-27 16:58:59', '2021-07-27 17:02:03', '/api/business/user**', NULL, 'RESOURCE_USER', '*'),
(15, '角色资源', '角色资源 RESOURCE_ROLE /api/business/role** *', '2021-07-27 17:26:46', '2021-07-27 17:27:24', '/api/business/role**', NULL, 'RESOURCE_ROLE', '*'),
(16, '资源资源', '资源资源 RESOURCE_RESOURCE /api/business/resource** *', '2021-07-27 17:28:06', '2021-07-27 17:29:03', '/api/business/resource**', NULL, 'RESOURCE_RESOURCE', '*');
SET IDENTITY_INSERT "spring_resource" OFF;

CREATE TABLE "spring_user" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "openId" VARCHAR(50) DEFAULT NULL,
  "username" VARCHAR(50) NOT NULL,
  "realname" VARCHAR(200) DEFAULT NULL,
  "mobile" VARCHAR(20) DEFAULT NULL,
  "email" VARCHAR(200) DEFAULT NULL,
  "password" VARCHAR(500) NOT NULL,
  "cleartextPwd" VARCHAR(200) DEFAULT NULL,
  "token" VARCHAR(200) DEFAULT NULL,
  "enabled" BIT NOT NULL DEFAULT 1,
  "accountNonExpired" BIT NOT NULL DEFAULT 1,
  "accountNonLocked" BIT NOT NULL DEFAULT 1,
  "credentialsNonExpired" BIT NOT NULL DEFAULT 1,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "fileId" BIGINT DEFAULT NULL
);

ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_username" UNIQUE("username");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_openid" UNIQUE("openId");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_mobile" UNIQUE("mobile");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_email" UNIQUE("email");

SET IDENTITY_INSERT "spring_user" ON;
INSERT INTO "spring_user" ("id", "openId", "username", "realname", "mobile", "email", "password", "cleartextPwd", "token", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "createdDate", "lastModifiedDate", "name", "fullTextBody", "fileId") VALUES
(1, NULL, 'superadmin', '超级管理员', '11111111111', NULL, '$2a$10$R9HWotqpXzmLOU0aYlFkquxcs0jq7f1raKHHnsbfT51gM9TF.2q7q', NULL, '1094a2c5e57c4c7fa3922d69b012cfa7', 1, 1, 1, 1, '2020-07-21 15:00:24', '2021-07-27 15:53:21', '超级管理员', '超级管理员 superadmin 超级管理员 11111111111 1 1 1 1', NULL);
SET IDENTITY_INSERT "spring_user" OFF;

CREATE TABLE "spring_role" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "code" VARCHAR(200) NOT NULL,
  "remark" TEXT
);

ALTER TABLE "spring_role" ADD CONSTRAINT "uq_spring_role_code" UNIQUE("code");

SET IDENTITY_INSERT "spring_role" ON;
INSERT INTO "spring_role" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "code", "remark") VALUES
(11, '超级管理员', '超级管理员 ROLE_SUPER_ADMIN 超级管理员', '2021-02-01 13:28:37', '2021-06-07 16:50:45', 'ROLE_SUPER_ADMIN', '超级管理员'),
(20, '业务数据角色', '业务数据角色 ROLE_BUSINESS 业务数据用户', '2021-07-27 16:48:56', '2021-07-27 16:57:15', 'ROLE_BUSINESS', '业务数据用户');
SET IDENTITY_INSERT "spring_role" OFF;

CREATE TABLE "ca_roleResourceLine" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "roleId" BIGINT DEFAULT NULL,
  "resourceId" BIGINT DEFAULT NULL
);

SET IDENTITY_INSERT "ca_roleResourceLine" ON;
INSERT INTO "ca_roleResourceLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "roleId", "resourceId") VALUES
(11, '业务数据资源', NULL, '2021-07-27 16:57:16', NULL, 20, 12),
(12, '业务数据数字资源', NULL, '2021-07-27 16:57:16', NULL, 20, 13);
SET IDENTITY_INSERT "ca_roleResourceLine" OFF;

CREATE TABLE "ca_userRoleLine" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "userId" BIGINT NOT NULL,
  "roleId" BIGINT NOT NULL
);

SET IDENTITY_INSERT "ca_userRoleLine" ON;
INSERT INTO "ca_userRoleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "userId", "roleId") VALUES
(15, '超级管理员', NULL, '2021-07-27 15:53:21', NULL, 1, 11);
SET IDENTITY_INSERT "ca_userRoleLine" OFF;

CREATE TABLE "ca_sys_module" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL
);

SET IDENTITY_INSERT "ca_sys_module" ON;
INSERT INTO "ca_sys_module" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate") VALUES
(1, '字典', '字典 bg-teal', '2021-09-02 16:05:41', '2021-09-03 16:51:05'),
(2, '文件', '文件 bg-primary', '2021-09-02 16:07:22', '2021-09-03 16:50:40'),
(3, '用户管理', '用户管理 bg-purple', '2021-09-03 16:47:49', '2021-09-03 16:49:51'),
(4, '微信', '微信', '2021-09-03 17:21:09', NULL);
SET IDENTITY_INSERT "ca_sys_module" OFF;

CREATE TABLE "ca_sys_moduleLine" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY ,
  "name" VARCHAR(200)  NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "moduleId" BIGINT DEFAULT NULL,
  "tableId" BIGINT DEFAULT NULL
);

SET IDENTITY_INSERT "ca_sys_moduleLine" ON;
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES
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
SET IDENTITY_INSERT "ca_sys_moduleLine" OFF;

CREATE TABLE "ca_tableFormBuilder" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "type" VARCHAR(200) DEFAULT NULL,
  "device" VARCHAR(200) DEFAULT NULL,
  "body" TEXT,
  "tableId" BIGINT DEFAULT NULL
);

CREATE TABLE SPRING_SESSION (
  PRIMARY_ID CHAR(36) NOT NULL,
  SESSION_ID CHAR(36) NOT NULL,
  CREATION_TIME BIGINT NOT NULL,
  LAST_ACCESS_TIME BIGINT NOT NULL,
  MAX_INACTIVE_INTERVAL INT NOT NULL,
  EXPIRY_TIME BIGINT NOT NULL,
  PRINCIPAL_NAME VARCHAR(100),
  CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID CHAR(36) NOT NULL,
  ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
  ATTRIBUTE_BYTES IMAGE NOT NULL,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);


CREATE TABLE "persistent_logins" (
  "username" VARCHAR(64) NOT NULL,
  "series" VARCHAR(64) NOT NULL,
  "token" VARCHAR(64) NOT NULL,
  "last_used" DATETIME NOT NULL
);

CREATE TABLE "ca_province" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "code" VARCHAR(200) DEFAULT NULL
) ;


CREATE TABLE "ca_city" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "code" VARCHAR(200) DEFAULT NULL,
  "provinceId" BIGINT DEFAULT NULL
);

CREATE TABLE "ca_district" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT ,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "code" VARCHAR(200) DEFAULT NULL,
  "cityId" BIGINT DEFAULT NULL
);

CREATE TABLE "ca_file" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT ,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "size" BIGINT DEFAULT NULL,
  "md5" VARCHAR(200) DEFAULT NULL,
  "type" VARCHAR(200) DEFAULT NULL,
  "url" VARCHAR(4000) DEFAULT NULL,
  "folderId" BIGINT DEFAULT NULL
);

CREATE TABLE "ca_folder" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT ,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "parentFolderId" BIGINT DEFAULT NULL
) ;

CREATE TABLE "ca_smsConfig" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "url" VARCHAR(200) DEFAULT NULL,
  "username" VARCHAR(200) DEFAULT NULL,
  "password" VARCHAR(200) DEFAULT NULL
);


CREATE TABLE "ca_smsTemplate" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "sign" VARCHAR(200) DEFAULT NULL,
  "template" VARCHAR(200) DEFAULT NULL,
  "smsConfigId" BIGINT DEFAULT NULL
);

CREATE TABLE "ca_weixinConfig" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" TEXT,
  "createdDate" DATETIME NOT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "appId" VARCHAR(200) DEFAULT NULL,
  "appSecret" VARCHAR(200) DEFAULT NULL,
  "token" VARCHAR(200) DEFAULT NULL,
  "encodingAESKey" VARCHAR(200) DEFAULT NULL,
  "accessToken" VARCHAR(200) DEFAULT NULL,
  "expiresIn" DATETIME DEFAULT NULL,
  "enable" INT DEFAULT NULL,
  "log" TEXT,
  "jsapiTicket" VARCHAR(400) DEFAULT NULL
);
