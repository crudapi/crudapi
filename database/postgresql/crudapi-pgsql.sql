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
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "caption" varchar(255) DEFAULT NULL,
  "createdDate" TIMESTAMP(6) DEFAULT NULL,
  "currentTime" bool DEFAULT NULL,
  "cycle" bool DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "format" varchar(255) DEFAULT NULL,
  "incrementBy" bigint DEFAULT NULL,
  "lastModifiedDate" TIMESTAMP(6) DEFAULT NULL,
  "maxValue" bigint DEFAULT NULL,
  "minValue" bigint DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "nextValue" bigint DEFAULT NULL,
  "sequenceType" varchar(255) DEFAULT NULL
);

ALTER TABLE "ca_meta_sequence" ADD CONSTRAINT "uq_bsm_sequence_name" UNIQUE("name");

INSERT INTO "ca_meta_sequence" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(1, '角色编码', '2021-02-01 11:17:34.807000', false, NULL, NULL, 'ROLE_%09d', 1, '2021-02-05 17:53:05.432000', 999999999, 1, 'roleCode', 8, 'STRING'),
(2, '资源编码', '2021-02-02 10:06:15.140000', false, NULL, NULL, 'RESOURCE_%09d', 1, '2021-02-02 10:06:15.140000', 999999999, 1, 'resourceCode', 8, 'STRING'),
(3, '会员流水号', '2021-07-25 21:24:48.973000', false, NULL, NULL, '%018d', 1, '2021-07-25 21:28:07.858000', 9999999999, 1, 'membershipCode', 17, 'STRING');

CREATE TABLE "ca_meta_table" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "caption" varchar(255) DEFAULT NULL,
  "createPhysicalTable" bool DEFAULT NULL,
  "createdDate" TIMESTAMP(6) DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "engine" varchar(255) DEFAULT NULL,
  "lastModifiedDate" TIMESTAMP(6) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "pluralName" varchar(255) DEFAULT NULL,
  "tableName" varchar(255) DEFAULT NULL,
  "systemable" bool DEFAULT NULL,
  "readOnly" bool DEFAULT NULL
);

ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_name" UNIQUE("name");
ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_plural_name" UNIQUE("pluralName");
ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_table_name" UNIQUE("tableName");

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1, '用户', true, '2020-12-23 17:06:29.161000', '用户', 'INNODB', '2021-08-05 10:52:53.955000', 'user', 'users', 'spring_user', true, false),
(2, '角色', true, '2021-02-01 10:51:21.324000', '角色', 'INNODB', '2021-08-05 10:53:53.426000', 'role', 'roles', 'spring_role', true, false),
(3, '资源', true, '2021-02-01 10:54:05.668000', '资源', 'INNODB', '2021-02-05 17:52:19.025000', 'resource', 'resources', 'spring_resource', true, false),
(4, '用户角色行', true, '2021-02-01 11:11:15.312000', '用户角色行', 'INNODB', '2021-02-01 11:11:15.312000', 'userRoleLine', 'userRoleLines', 'ca_userRoleLine', true, false),
(5, '角色资源行', true, '2021-02-01 11:27:24.848000', '角色资源行', 'INNODB', '2021-02-03 16:43:36.315000', 'roleResourceLine', 'roleResourceLines', 'ca_roleResourceLine', true, false),
(19, '省', true, '2021-02-07 11:57:10.121000', '', 'INNODB', '2021-02-07 15:25:57.159000', 'province', 'provinces', 'ca_province', true, false),
(20, '市', true, '2021-02-07 11:59:06.946000', '', 'INNODB', '2021-02-08 16:21:31.003000', 'city', 'cities', 'ca_city', true, false),
(22, '区', true, '2021-02-07 15:25:23.709000', '', 'INNODB', '2021-08-05 10:38:57.082000', 'district', 'districts', 'ca_district', true, false),
(24, '文件', true, '2021-02-08 11:09:32.053000', '', 'INNODB', '2021-02-09 14:33:24.635000', 'file', 'files', 'ca_file', true, false),
(34, '目录', true, '2021-02-09 14:21:40.991000', '', 'INNODB', '2021-02-09 14:21:40.991000', 'folder', 'folders', 'ca_folder', true, false),
(37, '微信配置', true, '2021-07-25 16:41:11.888000', '', 'INNODB', '2021-07-25 16:41:37.914000', 'weixinConfig', 'weixinConfigs', 'ca_weixinConfig', true, false),
(42, '短信配置', true, '2021-07-27 15:46:57.227000', '', 'INNODB', '2021-07-27 15:46:57.227000', 'smsConfig', 'smsConfigs', 'ca_smsConfig', true, false),
(43, '短信模板', true, '2021-07-28 10:56:47.080000', '', 'INNODB', '2021-07-28 10:56:47.080000', 'smsTemplate', 'smsTemplates', 'ca_smsTemplate', true, false),
(62, '模块', true, '2021-09-02 15:30:37.082000', '', 'INNODB', '2021-09-06 17:30:59.436000', 'module', 'modules', 'ca_sys_module', true, false),
(63, '模块行', true, '2021-09-02 15:34:04.944000', '', 'INNODB', '2021-09-02 15:34:04.944000', 'moduleLine', 'moduleLines', 'ca_sys_moduleLine', true, false),
(66, '表', true, '2021-09-02 15:59:07.087000', '', 'INNODB', '2021-09-06 16:20:28.729000', 'table', 'tables', 'ca_meta_table', true, true),
(69, '表页面', true, '2021-11-03 11:23:02.166000', '', 'INNODB', '2021-11-03 11:23:02.166000', 'tableFormBuilder', 'tableFormBuilders', 'ca_tableFormBuilder', true, false);


CREATE TABLE "ca_meta_column" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "autoIncrement" bool DEFAULT NULL,
  "caption" varchar(255) DEFAULT NULL,
  "createdDate" TIMESTAMP(6) DEFAULT NULL,
  "dataType" varchar(255) DEFAULT NULL,
  "defaultValue" varchar(255) DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "displayOrder" int DEFAULT NULL,
  "indexName" varchar(255) DEFAULT NULL,
  "indexStorage" varchar(255) DEFAULT NULL,
  "indexType" varchar(255) DEFAULT NULL,
  "insertable" bool DEFAULT NULL,
  "lastModifiedDate" TIMESTAMP(6) DEFAULT NULL,
  "length" int DEFAULT NULL,
  "name" varchar(255)  DEFAULT NULL,
  "nullable" bool DEFAULT NULL,
  "precision" int DEFAULT NULL,
  "queryable" bool DEFAULT NULL,
  "scale" int DEFAULT NULL,
  "seqId" bigint DEFAULT NULL,
  "unsigned" bool DEFAULT NULL,
  "updatable" bool DEFAULT NULL,
  "displayable" bool DEFAULT NULL,
  "systemable" bool DEFAULT NULL,
  "tableId" bigint DEFAULT NULL,
  "multipleValue" bool NULL DEFAULT false,
  CONSTRAINT "fk_bsm_column_seq_id" FOREIGN KEY ("seqId") REFERENCES "ca_meta_sequence" ("id"),
  CONSTRAINT "fk_bsm_column_table_id" FOREIGN KEY ("tableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_name" UNIQUE("tableId","name");
ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_index_name" UNIQUE("indexName");

INSERT INTO "ca_meta_column" VALUES (2, false, '名称', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 1);
INSERT INTO "ca_meta_column" VALUES (4, false, '创建时间', '2020-12-23 17:06:29.211', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-08-05 10:52:53.967', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, true, 1);
INSERT INTO "ca_meta_column" VALUES (5, false, '修改时间', '2020-12-23 17:06:29.211', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-08-05 10:52:53.967', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, true, 1);
INSERT INTO "ca_meta_column" VALUES (6, false, 'OPENID', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, 'OPENID', 5, 'uq_spring_user_openid', NULL, 'UNIQUE', true, '2021-08-05 10:52:53.967', 50, 'openId', true, NULL, false, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (7, false, '用户名', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, '用户名', 6, 'uq_spring_user_username', NULL, 'UNIQUE', true, '2021-08-05 10:52:53.967', 50, 'username', false, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (8, false, '真实姓名', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, '真实姓名', 7, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 200, 'realname', true, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (9, false, '手机', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, '手机', 8, 'uq_spring_user_mobile', NULL, 'UNIQUE', true, '2021-08-05 10:52:53.967', 20, 'mobile', true, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (10, false, '邮箱', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, '邮箱', 9, 'uq_spring_user_email', NULL, 'UNIQUE', true, '2021-08-05 10:52:53.967', 200, 'email', true, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (11, false, '密码', '2020-12-23 17:06:29.211', 'PASSWORD', NULL, '密码', 10, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 500, 'password', false, NULL, false, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (12, false, '明文密码', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, '明文密码', 11, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 200, 'cleartextPwd', true, NULL, false, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (13, false, 'TOKEN', '2020-12-23 17:06:29.211', 'VARCHAR', NULL, 'TOKEN', 12, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 200, 'token', true, NULL, false, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (14, false, '启用', '2020-12-23 17:06:29.211', 'BOOL', 'true', '启用', 13, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 10, 'enabled', false, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (15, false, '账号没有过期', '2020-12-23 17:06:29.211', 'BOOL', 'true', '账号没有过期', 14, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 10, 'accountNonExpired', false, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (16, false, '账号没有锁定', '2020-12-23 17:06:29.211', 'BOOL', 'true', '账号没有锁定', 15, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 10, 'accountNonLocked', false, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (17, false, '凭证没有过期', '2020-12-23 17:06:29.211', 'BOOL', 'true', '凭证没有过期', 16, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 10, 'credentialsNonExpired', false, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (21, false, '名称', '2021-02-01 10:51:21.334', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-08-05 10:53:53.431', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 2);
INSERT INTO "ca_meta_column" VALUES (23, false, '创建时间', '2021-02-01 10:51:21.334', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-08-05 10:53:53.431', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, true, 2);
INSERT INTO "ca_meta_column" VALUES (24, false, '修改时间', '2021-02-01 10:51:21.334', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-08-05 10:53:53.431', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, true, 2);
INSERT INTO "ca_meta_column" VALUES (25, false, '编码', '2021-02-01 10:51:21.334', 'VARCHAR', NULL, '编码', 5, 'uq_spring_role_code', NULL, 'UNIQUE', true, '2021-08-05 10:53:53.431', 200, 'code', false, NULL, true, NULL, 1, false, true, false, false, 2);
INSERT INTO "ca_meta_column" VALUES (26, false, '备注', '2021-02-01 10:51:21.334', 'TEXT', NULL, '备注', 6, NULL, NULL, NULL, true, '2021-08-05 10:53:53.431', NULL, 'remark', true, NULL, true, NULL, NULL, false, true, false, false, 2);
INSERT INTO "ca_meta_column" VALUES (28, false, '名称', '2021-02-01 10:54:05.671', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-05 17:52:19.036', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 3);
INSERT INTO "ca_meta_column" VALUES (30, false, '创建时间', '2021-02-01 10:54:05.671', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-05 17:52:19.036', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 3);
INSERT INTO "ca_meta_column" VALUES (31, false, '修改时间', '2021-02-01 10:54:05.671', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-05 17:52:19.036', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 3);
INSERT INTO "ca_meta_column" VALUES (33, false, 'URL表达式', '2021-02-01 10:54:05.671', 'VARCHAR', NULL, 'URL表达式', 6, 'uq_spring_resource_url', NULL, 'UNIQUE', true, '2021-02-05 17:52:19.036', 200, 'url', true, NULL, true, NULL, NULL, false, true, NULL, false, 3);
INSERT INTO "ca_meta_column" VALUES (34, false, '备注', '2021-02-01 10:54:05.671', 'TEXT', NULL, '备注', 8, NULL, NULL, NULL, true, '2021-02-05 17:52:19.036', NULL, 'remark', true, NULL, true, NULL, NULL, false, true, NULL, false, 3);
INSERT INTO "ca_meta_column" VALUES (36, false, '名称', '2021-02-01 11:11:15.313', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-01 11:11:15.313', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 4);
INSERT INTO "ca_meta_column" VALUES (38, false, '创建时间', '2021-02-01 11:11:15.313', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-01 11:11:15.313', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 4);
INSERT INTO "ca_meta_column" VALUES (39, false, '修改时间', '2021-02-01 11:11:15.313', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-01 11:11:15.313', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 4);
INSERT INTO "ca_meta_column" VALUES (40, false, '用户编号', '2021-02-01 11:11:15.313', 'BIGINT', NULL, '用户编号', 5, NULL, NULL, NULL, true, '2021-02-01 11:11:15.313', 20, 'userId', false, NULL, true, NULL, NULL, false, true, NULL, false, 4);
INSERT INTO "ca_meta_column" VALUES (41, false, '角色编号', '2021-02-01 11:11:15.313', 'BIGINT', NULL, '角色编号', 6, NULL, NULL, NULL, true, '2021-02-01 11:11:15.313', 20, 'roleId', false, NULL, true, NULL, NULL, false, true, NULL, false, 4);
INSERT INTO "ca_meta_column" VALUES (43, false, '名称', '2021-02-01 11:27:24.851', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-03 16:43:36.326', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 5);
INSERT INTO "ca_meta_column" VALUES (45, false, '创建时间', '2021-02-01 11:27:24.851', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-03 16:43:36.326', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 5);
INSERT INTO "ca_meta_column" VALUES (46, false, '修改时间', '2021-02-01 11:27:24.851', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-03 16:43:36.326', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 5);
INSERT INTO "ca_meta_column" VALUES (47, false, '角色编号', '2021-02-01 11:27:24.851', 'BIGINT', NULL, '角色编号', 5, NULL, NULL, NULL, true, '2021-02-03 16:43:36.326', 20, 'roleId', true, NULL, true, NULL, NULL, false, true, NULL, false, 5);
INSERT INTO "ca_meta_column" VALUES (48, false, '资源编号', '2021-02-01 11:27:24.851', 'BIGINT', NULL, '资源编号', 6, NULL, NULL, NULL, true, '2021-02-03 16:43:36.326', 20, 'resourceId', true, NULL, true, NULL, NULL, false, true, NULL, false, 5);
INSERT INTO "ca_meta_column" VALUES (56, false, '编码', '2021-02-02 10:06:53.176', 'VARCHAR', NULL, '编码', 5, 'uq_spring_resource_code', NULL, 'UNIQUE', true, '2021-02-05 17:52:19.036', 200, 'code', true, NULL, true, NULL, 2, false, true, NULL, false, 3);
INSERT INTO "ca_meta_column" VALUES (58, false, '操作', '2021-02-03 16:44:10.071', 'VARCHAR', NULL, '操作', 7, NULL, NULL, NULL, true, '2021-02-05 17:52:19.036', 200, 'action', true, NULL, true, NULL, NULL, false, true, NULL, false, 3);
INSERT INTO "ca_meta_column" VALUES (156, false, '名称', '2021-02-07 11:57:10.131', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-07 15:25:57.167', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 19);
INSERT INTO "ca_meta_column" VALUES (158, false, '创建时间', '2021-02-07 11:57:10.131', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-07 15:25:57.167', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 19);
INSERT INTO "ca_meta_column" VALUES (159, false, '修改时间', '2021-02-07 11:57:10.131', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-07 15:25:57.167', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 19);
INSERT INTO "ca_meta_column" VALUES (160, false, '编码', '2021-02-07 11:57:10.131', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, true, '2021-02-07 15:25:57.167', 200, 'code', true, NULL, true, NULL, NULL, false, true, NULL, false, 19);
INSERT INTO "ca_meta_column" VALUES (162, false, '名称', '2021-02-07 11:59:06.948', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-08 16:21:31.011', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 20);
INSERT INTO "ca_meta_column" VALUES (164, false, '创建时间', '2021-02-07 11:59:06.948', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-08 16:21:31.011', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 20);
INSERT INTO "ca_meta_column" VALUES (165, false, '修改时间', '2021-02-07 11:59:06.948', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-08 16:21:31.011', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 20);
INSERT INTO "ca_meta_column" VALUES (166, false, '编码', '2021-02-07 11:59:06.948', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, true, '2021-02-08 16:21:31.011', 200, 'code', true, NULL, true, NULL, NULL, false, true, NULL, false, 20);
INSERT INTO "ca_meta_column" VALUES (167, false, '省编号', '2021-02-07 11:59:06.948', 'BIGINT', NULL, '省编号', 6, NULL, NULL, NULL, true, '2021-02-08 16:21:31.011', 20, 'provinceId', true, NULL, true, NULL, NULL, false, true, NULL, false, 20);
INSERT INTO "ca_meta_column" VALUES (177, false, '名称', '2021-02-07 15:25:23.718', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-08-05 10:38:57.087', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 22);
INSERT INTO "ca_meta_column" VALUES (179, false, '创建时间', '2021-02-07 15:25:23.718', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-08-05 10:38:57.087', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, true, 22);
INSERT INTO "ca_meta_column" VALUES (180, false, '修改时间', '2021-02-07 15:25:23.718', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-08-05 10:38:57.087', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, true, 22);
INSERT INTO "ca_meta_column" VALUES (181, false, '编码', '2021-02-07 15:25:23.718', 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, true, '2021-08-05 10:38:57.087', 200, 'code', true, NULL, true, NULL, NULL, false, true, false, false, 22);
INSERT INTO "ca_meta_column" VALUES (182, false, '市编号', '2021-02-07 15:25:23.718', 'BIGINT', NULL, '市编号', 6, NULL, NULL, NULL, true, '2021-08-05 10:38:57.087', 20, 'cityId', true, NULL, true, NULL, NULL, false, true, false, false, 22);
INSERT INTO "ca_meta_column" VALUES (193, false, '名称', '2021-02-08 11:09:32.062', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-09 14:33:24.642', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 24);
INSERT INTO "ca_meta_column" VALUES (195, false, '创建时间', '2021-02-08 11:09:32.062', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-09 14:33:24.642', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 24);
INSERT INTO "ca_meta_column" VALUES (196, false, '修改时间', '2021-02-08 11:09:32.062', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-09 14:33:24.642', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 24);
INSERT INTO "ca_meta_column" VALUES (197, false, '大小', '2021-02-08 11:09:32.062', 'BIGINT', NULL, '大小', 6, NULL, NULL, NULL, true, '2021-02-09 14:33:24.642', 20, 'size', true, NULL, true, NULL, NULL, false, true, NULL, false, 24);
INSERT INTO "ca_meta_column" VALUES (198, false, 'MD5', '2021-02-08 11:09:32.062', 'VARCHAR', NULL, 'MD5', 7, NULL, NULL, NULL, true, '2021-02-09 14:33:24.642', 200, 'md5', true, NULL, true, NULL, NULL, false, true, NULL, false, 24);
INSERT INTO "ca_meta_column" VALUES (199, false, '类型', '2021-02-08 11:09:32.062', 'VARCHAR', NULL, '类型', 8, NULL, NULL, NULL, true, '2021-02-09 14:33:24.642', 200, 'type', true, NULL, true, NULL, NULL, false, true, NULL, false, 24);
INSERT INTO "ca_meta_column" VALUES (218, false, '链接', '2021-02-08 16:17:56.52', 'ATTACHMENT', NULL, '链接', 5, NULL, NULL, NULL, true, '2021-02-09 14:33:24.642', 4000, 'url', true, NULL, true, NULL, NULL, false, true, NULL, false, 24);
INSERT INTO "ca_meta_column" VALUES (219, false, '头像文件编号', '2021-02-08 16:27:37.689', 'BIGINT', NULL, '头像文件编号', 17, NULL, NULL, NULL, true, '2021-08-05 10:52:53.967', 20, 'fileId', true, NULL, true, NULL, NULL, false, true, false, false, 1);
INSERT INTO "ca_meta_column" VALUES (221, false, '名称', '2021-02-09 14:21:40.999', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-02-09 14:21:40.999', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 34);
INSERT INTO "ca_meta_column" VALUES (223, false, '创建时间', '2021-02-09 14:21:40.999', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-02-09 14:21:40.999', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 34);
INSERT INTO "ca_meta_column" VALUES (224, false, '修改时间', '2021-02-09 14:21:40.999', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-02-09 14:21:40.999', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 34);
INSERT INTO "ca_meta_column" VALUES (225, false, '父目录编号', '2021-02-09 14:21:40.999', 'BIGINT', NULL, '父目录编号', 5, NULL, NULL, NULL, true, '2021-02-09 14:21:40.999', 20, 'parentFolderId', true, NULL, true, NULL, NULL, false, true, NULL, false, 34);
INSERT INTO "ca_meta_column" VALUES (226, false, '目录编号', '2021-02-09 14:33:24.642', 'BIGINT', NULL, '目录编号', 9, NULL, NULL, NULL, true, '2021-02-09 14:33:24.642', 20, 'folderId', true, NULL, true, NULL, NULL, false, true, NULL, false, 24);
INSERT INTO "ca_meta_column" VALUES (245, false, '名称', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 37);
INSERT INTO "ca_meta_column" VALUES (247, false, '创建时间', '2021-07-25 16:41:11.9', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-07-25 16:41:37.922', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 37);
INSERT INTO "ca_meta_column" VALUES (248, false, '修改时间', '2021-07-25 16:41:11.9', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-07-25 16:41:37.922', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 37);
INSERT INTO "ca_meta_column" VALUES (249, false, 'AppId', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, 'AppId', 5, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'appId', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (250, false, 'AppSecret', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, 'AppSecret', 6, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'appSecret', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (251, false, 'Token', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, 'Token', 7, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'token', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (252, false, 'EncodingAESKey', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, 'EncodingAESKey', 8, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'encodingAESKey', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (253, false, 'AccessToken', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, 'AccessToken', 9, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'accessToken', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (254, false, 'ExpiresIn', '2021-07-25 16:41:11.9', 'DATETIME', NULL, 'ExpiresIn', 10, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 200, 'expiresIn', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (255, false, 'Enable', '2021-07-25 16:41:11.9', 'INT', NULL, 'Enable', 11, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 10, 'enable', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (256, false, 'Log', '2021-07-25 16:41:11.9', 'TEXT', NULL, 'Log', 12, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', NULL, 'log', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (257, false, 'JsapiTicket', '2021-07-25 16:41:11.9', 'VARCHAR', NULL, 'JsapiTicket', 13, NULL, NULL, NULL, true, '2021-07-25 16:41:37.922', 400, 'jsapiTicket', true, NULL, true, NULL, NULL, false, true, NULL, false, 37);
INSERT INTO "ca_meta_column" VALUES (294, false, '名称', '2021-07-27 15:46:57.24', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-07-27 15:46:57.24', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 42);
INSERT INTO "ca_meta_column" VALUES (296, false, '创建时间', '2021-07-27 15:46:57.24', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-07-27 15:46:57.24', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 42);
INSERT INTO "ca_meta_column" VALUES (297, false, '修改时间', '2021-07-27 15:46:57.24', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-07-27 15:46:57.24', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 42);
INSERT INTO "ca_meta_column" VALUES (298, false, 'Url', '2021-07-27 15:46:57.24', 'VARCHAR', NULL, 'Url', 5, NULL, NULL, NULL, true, '2021-07-27 15:46:57.24', 200, 'url', true, NULL, true, NULL, NULL, false, true, NULL, false, 42);
INSERT INTO "ca_meta_column" VALUES (299, false, '用户名', '2021-07-27 15:46:57.24', 'VARCHAR', NULL, '用户名', 6, NULL, NULL, NULL, true, '2021-07-27 15:46:57.24', 200, 'username', true, NULL, true, NULL, NULL, false, true, NULL, false, 42);
INSERT INTO "ca_meta_column" VALUES (300, false, '密码', '2021-07-27 15:46:57.24', 'VARCHAR', NULL, '密码', 7, NULL, NULL, NULL, true, '2021-07-27 15:46:57.24', 200, 'password', true, NULL, true, NULL, NULL, false, true, NULL, false, 42);
INSERT INTO "ca_meta_column" VALUES (302, false, '名称', '2021-07-28 10:56:47.092', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-07-28 10:56:47.092', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, true, 43);
INSERT INTO "ca_meta_column" VALUES (304, false, '创建时间', '2021-07-28 10:56:47.092', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-07-28 10:56:47.092', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, NULL, true, 43);
INSERT INTO "ca_meta_column" VALUES (305, false, '修改时间', '2021-07-28 10:56:47.092', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-07-28 10:56:47.092', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, NULL, true, 43);
INSERT INTO "ca_meta_column" VALUES (306, false, '签名', '2021-07-28 10:56:47.092', 'VARCHAR', NULL, '签名', 5, NULL, NULL, NULL, true, '2021-07-28 10:56:47.092', 200, 'sign', true, NULL, true, NULL, NULL, false, true, NULL, false, 43);
INSERT INTO "ca_meta_column" VALUES (307, false, '模板', '2021-07-28 10:56:47.092', 'VARCHAR', NULL, '模板', 6, NULL, NULL, NULL, true, '2021-07-28 10:56:47.092', 200, 'template', true, NULL, true, NULL, NULL, false, true, NULL, false, 43);
INSERT INTO "ca_meta_column" VALUES (308, false, '短信配置编号', '2021-07-28 10:56:47.092', 'BIGINT', NULL, '短信配置编号', 7, NULL, NULL, NULL, true, '2021-07-28 10:56:47.092', 20, 'smsConfigId', true, NULL, true, NULL, NULL, false, true, NULL, false, 43);
INSERT INTO "ca_meta_column" VALUES (433, false, '名称', '2021-09-02 15:30:37.086', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-09-06 17:30:59.446', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, false, 62);
INSERT INTO "ca_meta_column" VALUES (435, false, '创建时间', '2021-09-02 15:30:37.086', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-09-06 17:30:59.446', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, 62);
INSERT INTO "ca_meta_column" VALUES (436, false, '修改时间', '2021-09-02 15:30:37.086', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-09-06 17:30:59.446', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, 62);
INSERT INTO "ca_meta_column" VALUES (438, false, '名称', '2021-09-02 15:34:04.947', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-09-02 15:34:04.947', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, false, 63);
INSERT INTO "ca_meta_column" VALUES (440, false, '创建时间', '2021-09-02 15:34:04.947', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-09-02 15:34:04.947', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, 63);
INSERT INTO "ca_meta_column" VALUES (441, false, '修改时间', '2021-09-02 15:34:04.947', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-09-02 15:34:04.947', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, 63);
INSERT INTO "ca_meta_column" VALUES (442, false, '模块编号', '2021-09-02 15:34:04.947', 'BIGINT', NULL, '模块编号', 5, NULL, NULL, NULL, true, '2021-09-02 15:34:04.947', 20, 'moduleId', true, NULL, true, NULL, NULL, false, true, false, false, 63);
INSERT INTO "ca_meta_column" VALUES (443, false, '表编号', '2021-09-02 15:34:04.947', 'BIGINT', NULL, '表编号', 6, NULL, NULL, NULL, true, '2021-09-02 15:34:04.947', 20, 'tableId', true, NULL, true, NULL, NULL, false, true, false, false, 63);
INSERT INTO "ca_meta_column" VALUES (462, false, '中文名称', '2021-09-02 15:59:07.09', 'VARCHAR', NULL, '中文名称', 2, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', 255, 'caption', true, NULL, true, NULL, NULL, false, true, true, false, 66);
INSERT INTO "ca_meta_column" VALUES (463, false, '是否创建物理表', '2021-09-02 15:59:07.09', 'BOOL', NULL, '是否创建物理表', 3, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', NULL, 'createPhysicalTable', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (464, false, '创建时间', '2021-09-02 15:59:07.09', 'DATETIME', NULL, '创建时间', 4, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', 6, 'createdDate', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (465, false, '描述', '2021-09-02 15:59:07.09', 'VARCHAR', NULL, '描述', 5, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', 255, 'description', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (466, false, '引擎', '2021-09-02 15:59:07.09', 'VARCHAR', NULL, '引擎', 6, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', 255, 'engine', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (467, false, '修改时间', '2021-09-02 15:59:07.09', 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', 6, 'lastModifiedDate', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (468, false, '英文名称', '2021-09-02 15:59:07.09', 'VARCHAR', NULL, '英文名称', 1, 'uq_bsm_table_name', 'BTREE', 'UNIQUE', true, '2021-09-06 16:20:28.739', 255, 'name', true, NULL, true, NULL, NULL, false, true, true, false, 66);
INSERT INTO "ca_meta_column" VALUES (469, false, '英文复数', '2021-09-02 15:59:07.09', 'VARCHAR', NULL, '英文复数', 8, 'uq_bsm_table_plural_name', 'BTREE', 'UNIQUE', true, '2021-09-06 16:20:28.739', 255, 'pluralName', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (470, false, '物理表名称', '2021-09-02 15:59:07.09', 'VARCHAR', NULL, '物理表名称', 9, 'uq_bsm_table_table_name', 'BTREE', 'UNIQUE', true, '2021-09-06 16:20:28.739', 255, 'tableName', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (471, false, '是否系统表', '2021-09-02 15:59:07.09', 'BOOL', NULL, '是否系统表', 10, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', NULL, 'systemable', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (473, false, '是否只读', '2021-09-06 16:10:31.028', 'BOOL', NULL, '是否只读', 11, NULL, NULL, NULL, true, '2021-09-06 16:20:28.739', NULL, 'readOnly', true, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (490, false, '名称', '2021-11-03 11:23:02.177', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2021-11-03 11:23:02.177', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, false, 69);
INSERT INTO "ca_meta_column" VALUES (492, false, '创建时间', '2021-11-03 11:23:02.177', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2021-11-03 11:23:02.177', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (493, false, '修改时间', '2021-11-03 11:23:02.177', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2021-11-03 11:23:02.177', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (494, false, '类型', '2021-11-03 11:23:02.177', 'VARCHAR', NULL, '类型', 5, NULL, NULL, NULL, true, '2021-11-03 11:23:02.177', 200, 'type', true, NULL, true, NULL, NULL, false, true, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (495, false, '设备', '2021-11-03 11:23:02.177', 'VARCHAR', NULL, '设备', 6, NULL, NULL, NULL, true, '2021-11-03 11:23:02.177', 200, 'device', true, NULL, true, NULL, NULL, false, true, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (496, false, '内容', '2021-11-03 11:23:02.177', 'LONGTEXT', NULL, '内容', 7, NULL, NULL, NULL, true, '2021-11-03 11:23:02.177', NULL, 'body', true, NULL, true, NULL, NULL, false, true, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (497, false, '表编号', '2021-11-03 11:23:02.177', 'BIGINT', NULL, '表编号', 8, NULL, NULL, NULL, true, '2021-11-03 11:23:02.177', 20, 'tableId', true, NULL, true, NULL, NULL, false, true, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (1, true, '编号', '2020-12-23 17:06:29.211', 'BIGINT', NULL, '编号', 0, 'spring_user_pkey', NULL, 'PRIMARY', false, '2021-08-05 10:52:53.967', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, true, 1);
INSERT INTO "ca_meta_column" VALUES (20, true, '编号', '2021-02-01 10:51:21.334', 'BIGINT', NULL, '主键', 0, 'spring_role_pkey', NULL, 'PRIMARY', false, '2021-08-05 10:53:53.431', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, true, 2);
INSERT INTO "ca_meta_column" VALUES (27, true, '编号', '2021-02-01 10:54:05.671', 'BIGINT', NULL, '主键', 0, 'spring_resource_pkey', NULL, 'PRIMARY', false, '2021-02-05 17:52:19.036', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 3);
INSERT INTO "ca_meta_column" VALUES (35, true, '编号', '2021-02-01 11:11:15.313', 'BIGINT', NULL, '主键', 0, 'ca_userRoleLine_pkey', NULL, 'PRIMARY', false, '2021-02-01 11:11:15.313', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 4);
INSERT INTO "ca_meta_column" VALUES (42, true, '编号', '2021-02-01 11:27:24.851', 'BIGINT', NULL, '主键', 0, 'ca_roleResourceLine_pkey', NULL, 'PRIMARY', false, '2021-02-03 16:43:36.326', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 5);
INSERT INTO "ca_meta_column" VALUES (155, true, '编号', '2021-02-07 11:57:10.131', 'BIGINT', NULL, '主键', 0, 'ca_province_pkey', NULL, 'PRIMARY', false, '2021-02-07 15:25:57.167', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 19);
INSERT INTO "ca_meta_column" VALUES (161, true, '编号', '2021-02-07 11:59:06.948', 'BIGINT', NULL, '主键', 0, 'ca_city_pkey', NULL, 'PRIMARY', false, '2021-02-08 16:21:31.011', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 20);
INSERT INTO "ca_meta_column" VALUES (176, true, '编号', '2021-02-07 15:25:23.718', 'BIGINT', NULL, '主键', 0, 'ca_district_pkey', NULL, 'PRIMARY', false, '2021-08-05 10:38:57.087', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, true, 22);
INSERT INTO "ca_meta_column" VALUES (192, true, '编号', '2021-02-08 11:09:32.062', 'BIGINT', NULL, '主键', 0, 'ca_file_pkey', NULL, 'PRIMARY', false, '2021-02-09 14:33:24.642', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 24);
INSERT INTO "ca_meta_column" VALUES (220, true, '编号', '2021-02-09 14:21:40.999', 'BIGINT', NULL, '主键', 0, 'ca_folder_pkey', NULL, 'PRIMARY', false, '2021-02-09 14:21:40.999', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 34);
INSERT INTO "ca_meta_column" VALUES (244, true, '编号', '2021-07-25 16:41:11.9', 'BIGINT', NULL, '主键', 0, 'ca_weixinConfig_pkey', NULL, 'PRIMARY', false, '2021-07-25 16:41:37.922', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 37);
INSERT INTO "ca_meta_column" VALUES (293, true, '编号', '2021-07-27 15:46:57.24', 'BIGINT', NULL, '主键', 0, 'ca_smsConfig_pkey', NULL, 'PRIMARY', false, '2021-07-27 15:46:57.24', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 42);
INSERT INTO "ca_meta_column" VALUES (301, true, '编号', '2021-07-28 10:56:47.092', 'BIGINT', NULL, '主键', 0, 'ca_smsTemplate_pkey', NULL, 'PRIMARY', false, '2021-07-28 10:56:47.092', 20, 'id', false, NULL, false, NULL, NULL, true, false, NULL, true, 43);
INSERT INTO "ca_meta_column" VALUES (432, true, '编号', '2021-09-02 15:30:37.086', 'BIGINT', NULL, '主键', 0, 'ca_sys_module_pkey', NULL, 'PRIMARY', false, '2021-09-06 17:30:59.446', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, 62);
INSERT INTO "ca_meta_column" VALUES (437, true, '编号', '2021-09-02 15:34:04.947', 'BIGINT', NULL, '主键', 0, 'ca_sys_moduleLine_pkey', NULL, 'PRIMARY', false, '2021-09-02 15:34:04.947', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, 63);
INSERT INTO "ca_meta_column" VALUES (461, true, '编号', '2021-09-02 15:59:07.09', 'BIGINT', NULL, '编号', 0, 'ca_meta_table_pkey', NULL, 'PRIMARY', true, '2021-09-06 16:20:28.739', NULL, 'id', false, NULL, true, NULL, NULL, false, true, false, false, 66);
INSERT INTO "ca_meta_column" VALUES (489, true, '编号', '2021-11-03 11:23:02.177', 'BIGINT', NULL, '主键', 0, 'ca_tableFormBuilder_pkey', NULL, 'PRIMARY', false, '2021-11-03 11:23:02.177', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, 69);
INSERT INTO "ca_meta_column" VALUES (3, false, '全文索引', '2020-12-23 17:06:29.211', 'TEXT', NULL, '全文索引', 2, 'spring_user_ftidx', NULL, 'FULLTEXT', false, '2021-08-05 10:52:53.967', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, true, 1);
INSERT INTO "ca_meta_column" VALUES (22, false, '全文索引', '2021-02-01 10:51:21.334', 'TEXT', NULL, '全文索引', 2, 'spring_role_ftidx', NULL, 'FULLTEXT', false, '2021-08-05 10:53:53.431', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, true, 2);
INSERT INTO "ca_meta_column" VALUES (29, false, '全文索引', '2021-02-01 10:54:05.671', 'TEXT', NULL, '全文索引', 2, 'spring_resource_ftidx', NULL, 'FULLTEXT', false, '2021-02-05 17:52:19.036', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 3);
INSERT INTO "ca_meta_column" VALUES (37, false, '全文索引', '2021-02-01 11:11:15.313', 'TEXT', NULL, '全文索引', 2, 'ca_userRoleLine_ftidx', NULL, 'FULLTEXT', false, '2021-02-01 11:11:15.313', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 4);
INSERT INTO "ca_meta_column" VALUES (44, false, '全文索引', '2021-02-01 11:27:24.851', 'TEXT', NULL, '全文索引', 2, 'ca_roleResourceLine_ftidx', NULL, 'FULLTEXT', false, '2021-02-03 16:43:36.326', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 5);
INSERT INTO "ca_meta_column" VALUES (157, false, '全文索引', '2021-02-07 11:57:10.131', 'TEXT', NULL, '全文索引', 2, 'ca_province_ftidx', NULL, 'FULLTEXT', false, '2021-02-07 15:25:57.167', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 19);
INSERT INTO "ca_meta_column" VALUES (163, false, '全文索引', '2021-02-07 11:59:06.948', 'TEXT', NULL, '全文索引', 2, 'ca_city_ftidx', NULL, 'FULLTEXT', false, '2021-02-08 16:21:31.011', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 20);
INSERT INTO "ca_meta_column" VALUES (178, false, '全文索引', '2021-02-07 15:25:23.718', 'TEXT', NULL, '全文索引', 2, 'ca_district_ftidx', NULL, 'FULLTEXT', false, '2021-08-05 10:38:57.087', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, true, 22);
INSERT INTO "ca_meta_column" VALUES (194, false, '全文索引', '2021-02-08 11:09:32.062', 'TEXT', NULL, '全文索引', 2, 'ca_file_ftidx', NULL, 'FULLTEXT', false, '2021-02-09 14:33:24.642', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 24);
INSERT INTO "ca_meta_column" VALUES (222, false, '全文索引', '2021-02-09 14:21:40.999', 'TEXT', NULL, '全文索引', 2, 'ca_folder_ftidx', NULL, 'FULLTEXT', false, '2021-02-09 14:21:40.999', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 34);
INSERT INTO "ca_meta_column" VALUES (246, false, '全文索引', '2021-07-25 16:41:11.9', 'TEXT', NULL, '全文索引', 2, 'ca_weixinConfig_ftidx', NULL, 'FULLTEXT', false, '2021-07-25 16:41:37.922', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 37);
INSERT INTO "ca_meta_column" VALUES (295, false, '全文索引', '2021-07-27 15:46:57.24', 'TEXT', NULL, '全文索引', 2, 'ca_smsConfig_ftidx', NULL, 'FULLTEXT', false, '2021-07-27 15:46:57.24', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 42);
INSERT INTO "ca_meta_column" VALUES (303, false, '全文索引', '2021-07-28 10:56:47.092', 'TEXT', NULL, '全文索引', 2, 'ca_smsTemplate_ftidx', NULL, 'FULLTEXT', false, '2021-07-28 10:56:47.092', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, NULL, true, 43);
INSERT INTO "ca_meta_column" VALUES (434, false, '全文索引', '2021-09-02 15:30:37.086', 'TEXT', NULL, '全文索引', 2, 'ca_sys_module_ftidx', NULL, 'FULLTEXT', false, '2021-09-06 17:30:59.446', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, 62);
INSERT INTO "ca_meta_column" VALUES (439, false, '全文索引', '2021-09-02 15:34:04.947', 'TEXT', NULL, '全文索引', 2, 'ca_sys_moduleLine_ftidx', NULL, 'FULLTEXT', false, '2021-09-02 15:34:04.947', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, 63);
INSERT INTO "ca_meta_column" VALUES (491, false, '全文索引', '2021-11-03 11:23:02.177', 'TEXT', NULL, '全文索引', 2, 'ca_tableFormBuilder_ftidx', NULL, 'FULLTEXT', false, '2021-11-03 11:23:02.177', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, 69);

CREATE TABLE "ca_meta_index" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "caption" varchar(255) DEFAULT NULL,
  "createdDate" TIMESTAMP(6) DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "indexStorage" varchar(255) DEFAULT NULL,
  "indexType" varchar(255) DEFAULT NULL,
  "lastModifiedDate" TIMESTAMP(6) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "tableId" bigint DEFAULT NULL,
  CONSTRAINT "fk_bsm_index_table_id" FOREIGN KEY ("tableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_index" ADD CONSTRAINT "uq_bsm_index_name" UNIQUE("tableId","name");

INSERT INTO "ca_meta_index" ("id", "caption", "createdDate", "description", "indexStorage", "indexType", "lastModifiedDate", "name", "tableId") VALUES
(12, '表页面类型', '2021-11-03 11:23:02.222000', '表页面类型唯一', 'BTREE', 'UNIQUE', '2021-11-03 11:23:02.222000', 'tableTypeIndex', 69);

CREATE TABLE "ca_meta_index_line" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "columnId" bigint DEFAULT NULL,
  "indexId" bigint DEFAULT NULL,
  CONSTRAINT "fk_bsm_index_line_index_id" FOREIGN KEY ("indexId") REFERENCES "ca_meta_index" ("id") ON DELETE CASCADE
);

INSERT INTO "ca_meta_index_line" ("id", "columnId", "indexId") VALUES
(20, 494, 12),
(21, 497, 12);

CREATE TABLE "ca_meta_table_relation" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "caption" varchar(255) DEFAULT NULL,
  "createdDate" TIMESTAMP(6) DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "lastModifiedDate" TIMESTAMP(6) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "relationType" varchar(255) DEFAULT NULL,
  "fromColumnId" bigint DEFAULT NULL,
  "fromTableId" bigint DEFAULT NULL,
  "toColumnId" bigint DEFAULT NULL,
  "toTableId" bigint DEFAULT NULL,
  CONSTRAINT "fk_bsm_table_relation_from_column_id" FOREIGN KEY ("fromColumnId") REFERENCES "ca_meta_column" ("id"),
  CONSTRAINT "fk_bsm_table_relation_from_table_id" FOREIGN KEY ("fromTableId") REFERENCES "ca_meta_table" ("id"),
  CONSTRAINT "fk_bsm_table_relation_to_column_id" FOREIGN KEY ("toColumnId") REFERENCES "ca_meta_column" ("id"),
  CONSTRAINT "fk_bsm_table_relation_to_table_id" FOREIGN KEY ("toTableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_table_relation" ADD CONSTRAINT "uq_ca_table_relation" UNIQUE("fromTableId","toTableId","relationType","fromColumnId","toColumnId");

INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES
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

CREATE TABLE "spring_resource" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "url" varchar(200) DEFAULT NULL,
  "remark" text,
  "code" varchar(200) DEFAULT NULL,
  "action" varchar(200) DEFAULT NULL
);

ALTER TABLE "spring_resource" ADD CONSTRAINT "uq_spring_resource_code" UNIQUE("code");
ALTER TABLE "spring_resource" ADD CONSTRAINT "uq_spring_resource_url" UNIQUE("url");
CREATE INDEX "spring_resource_ftidx" ON "spring_resource" ("fullTextBody");

INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(12, '业务数据', '业务数据 RESOURCE_BUSINESS /api/business/** *', '2021-07-27 16:02:17', '2021-07-27 16:51:43', '/api/business/**', NULL, 'RESOURCE_BUSINESS', '*'),
(13, '业务数据数字', '业务数据数字 RESOURCE_BUSINESS_COUNT /api/business/**/count *', '2021-07-27 16:52:29', '2021-07-27 16:52:41', '/api/business/**/count', NULL, 'RESOURCE_BUSINESS_COUNT', '*'),
(14, '用户', '用户 RESOURCE_USER /api/business/user** *', '2021-07-27 16:58:59', '2021-07-27 17:02:03', '/api/business/user**', NULL, 'RESOURCE_USER', '*'),
(15, '角色资源', '角色资源 RESOURCE_ROLE /api/business/role** *', '2021-07-27 17:26:46', '2021-07-27 17:27:24', '/api/business/role**', NULL, 'RESOURCE_ROLE', '*'),
(16, '资源资源', '资源资源 RESOURCE_RESOURCE /api/business/resource** *', '2021-07-27 17:28:06', '2021-07-27 17:29:03', '/api/business/resource**', NULL, 'RESOURCE_RESOURCE', '*');

CREATE TABLE "spring_user" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "openId" varchar(50) DEFAULT NULL,
  "username" varchar(50) NOT NULL,
  "realname" varchar(200) DEFAULT NULL,
  "mobile" varchar(20) DEFAULT NULL,
  "email" varchar(200) DEFAULT NULL,
  "password" varchar(500) NOT NULL,
  "cleartextPwd" varchar(200) DEFAULT NULL,
  "token" varchar(200) DEFAULT NULL,
  "enabled" bool NOT NULL DEFAULT true,
  "accountNonExpired" bool NOT NULL DEFAULT true,
  "accountNonLocked" bool NOT NULL DEFAULT true,
  "credentialsNonExpired" bool NOT NULL DEFAULT true,
  "createdDate" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "lastModifiedDate" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "fileId" bigint DEFAULT NULL
);

ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_username" UNIQUE("username");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_openid" UNIQUE("openId");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_mobile" UNIQUE("mobile");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_email" UNIQUE("email");
CREATE INDEX "spring_user_ftidx" ON "spring_user" ("fullTextBody");

INSERT INTO "spring_user" ("id", "openId", "username", "realname", "mobile", "email", "password", "cleartextPwd", "token", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "createdDate", "lastModifiedDate", "name", "fullTextBody", "fileId") VALUES
(1, NULL, 'superadmin', '超级管理员', '11111111111', NULL, '$2a$10$R9HWotqpXzmLOU0aYlFkquxcs0jq7f1raKHHnsbfT51gM9TF.2q7q', NULL, '1094a2c5e57c4c7fa3922d69b012cfa7', true, true, true, true, '2020-07-21 15:00:24', '2021-07-27 15:53:21', '超级管理员', '超级管理员 superadmin 超级管理员 11111111111 true true true true', NULL);


CREATE TABLE "spring_role" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "code" varchar(200) NOT NULL,
  "remark" text
);

ALTER TABLE "spring_role" ADD CONSTRAINT "uq_spring_role_code" UNIQUE("code");
CREATE INDEX "spring_role_ftidx" ON "spring_role" ("fullTextBody");

INSERT INTO "spring_role" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "code", "remark") VALUES
(11, '超级管理员', '超级管理员 ROLE_SUPER_ADMIN 超级管理员', '2021-02-01 13:28:37', '2021-06-07 16:50:45', 'ROLE_SUPER_ADMIN', '超级管理员'),
(20, '业务数据角色', '业务数据角色 ROLE_BUSINESS 业务数据用户', '2021-07-27 16:48:56', '2021-07-27 16:57:15', 'ROLE_BUSINESS', '业务数据用户');


CREATE TABLE "ca_roleResourceLine" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "roleId" bigint DEFAULT NULL,
  "resourceId" bigint DEFAULT NULL
);

CREATE INDEX "ca_roleResourceLine_ftidx" ON "ca_roleResourceLine" ("fullTextBody");

INSERT INTO "ca_roleResourceLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "roleId", "resourceId") VALUES
(11, '业务数据资源', NULL, '2021-07-27 16:57:16', NULL, 20, 12),
(12, '业务数据数字资源', NULL, '2021-07-27 16:57:16', NULL, 20, 13);

CREATE TABLE "ca_userRoleLine" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "userId" bigint NOT NULL,
  "roleId" bigint NOT NULL
);

CREATE INDEX "ca_userRoleLine_ftidx" ON "ca_userRoleLine" ("fullTextBody");

INSERT INTO "ca_userRoleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "userId", "roleId") VALUES
(15, '超级管理员', NULL, '2021-07-27 15:53:21', NULL, 1, 11);

CREATE TABLE "ca_sys_module" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL
);

CREATE INDEX "ca_sys_module_ftidx" ON "ca_sys_module" ("fullTextBody");

INSERT INTO "ca_sys_module" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate") VALUES
(1, '字典', '字典 bg-teal', '2021-09-02 16:05:41', '2021-09-03 16:51:05'),
(2, '文件', '文件 bg-primary', '2021-09-02 16:07:22', '2021-09-03 16:50:40'),
(3, '用户管理', '用户管理 bg-purple', '2021-09-03 16:47:49', '2021-09-03 16:49:51'),
(4, '微信', '微信', '2021-09-03 17:21:09', NULL);


CREATE TABLE "ca_sys_moduleLine" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY ,
  "name" varchar(200)  NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "moduleId" bigint DEFAULT NULL,
  "tableId" bigint DEFAULT NULL
);
CREATE INDEX "ca_sys_moduleLine_ftidx" ON "ca_sys_moduleLine" ("fullTextBody");


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

CREATE TABLE "ca_tableFormBuilder" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "type" varchar(200) DEFAULT NULL,
  "device" varchar(200) DEFAULT NULL,
  "body" text,
  "tableId" bigint DEFAULT NULL
);
CREATE INDEX "ca_tableFormBuilder_ftidx" ON "ca_tableFormBuilder" ("fullTextBody");


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
  ATTRIBUTE_BYTES BYTEA NOT NULL,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);


CREATE TABLE "persistent_logins" (
  "username" varchar(64) NOT NULL,
  "series" varchar(64) NOT NULL,
  "token" varchar(64) NOT NULL,
  "last_used" TIMESTAMP NOT NULL
);

CREATE TABLE "ca_province" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "code" varchar(200) DEFAULT NULL
);
CREATE INDEX "ca_province_ftidx" ON "ca_province" ("fullTextBody");

CREATE TABLE "ca_city" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "code" varchar(200) DEFAULT NULL,
  "provinceId" bigint DEFAULT NULL
);
CREATE INDEX "ca_city_ftidx" ON "ca_city" ("fullTextBody");

CREATE TABLE "ca_district" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text ,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "code" varchar(200) DEFAULT NULL,
  "cityId" bigint DEFAULT NULL
);
CREATE INDEX "ca_district_ftidx" ON "ca_district" ("fullTextBody");

CREATE TABLE "ca_file" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text ,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "size" bigint DEFAULT NULL,
  "md5" varchar(200) DEFAULT NULL,
  "type" varchar(200) DEFAULT NULL,
  "url" varchar(4000) DEFAULT NULL,
  "folderId" bigint DEFAULT NULL
);
CREATE INDEX "ca_file_ftidx" ON "ca_file" ("fullTextBody");

CREATE TABLE "ca_folder" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text ,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "parentFolderId" bigint DEFAULT NULL
);

CREATE INDEX "ca_folder_ftidx" ON "ca_folder" ("fullTextBody");

CREATE TABLE "ca_smsConfig" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "url" varchar(200) DEFAULT NULL,
  "username" varchar(200) DEFAULT NULL,
  "password" varchar(200) DEFAULT NULL
);

CREATE INDEX "ca_smsConfig_ftidx" ON "ca_smsConfig" ("fullTextBody");

CREATE TABLE "ca_smsTemplate" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "sign" varchar(200) DEFAULT NULL,
  "template" varchar(200) DEFAULT NULL,
  "smsConfigId" bigint DEFAULT NULL
);
CREATE INDEX "ca_smsTemplate_ftidx" ON "ca_smsTemplate" ("fullTextBody");

CREATE TABLE "ca_weixinConfig" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "appId" varchar(200) DEFAULT NULL,
  "appSecret" varchar(200) DEFAULT NULL,
  "token" varchar(200) DEFAULT NULL,
  "encodingAESKey" varchar(200) DEFAULT NULL,
  "accessToken" varchar(200) DEFAULT NULL,
  "expiresIn" TIMESTAMP DEFAULT NULL,
  "enable" int DEFAULT NULL,
  "log" text,
  "jsapiTicket" varchar(400) DEFAULT NULL
);

CREATE INDEX "ca_weixinConfig_ftidx" ON "ca_weixinConfig" ("fullTextBody");

--SELECT "c"."relname" FROM "pg_class" "c" WHERE "c"."relkind" = 'S';
SELECT setval('"ca_meta_sequence_id_seq"', 10000, true);
SELECT setval('"ca_meta_table_id_seq"', 10000, true);
SELECT setval('"ca_meta_column_id_seq"', 10000, true);
SELECT setval('"ca_meta_index_id_seq"', 10000, true);
SELECT setval('"ca_meta_index_line_id_seq"', 10000, true);
SELECT setval('"ca_meta_table_relation_id_seq"', 10000, true);
SELECT setval('"spring_resource_id_seq"', 10000, true);
SELECT setval('"spring_user_id_seq"', 10000, true);
SELECT setval('"spring_role_id_seq"', 10000, true);
SELECT setval('"ca_roleResourceLine_id_seq"', 10000, true);
SELECT setval('"ca_userRoleLine_id_seq"', 10000, true);
SELECT setval('"ca_sys_module_id_seq"', 10000, true);
SELECT setval('"ca_sys_moduleLine_id_seq"', 10000, true);
SELECT setval('"ca_tableFormBuilder_id_seq"', 10000, true);
SELECT setval('"ca_province_id_seq"', 10000, true);
SELECT setval('"ca_city_id_seq"', 10000, true);
SELECT setval('"ca_district_id_seq"', 10000, true);
SELECT setval('"ca_file_id_seq"', 10000, true);
SELECT setval('"ca_folder_id_seq"', 10000, true);
SELECT setval('"ca_smsConfig_id_seq"', 10000, true);
SELECT setval('"ca_smsTemplate_id_seq"', 10000, true);
SELECT setval('"ca_weixinConfig_id_seq"', 10000, true);
