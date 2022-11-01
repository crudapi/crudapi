INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1000, 'SQL接口', true, '2022-07-04 17:57:16.247000', '', 'INNODB', '2022-07-04 19:45:03.678000', 'sqlapi', 'sqlapis', 'ca_sqlapi', true, NULL);

INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "multipleValue", "tableId") VALUES
(1000, true, '编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '主键', 0, 'ca_sqlapi_pkey', NULL, 'PRIMARY', false, '2022-07-04 19:45:03.681000', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, false, 1000),
(1001, false, '名称', '2022-07-04 17:57:16.260000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2022-07-04 19:45:03.681000', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, false, false, 1000),
(1002, false, '分组', '2022-07-04 17:57:16.260000', 'VARCHAR', NULL, '分组', 2, NULL, NULL, NULL, true, '2022-07-04 19:45:03.681000', 200, 'group', false, NULL, true, NULL, NULL, false, true, false, false, false, 1000),
(1003, false, '数据SQL', '2022-07-04 17:57:16.260000', 'VARCHAR', NULL, '数据SQL', 3, NULL, NULL, NULL, true, '2022-07-04 19:45:03.681000', 4000, 'dataSql', false, NULL, true, NULL, NULL, false, true, false, false, false, 1000),
(1004, false, '全文索引', '2022-07-04 17:57:16.260000', 'TEXT', NULL, '全文索引', 5, 'ca_sqlapi_ftidx', NULL, 'FULLTEXT', false, '2022-07-04 19:45:03.681000', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, false, 1000),
(1005, false, '创建时间', '2022-07-04 17:57:16.260000', 'DATETIME', NULL, '创建时间', 6, NULL, NULL, NULL, false, '2022-07-04 19:45:03.681000', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, false, 1000),
(1006, false, '修改时间', '2022-07-04 17:57:16.260000', 'DATETIME', NULL, '修改时间', 7, NULL, NULL, NULL, false, '2022-07-04 19:45:03.681000', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, false, 1000),
(1007, false, '创建者编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '创建者编号', 8, NULL, NULL, NULL, false, '2022-07-04 19:45:03.681000', 20, 'createById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1000),
(1008, false, '修改者编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '修改者编号', 9, NULL, NULL, NULL, false, '2022-07-04 19:45:03.681000', 20, 'updateById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1000),
(1009, false, '所有者编号', '2022-07-04 17:57:16.260000', 'BIGINT', NULL, '所有者编号', 10, NULL, NULL, NULL, true, '2022-07-04 19:45:03.681000', 20, 'ownerId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1000),
(1010, false, '是否删除', '2022-07-04 17:57:16.260000', 'BOOL', NULL, '是否删除', 11, NULL, NULL, NULL, true, '2022-07-04 19:45:03.681000', NULL, 'isDeleted', true, NULL, true, NULL, NULL, false, true, false, false, false, 1000),
(1011, false, '个数SQL', '2022-07-04 19:44:11.138000', 'VARCHAR', NULL, '个数SQL', 4, NULL, NULL, NULL, true, '2022-07-04 19:45:03.681000', 4000, 'countSql', false, NULL, true, NULL, NULL, false, true, false, false, false, 1000);

CREATE TABLE "ca_sqlapi" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "group" varchar(200) NOT NULL,
  "dataSql" varchar(4000) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "createById" bigint DEFAULT NULL,
  "updateById" bigint DEFAULT NULL,
  "ownerId" bigint DEFAULT NULL,
  "isDeleted" bool DEFAULT NULL,
  "countSql" varchar(4000)  NOT NULL
);

CREATE INDEX "ca_sqlapi_ftidx" ON "ca_sqlapi" ("fullTextBody");

INSERT INTO "ca_sqlapi" ("id", "name", "group", "dataSql", "fullTextBody", "createdDate", "lastModifiedDate", "createById", "updateById", "ownerId", "isDeleted", "countSql") VALUES
(1, 'user', 'ums', 'SELECT * FROM spring_user', 'user ums SELECT * FROM spring_user SELECT count(*) FROM spring_user 1 1 1 false', '2022-07-04 18:01:29', '2022-07-04 20:54:40', 1, 1, 1, false, 'SELECT count(*) FROM spring_user');

SELECT setval('"ca_sqlapi_id_seq"', 10000, true);
