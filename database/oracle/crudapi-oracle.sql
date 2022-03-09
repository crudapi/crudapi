drop sequence "ca_meta_sequence_id";
drop sequence "ca_meta_table_id";
drop sequence "ca_meta_column_id";
drop sequence "ca_meta_index_id";
drop sequence "ca_meta_index_line_id";
drop sequence "ca_meta_table_relation_id";
drop sequence "spring_user_id";
drop sequence "spring_resource_id";
drop sequence "spring_role_id";
drop sequence "ca_roleResourceLine_id";
drop sequence "ca_userRoleLine_id";
drop sequence "ca_weixinConfig_id";
drop sequence "ca_smsTemplate_id";
drop sequence "ca_smsConfig_id";
drop sequence "ca_folder_id";
drop sequence "ca_file_id";
drop sequence "ca_district_id";
drop sequence "ca_city_id";
drop sequence "ca_province_id";
drop sequence "ca_tableFormBuilder_id";
drop sequence "ca_sys_module_id";
drop sequence "ca_sys_moduleLine_id";

DROP TABLE "ca_meta_table_relation";
DROP TABLE "ca_meta_index_line";
DROP TABLE "ca_meta_index";
DROP TABLE "ca_meta_column";
DROP TABLE "ca_meta_table";
DROP TABLE "ca_meta_sequence";

DROP TABLE "ca_userRoleLine";
DROP TABLE "ca_roleResourceLine";
DROP TABLE "spring_role";
DROP TABLE "spring_resource";
DROP TABLE "spring_user";
DROP TABLE "persistent_logins";
DROP TABLE SPRING_SESSION_ATTRIBUTES;
DROP TABLE SPRING_SESSION;

DROP TABLE "ca_weixinConfig";
DROP TABLE "ca_smsTemplate";
DROP TABLE "ca_smsConfig";
DROP TABLE "ca_folder";
DROP TABLE "ca_file";
DROP TABLE "ca_district";
DROP TABLE "ca_city";
DROP TABLE "ca_province";
DROP TABLE "ca_tableFormBuilder";
DROP TABLE "ca_sys_moduleLine";
DROP TABLE "ca_sys_module";

CREATE TABLE "ca_meta_sequence" (
  "id" INT NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATE DEFAULT NULL,
  "currentTime" NUMBER(1) DEFAULT NULL,
  "cycle" NUMBER(1) DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "format" VARCHAR(255) DEFAULT NULL,
  "incrementBy" INT DEFAULT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "maxValue" INT DEFAULT NULL,
  "minValue" INT DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "nextValue" INT DEFAULT NULL,
  "sequenceType" VARCHAR(255) DEFAULT NULL
);

ALTER TABLE "ca_meta_sequence" ADD CONSTRAINT "uq_bsm_sequence_name" UNIQUE("name");

INSERT INTO "ca_meta_sequence" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(1, '角色编码', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 0, NULL, NULL, 'ROLE_%09d', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 999999999, 1, 'roleCode', 8, 'STRING');

INSERT INTO "ca_meta_sequence" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(2, '资源编码', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 0, NULL, NULL, 'RESOURCE_%09d', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 999999999, 1, 'resourceCode', 8, 'STRING');

INSERT INTO "ca_meta_sequence" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(3, '会员流水号', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 0, NULL, NULL, '%018d', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 9999999999, 1, 'membershipCode', 17, 'STRING');

CREATE TABLE "ca_meta_table" (
  "id" INT NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createPhysicalTable" NUMBER(1) DEFAULT NULL,
  "createdDate" DATE DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "engine" VARCHAR(255) DEFAULT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "pluralName" VARCHAR(255) DEFAULT NULL,
  "tableName" VARCHAR(255) DEFAULT NULL,
  "systemable" NUMBER(1) DEFAULT NULL,
  "readOnly" NUMBER(1) DEFAULT NULL
);

ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_name" UNIQUE("name");
ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_plural_name" UNIQUE("pluralName");
ALTER TABLE "ca_meta_table" ADD CONSTRAINT "uq_bsm_table_table_name" UNIQUE("tableName");

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1, '用户', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '用户', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'user', 'users', 'spring_user', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(2, '角色', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '角色', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'role', 'roles', 'spring_role', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(3, '资源', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '资源', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'resource', 'resources', 'spring_resource', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(4, '用户角色行', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '用户角色行', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'userRoleLine', 'userRoleLines', 'ca_userRoleLine', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(5, '角色资源行', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '角色资源行', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'roleResourceLine', 'roleResourceLines', 'ca_roleResourceLine', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(19, '省', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'province', 'provinces', 'ca_province', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(20, '市', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'city', 'cities', 'ca_city', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(22, '区', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'district', 'districts', 'ca_district', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(24, '文件', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'file', 'files', 'ca_file', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(34, '目录', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'folder', 'folders', 'ca_folder', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(37, '微信配置', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'weixinConfig', 'weixinConfigs', 'ca_weixinConfig', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(42, '短信配置', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'smsConfig', 'smsConfigs', 'ca_smsConfig', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(43, '短信模板', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'smsTemplate', 'smsTemplates', 'ca_smsTemplate', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(62, '模块', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'module', 'modules', 'ca_sys_module', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(63, '模块行', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'moduleLine', 'moduleLines', 'ca_sys_moduleLine', 1, 0);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(66, '表', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'table', 'tables', 'ca_meta_table', 1, 1);

INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(69, '表页面', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), '', 'INNODB', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'tableFormBuilder', 'tableFormBuilders', 'ca_tableFormBuilder', 1, 0);

CREATE TABLE "ca_meta_column" (
  "id" INT NOT NULL PRIMARY KEY,
  "autoIncrement" NUMBER(1) DEFAULT NULL,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATE DEFAULT NULL,
  "dataType" VARCHAR(255) DEFAULT NULL,
  "defaultValue" VARCHAR(255) DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "displayOrder" INT DEFAULT NULL,
  "indexName" VARCHAR(255) DEFAULT NULL,
  "indexStorage" VARCHAR(255) DEFAULT NULL,
  "indexType" VARCHAR(255) DEFAULT NULL,
  "insertable" NUMBER(1) DEFAULT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "length" INT DEFAULT NULL,
  "name" VARCHAR(255)  DEFAULT NULL,
  "nullable" NUMBER(1) DEFAULT NULL,
  "precision" INT DEFAULT NULL,
  "queryable" NUMBER(1) DEFAULT NULL,
  "scale" INT DEFAULT NULL,
  "seqId" INT DEFAULT NULL,
  "unsigned" NUMBER(1) DEFAULT NULL,
  "updatable" NUMBER(1) DEFAULT NULL,
  "displayable" NUMBER(1) DEFAULT NULL,
  "systemable" NUMBER(1) DEFAULT NULL,
  "tableId" INT DEFAULT NULL,
  CONSTRAINT "fk_bsm_column_seq_id" FOREIGN KEY ("seqId") REFERENCES "ca_meta_sequence" ("id"),
  CONSTRAINT "fk_bsm_column_table_id" FOREIGN KEY ("tableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_name" UNIQUE("tableId","name");
--ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_index_name" UNIQUE("tableId","indexName");
 
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(1, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '编号', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 1, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(2, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(3, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(4, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 1, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(5, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(6, 0, 'OPENID', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'OPENID', 5, 'uq_spring_user_openid', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 50, 'openId', 1, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(7, 0, '用户名', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '用户名', 6, 'uq_spring_user_username', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 50, 'username', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(8, 0, '真实姓名', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '真实姓名', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'realname', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(9, 0, '手机', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '手机', 8, 'uq_spring_user_mobile', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'mobile', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(10, 0, '邮箱', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '邮箱', 9, 'uq_spring_user_email', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'email', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(11, 0, '密码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'PASSWORD', NULL, '密码', 10, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 500, 'password', 0, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(12, 0, '明文密码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '明文密码', 11, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'cleartextPwd', 1, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(13, 0, 'TOKEN', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'TOKEN', 12, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'token', 1, NULL, 0, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(14, 0, '启用', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', 'true', '启用', 13, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 10, 'enabled', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(15, 0, '账号没有过期', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', 'true', '账号没有过期', 14, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 10, 'accountNonExpired', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(16, 0, '账号没有锁定', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', 'true', '账号没有锁定', 15, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 10, 'accountNonLocked', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(17, 0, '凭证没有过期', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', 'true', '凭证没有过期', 16, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 10, 'credentialsNonExpired', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(20, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 1, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(21, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(22, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(23, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 1, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(24, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(25, 0, '编码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '编码', 5, 'uq_spring_role_code', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'code', 0, NULL, 1, NULL, 1, 0, 1, 0, 0, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(26, 0, '备注', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '备注', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'remark', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 2);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(27, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(28, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(29, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(30, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(31, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(33, 0, 'URL表达式', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'URL表达式', 6, 'uq_spring_resource_url', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'url', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(34, 0, '备注', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '备注', 8, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'remark', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(35, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(36, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(37, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(38, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(39, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(40, 0, '用户编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '用户编号', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'userId', 0, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(41, 0, '角色编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '角色编号', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'roleId', 0, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 4);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(42, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(43, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(44, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(45, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(46, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(47, 0, '角色编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '角色编号', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'roleId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(48, 0, '资源编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '资源编号', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'resourceId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 5);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(56, 0, '编码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '编码', 5, 'uq_spring_resource_code', NULL, 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'code', 1, NULL, 1, NULL, 2, 0, 1, NULL, 0, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(58, 0, '操作', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '操作', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'action', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 3);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(155, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 19);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(156, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 19);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(157, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 19);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(158, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 19);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(159, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 19);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(160, 0, '编码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'code', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 19);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(161, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(162, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(163, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(164, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(165, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(166, 0, '编码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'code', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(167, 0, '省编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '省编号', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'provinceId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 20);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(176, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 1, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(177, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(178, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(179, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 1, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(180, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 1, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(181, 0, '编码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '编码', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'code', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(182, 0, '市编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '市编号', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'cityId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 22);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(192, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(193, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(194, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(195, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(196, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(197, 0, '大小', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '大小', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'size', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(198, 0, 'MD5', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'MD5', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'md5', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(199, 0, '类型', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '类型', 8, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'type', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(218, 0, '链接', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'ATTACHMENT', NULL, '链接', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 4000, 'url', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(219, 0, '头像文件编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '头像文件编号', 17, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'fileId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 1);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(220, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 34);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(221, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 34);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(222, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 34);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(223, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 34);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(224, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 34);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(225, 0, '父目录编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '父目录编号', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'parentFolderId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 34);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(226, 0, '目录编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '目录编号', 9, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'folderId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 24);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(244, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(245, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(246, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(247, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(248, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(249, 0, 'AppId', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'AppId', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'appId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(250, 0, 'AppSecret', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'AppSecret', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'appSecret', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(251, 0, 'Token', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'Token', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'token', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(252, 0, 'EncodingAESKey', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'EncodingAESKey', 8, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'encodingAESKey', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(253, 0, 'AccessToken', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'AccessToken', 9, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'accessToken', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(254, 0, 'ExpiresIn', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, 'ExpiresIn', 10, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'expiresIn', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(255, 0, 'Enable', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'INT', NULL, 'Enable', 11, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 10, 'enable', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(256, 0, 'Log', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, 'Log', 12, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'log', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(257, 0, 'JsapiTicket', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'JsapiTicket', 13, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 400, 'jsapiTicket', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 37);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(293, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(294, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(295, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(296, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(297, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(298, 0, 'Url', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, 'Url', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'url', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(299, 0, '用户名', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '用户名', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'username', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(300, 0, '密码', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '密码', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'password', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 42);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(301, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, NULL, 1, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(302, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 1, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(303, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(304, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(305, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, NULL, 1, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(306, 0, '签名', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '签名', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'sign', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(307, 0, '模板', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '模板', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'template', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(308, 0, '短信配置编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '短信配置编号', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'smsConfigId', 1, NULL, 1, NULL, NULL, 0, 1, NULL, 0, 43);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(432, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 0, 62);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(433, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 0, 62);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(434, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 62);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(435, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 0, 62);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(436, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 62);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(437, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(438, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(439, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(440, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(441, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(442, 0, '模块编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '模块编号', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'moduleId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(443, 0, '表编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '表编号', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'tableId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 63);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(461, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '编号', 0, NULL, NULL, 'PRIMARY', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'id', 0, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(462, 0, '中文名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '中文名称', 2, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 255, 'caption', 1, NULL, 1, NULL, NULL, 0, 1, 1, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(463, 0, '是否创建物理表', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', NULL, '是否创建物理表', 3, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createPhysicalTable', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(464, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 4, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 6, 'createdDate', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(465, 0, '描述', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '描述', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 255, 'description', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(466, 0, '引擎', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '引擎', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 255, 'engine', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(467, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 6, 'lastModifiedDate', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(468, 0, '英文名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '英文名称', 1, 'uq_bsm_table_name', 'BTREE', 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 255, 'name', 1, NULL, 1, NULL, NULL, 0, 1, 1, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(469, 0, '英文复数', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '英文复数', 8, 'uq_bsm_table_plural_name', 'BTREE', 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 255, 'pluralName', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(470, 0, '物理表名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '物理表名称', 9, 'uq_bsm_table_table_name', 'BTREE', 'UNIQUE', 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 255, 'tableName', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(471, 0, '是否系统表', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', NULL, '是否系统表', 10, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'systemable', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(473, 0, '是否只读', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BOOL', NULL, '是否只读', 11, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'readOnly', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 66);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(489, 1, '编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '主键', 0, NULL, NULL, 'PRIMARY', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'id', 0, NULL, 0, NULL, NULL, 1, 0, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(490, 0, '名称', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'name', 0, NULL, 1, NULL, NULL, 0, 1, 1, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(491, 0, '全文索引', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'TEXT', NULL, '全文索引', 2, 'ft_fulltext_body', NULL, 'FULLTEXT', 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'fullTextBody', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(492, 0, '创建时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'createdDate', 0, NULL, 0, NULL, NULL, 0, 0, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(493, 0, '修改时间', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, 0, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'lastModifiedDate', 1, NULL, 0, NULL, NULL, 0, 0, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(494, 0, '类型', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '类型', 5, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'type', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(495, 0, '设备', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'VARCHAR', NULL, '设备', 6, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 200, 'device', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(496, 0, '内容', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'LONGTEXT', NULL, '内容', 7, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 'body', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69);
INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "tableId") VALUES 
(497, 0, '表编号', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'BIGINT', NULL, '表编号', 8, NULL, NULL, NULL, 1, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 20, 'tableId', 1, NULL, 1, NULL, NULL, 0, 1, 0, 0, 69);

CREATE TABLE "ca_meta_index" (
  "id" INT  NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATE DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "indexStorage" VARCHAR(255) DEFAULT NULL,
  "indexType" VARCHAR(255) DEFAULT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "tableId" INT DEFAULT NULL,
  CONSTRAINT "fk_bsm_index_table_id" FOREIGN KEY ("tableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_index" ADD CONSTRAINT "uq_bsm_index_name" UNIQUE("tableId","name");

INSERT INTO "ca_meta_index" ("id", "caption", "createdDate", "description", "indexStorage", "indexType", "lastModifiedDate", "name", "tableId") VALUES
(12, '表页面类型', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '表页面类型唯一', 'BTREE', 'UNIQUE', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'tableTypeIndex', 69);

CREATE TABLE "ca_meta_index_line" (
  "id" INT NOT NULL PRIMARY KEY,
  "columnId" INT DEFAULT NULL,
  "indexId" INT DEFAULT NULL,
  CONSTRAINT "fk_bsm_index_line_index_id" FOREIGN KEY ("indexId") REFERENCES "ca_meta_index" ("id") ON DELETE CASCADE
);

INSERT INTO "ca_meta_index_line" ("id", "columnId", "indexId") VALUES (20, 494, 12);
INSERT INTO "ca_meta_index_line" ("id", "columnId", "indexId") VALUES (21, 497, 12);

CREATE TABLE "ca_meta_table_relation" (
  "id" INT NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATE DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "relationType" VARCHAR(255) DEFAULT NULL,
  "fromColumnId" INT DEFAULT NULL,
  "fromTableId" INT DEFAULT NULL,
  "toColumnId" INT DEFAULT NULL,
  "toTableId" INT DEFAULT NULL,
  CONSTRAINT "fk_bsm_table_relation_from_column_id" FOREIGN KEY ("fromColumnId") REFERENCES "ca_meta_column" ("id"),
  CONSTRAINT "fk_bsm_table_relation_from_table_id" FOREIGN KEY ("fromTableId") REFERENCES "ca_meta_table" ("id"),
  CONSTRAINT "fk_bsm_table_relation_to_column_id" FOREIGN KEY ("toColumnId") REFERENCES "ca_meta_column" ("id"),
  CONSTRAINT "fk_bsm_table_relation_to_table_id" FOREIGN KEY ("toTableId") REFERENCES "ca_meta_table" ("id")
);

ALTER TABLE "ca_meta_table_relation" ADD CONSTRAINT "uq_ca_table_relation" UNIQUE("fromTableId","toTableId","relationType","fromColumnId","toColumnId");

;
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(1, '角色行', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '角色行', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'roleLines', 'OneToMany', 1, 1, 40, 4);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(2, '角色', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '角色', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'role', 'ManyToOne', 41, 4, 20, 2);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(3, '资源行', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '资源行', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'resourceLines', 'OneToMany', 20, 2, 47, 5);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(4, '资源', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '资源', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'resource', 'ManyToOne', 48, 5, 27, 3);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(5, '城市', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'cities', 'OneToMany', 155, 19, 167, 20);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(6, '区', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'districts', 'OneToMany', 161, 20, 182, 22);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(7, '头像', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'avatar', 'ManyToOne', 219, 1, 192, 24);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(8, '子目录', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'childrenFolders', 'OneToMany', 220, 34, 225, 34);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(13, '文件', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'files', 'OneToMany', 220, 34, 226, 24);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(21, '模板', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'templates', 'OneToMany', 293, 42, 308, 43);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(22, '模块行', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'moduleLines', 'OneToMany', 432, 62, 442, 63);
INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES 
(23, '表', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'table', 'ManyToOne', 443, 63, 461, 66);


CREATE TABLE "spring_resource" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "url" VARCHAR(200) DEFAULT NULL,
  "remark" VARCHAR(200),
  "code" VARCHAR(200) DEFAULT NULL,
  "action" VARCHAR(200) DEFAULT NULL
);

ALTER TABLE "spring_resource" ADD CONSTRAINT "uq_spring_resource_code" UNIQUE("code");
ALTER TABLE "spring_resource" ADD CONSTRAINT "uq_spring_resource_url" UNIQUE("url");

INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(12, '业务数据', '业务数据 RESOURCE_BUSINESS /api/business/** *', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '/api/business/**', NULL, 'RESOURCE_BUSINESS', '*');
INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(13, '业务数据数字', '业务数据数字 RESOURCE_BUSINESS_COUNT /api/business/**/count *', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '/api/business/**/count', NULL, 'RESOURCE_BUSINESS_COUNT', '*');
INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(14, '用户', '用户 RESOURCE_USER /api/business/user** *', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '/api/business/user**', NULL, 'RESOURCE_USER', '*');
INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(15, '角色资源', '角色资源 RESOURCE_ROLE /api/business/role** *', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '/api/business/role**', NULL, 'RESOURCE_ROLE', '*');
INSERT INTO "spring_resource" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "url", "remark", "code", "action") VALUES
(16, '资源资源', '资源资源 RESOURCE_RESOURCE /api/business/resource** *', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '/api/business/resource**', NULL, 'RESOURCE_RESOURCE', '*');

CREATE TABLE "spring_user" (
  "id" INT NOT NULL PRIMARY KEY,
  "openId" VARCHAR(50) DEFAULT NULL,
  "username" VARCHAR(50) NOT NULL,
  "realname" VARCHAR(200) DEFAULT NULL,
  "mobile" VARCHAR(20) DEFAULT NULL,
  "email" VARCHAR(200) DEFAULT NULL,
  "password" VARCHAR(500) NOT NULL,
  "cleartextPwd" VARCHAR(200) DEFAULT NULL,
  "token" VARCHAR(200) DEFAULT NULL,
  "enabled" NUMBER(1) DEFAULT 1 NOT NULL,
  "accountNonExpired" NUMBER(1) DEFAULT 1 NOT NULL,
  "accountNonLocked" NUMBER(1) DEFAULT 1 NOT NULL,
  "credentialsNonExpired" NUMBER(1) DEFAULT 1 NOT NULL,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "fileId" INT DEFAULT NULL
);

ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_username" UNIQUE("username");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_openid" UNIQUE("openId");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_mobile" UNIQUE("mobile");
ALTER TABLE "spring_user" ADD CONSTRAINT "uq_spring_user_email" UNIQUE("email");

INSERT INTO "spring_user" ("id", "openId", "username", "realname", "mobile", "email", "password", "cleartextPwd", "token", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "createdDate", "lastModifiedDate", "name", "fullTextBody", "fileId") VALUES
(1, NULL, 'superadmin', '超级管理员', '11111111111', NULL, '$2a$10$R9HWotqpXzmLOU0aYlFkquxcs0jq7f1raKHHnsbfT51gM9TF.2q7q', NULL, '1094a2c5e57c4c7fa3922d69b012cfa7', 1, 1, 1, 1,  to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'),  to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), '超级管理员', '超级管理员 superadmin 超级管理员 11111111111 1 1 1 1', NULL);

CREATE TABLE "spring_role" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "code" VARCHAR(200) NOT NULL,
  "remark" VARCHAR(2000)
);

ALTER TABLE "spring_role" ADD CONSTRAINT "uq_spring_role_code" UNIQUE("code");

INSERT INTO "spring_role" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "code", "remark") VALUES
(11, '超级管理员', '超级管理员 ROLE_SUPER_ADMIN 超级管理员', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO "spring_role" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "code", "remark") VALUES
(20, '业务数据角色', '业务数据角色 ROLE_BUSINESS 业务数据用户', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 'ROLE_BUSINESS', '业务数据用户');

CREATE TABLE "ca_roleResourceLine" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "roleId" INT DEFAULT NULL,
  "resourceId" INT DEFAULT NULL
);

INSERT INTO "ca_roleResourceLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "roleId", "resourceId") VALUES
(11, '业务数据资源', NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 20, 12);
INSERT INTO "ca_roleResourceLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "roleId", "resourceId") VALUES
(12, '业务数据数字资源', NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 20, 13);

CREATE TABLE "ca_userRoleLine" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "userId" INT NOT NULL,
  "roleId" INT NOT NULL
);

INSERT INTO "ca_userRoleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "userId", "roleId") VALUES
(15, '超级管理员', NULL, to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 1, 11);

CREATE TABLE "ca_sys_module" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL
);

INSERT INTO "ca_sys_module" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate") VALUES
(1, '字典', '字典 bg-teal', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'));
INSERT INTO "ca_sys_module" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate") VALUES
(2, '文件', '文件 bg-primary', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'));
INSERT INTO "ca_sys_module" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate") VALUES
(3, '用户管理', '用户管理 bg-purple', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'));
INSERT INTO "ca_sys_module" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate") VALUES
(4, '微信', '微信', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL);

CREATE TABLE "ca_sys_moduleLine" (
  "id" INT NOT NULL PRIMARY KEY ,
  "name" VARCHAR(200)  NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "moduleId" INT DEFAULT NULL,
  "tableId" INT DEFAULT NULL
);

INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(1, '省', '省 1 19', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 1, 19);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(2, '市', '市 1 20', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 1, 20);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(3, '区', '区 1 22', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 1, 22);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(4, '文件', '文件 2 24', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 2, 24);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(5, '目录', '目录 2 34', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 2, 34);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(6, '用户', '用户 3 1', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 3, 1);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(7, '角色', '角色 3 2', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 3, 2);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(8, '资源', '资源 3 3', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 3, 3);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(9, '用户角色行', '用户角色行 3 4', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 3, 4);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(10, '角色资源行', '角色资源行 3 5', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), 3, 5);
INSERT INTO "ca_sys_moduleLine" ("id", "name", "fullTextBody", "createdDate", "lastModifiedDate", "moduleId", "tableId") VALUES 
(11, '微信配置', '微信配置 4 37', to_date('2022-02-28 17:06:29','yyyy-mm-dd HH24:MI:SS'), NULL, 4, 37);

CREATE TABLE "ca_tableFormBuilder" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "type" VARCHAR(200) DEFAULT NULL,
  "device" VARCHAR(200) DEFAULT NULL,
  "body" VARCHAR(4000),
  "tableId" INT DEFAULT NULL
);

CREATE TABLE SPRING_SESSION (
  PRIMARY_ID CHAR(36) NOT NULL,
  SESSION_ID CHAR(36) NOT NULL,
  CREATION_TIME NUMBER(19,0) NOT NULL,
  LAST_ACCESS_TIME NUMBER(19,0) NOT NULL,
  MAX_INACTIVE_INTERVAL NUMBER(10,0) NOT NULL,
  EXPIRY_TIME NUMBER(19,0) NOT NULL,
  PRINCIPAL_NAME VARCHAR2(100 CHAR),
  CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID CHAR(36) NOT NULL,
  ATTRIBUTE_NAME VARCHAR2(200 CHAR) NOT NULL,
  ATTRIBUTE_BYTES BLOB NOT NULL,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

CREATE TABLE "persistent_logins" (
  "username" VARCHAR(64) NOT NULL,
  "series" VARCHAR(64) NOT NULL,
  "token" VARCHAR(64) NOT NULL,
  "last_used" DATE NOT NULL
);

CREATE TABLE "ca_province" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "code" VARCHAR(200) DEFAULT NULL
) ;

CREATE TABLE "ca_city" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "code" VARCHAR(200) DEFAULT NULL,
  "provinceId" INT DEFAULT NULL
);

CREATE TABLE "ca_district" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG ,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "code" VARCHAR(200) DEFAULT NULL,
  "cityId" INT DEFAULT NULL
);

CREATE TABLE "ca_file" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG ,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "size" INT DEFAULT NULL,
  "md5" VARCHAR(200) DEFAULT NULL,
  "type" VARCHAR(200) DEFAULT NULL,
  "url" VARCHAR(4000) DEFAULT NULL,
  "folderId" INT DEFAULT NULL
);

CREATE TABLE "ca_folder" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG ,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "parentFolderId" INT DEFAULT NULL
) ;

CREATE TABLE "ca_smsConfig" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "url" VARCHAR(200) DEFAULT NULL,
  "username" VARCHAR(200) DEFAULT NULL,
  "password" VARCHAR(200) DEFAULT NULL
);

CREATE TABLE "ca_smsTemplate" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "sign" VARCHAR(200) DEFAULT NULL,
  "template" VARCHAR(200) DEFAULT NULL,
  "smsConfigId" INT DEFAULT NULL
);

CREATE TABLE "ca_weixinConfig" (
  "id" INT NOT NULL PRIMARY KEY,
  "name" VARCHAR(200) NOT NULL,
  "fullTextBody" LONG,
  "createdDate" DATE NOT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "appId" VARCHAR(200) DEFAULT NULL,
  "appSecret" VARCHAR(200) DEFAULT NULL,
  "token" VARCHAR(200) DEFAULT NULL,
  "encodingAESKey" VARCHAR(200) DEFAULT NULL,
  "accessToken" VARCHAR(200) DEFAULT NULL,
  "expiresIn" DATE DEFAULT NULL,
  "enable" INT DEFAULT NULL,
  "log" VARCHAR(4000),
  "jsapiTicket" VARCHAR(400) DEFAULT NULL
);

create sequence "ca_meta_sequence_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_meta_table_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_meta_column_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_meta_index_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_meta_index_line_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_meta_table_relation_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "spring_user_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "spring_resource_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "spring_role_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_roleResourceLine_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_userRoleLine_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_weixinConfig_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_smsTemplate_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_smsConfig_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_folder_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_file_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_district_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_city_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_province_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_tableFormBuilder_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_sys_module_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;
create sequence "ca_sys_moduleLine_id" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;

create or replace trigger "ca_meta_sequence_tg_insert" before insert on "ca_meta_sequence" for each row
begin
  select "ca_meta_sequence_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_meta_table_tg_insert" before insert on "ca_meta_table" for each row
begin
  select "ca_meta_table_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_meta_column_tg_insert" before insert on "ca_meta_column" for each row
begin
  select "ca_meta_column_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_meta_index_tg_insert" before insert on "ca_meta_index" for each row
begin
  select "ca_meta_index_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_meta_index_line_tg_insert" before insert on "ca_meta_index_line" for each row
begin
  select "ca_meta_index_line_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_meta_table_relation_tg_insert" before insert on "ca_meta_table_relation" for each row
begin
  select "ca_meta_table_relation_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "spring_user_tg_insert" before insert on "spring_user" for each row
begin
  select "spring_user_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "spring_resource_tg_insert" before insert on "spring_resource" for each row
begin
  select "spring_resource_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "spring_role_tg_insert" before insert on "spring_role" for each row
begin
  select "spring_role_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_roleResourceLine_tg_insert" before insert on "ca_roleResourceLine" for each row
begin
  select "ca_roleResourceLine_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_userRoleLine_tg_insert" before insert on "ca_userRoleLine" for each row
begin
  select "ca_userRoleLine_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_weixinConfig_tg_insert" before insert on "ca_weixinConfig" for each row
begin
  select "ca_weixinConfig_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_smsTemplate_tg_insert" before insert on "ca_smsTemplate" for each row
begin
  select "ca_smsTemplate_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_smsConfig_tg_insert" before insert on "ca_smsConfig" for each row
begin
  select "ca_smsConfig_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_folder_tg_insert" before insert on "ca_folder" for each row
begin
  select "ca_folder_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_file_tg_insert" before insert on "ca_file" for each row
begin
  select "ca_file_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_district_tg_insert" before insert on "ca_district" for each row
begin
  select "ca_district_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_city_tg_insert" before insert on "ca_city" for each row
begin
  select "ca_city_id".nextval into:new."id" from dual;
end;
/


create or replace trigger "ca_province_tg_insert" before insert on "ca_province" for each row
begin
  select "ca_province_id".nextval into:new."id" from dual;
end;
/


create or replace trigger "ca_tableFormBuilder_tg_insert" before insert on "ca_tableFormBuilder" for each row
begin
  select "ca_tableFormBuilder_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_sys_module_tg_insert" before insert on "ca_sys_module" for each row
begin
  select "ca_sys_module_id".nextval into:new."id" from dual;
end;
/

create or replace trigger "ca_sys_moduleLine_tg_insert" before insert on "ca_sys_moduleLine" for each row
begin
  select "ca_sys_moduleLine_id".nextval into:new."id" from dual;
end;
/

