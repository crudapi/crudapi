INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1003, '角色表权限行', true, '2022-11-11 17:34:53.708000', '', 'INNODB', '2022-11-11 17:34:53.709000', 'roleTablePermissionLine', 'roleTablePermissionLines', 'ca_roleTablePermissionLine', true, false);

INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "multipleValue", "tableId") VALUES
(1300, true, '编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '主键', 0, 'ca_roleTablePermissionLine_key', NULL, 'PRIMARY', false, '2022-11-11 17:34:53.729000', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, false, 1003),
(1301, false, '名称', '2022-11-11 17:34:53.729000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2022-11-11 17:34:53.729000', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, false, false, 1003),
(1302, false, '全文索引', '2022-11-11 17:34:53.729000', 'TEXT', NULL, '全文索引', 2, 'ca_roleTablePermissionLine_ftidx', NULL, 'FULLTEXT', false, '2022-11-11 17:34:53.729000', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, false, 1003),
(1303, false, '创建时间', '2022-11-11 17:34:53.729000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2022-11-11 17:34:53.729000', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, false, 1003),
(1304, false, '修改时间', '2022-11-11 17:34:53.729000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2022-11-11 17:34:53.729000', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, NULL, 1003),
(1305, false, '创建者编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '创建者编号', 5, NULL, NULL, NULL, false, '2022-11-11 17:34:53.729000', 20, 'createById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1003),
(1306, false, '修改者编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '修改者编号', 6, NULL, NULL, NULL, false, '2022-11-11 17:34:53.729000', 20, 'updateById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1003),
(1307, false, '所有者编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '所有者编号', 7, NULL, NULL, NULL, true, '2022-11-11 17:34:53.729000', 20, 'ownerId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1003),
(1308, false, '是否删除', '2022-11-11 17:34:53.729000', 'BOOL', NULL, '是否删除', 8, NULL, NULL, NULL, true, '2022-11-11 17:34:53.729000', NULL, 'isDeleted', true, NULL, true, NULL, NULL, false, true, false, false, false, 1003),
(1309, false, '角色编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '角色编号', 9, NULL, NULL, NULL, true, '2022-11-11 17:34:53.729000', 20, 'roleId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1003),
(1310, false, '表权限编号', '2022-11-11 17:34:53.729000', 'BIGINT', NULL, '表权限编号', 10, NULL, NULL, NULL, true, '2022-11-11 17:34:53.729000', 20, 'tablePermissionId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1003);

CREATE TABLE "ca_roleTablePermissionLine" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "createById" bigint DEFAULT NULL,
  "updateById" bigint DEFAULT NULL,
  "ownerId" bigint DEFAULT NULL,
  "isDeleted" bool DEFAULT NULL,
  "roleId" bigint DEFAULT NULL,
  "tablePermissionId" bigint DEFAULT NULL
);

CREATE INDEX "ca_roleTablePermissionLine_ftidx" ON "ca_roleTablePermissionLine" ("fullTextBody");

SELECT setval('"ca_roleTablePermissionLine_id_seq"', 10000, true);

