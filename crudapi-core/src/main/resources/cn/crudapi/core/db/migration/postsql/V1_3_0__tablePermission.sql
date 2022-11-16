INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1002, '表权限', true, '2022-11-11 16:44:22.257000', '', 'INNODB', '2022-11-12 16:04:26.649000', 'tablePermission', 'ca_tablePermissions', 'ca_tablePermission', true, false);


INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "multipleValue", "tableId") VALUES
(1200, true, '编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '主键', 0, 'ca_tablePermission_key', NULL, 'PRIMARY', false, '2022-11-12 16:04:26.658000', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, false, 1002),
(1201, false, '名称', '2022-11-11 16:44:22.276000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2022-11-12 16:04:26.658000', 200, 'name', false, NULL, true, NULL, NULL, false, true, true, false, false, 1002),
(1202, false, '全文索引', '2022-11-11 16:44:22.276000', 'TEXT', NULL, '全文索引', 2, 'ca_tablePermission_ftidx', NULL, 'FULLTEXT', false, '2022-11-12 16:04:26.658000', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, false, 1002),
(1203, false, '创建时间', '2022-11-11 16:44:22.276000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2022-11-12 16:04:26.658000', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, false, 1002),
(1204, false, '修改时间', '2022-11-11 16:44:22.276000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2022-11-12 16:04:26.658000', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, NULL, 1002),
(1205, false, '创建者编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '创建者编号', 5, NULL, NULL, NULL, false, '2022-11-12 16:04:26.658000', 20, 'createById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1002),
(1206, false, '修改者编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '修改者编号', 6, NULL, NULL, NULL, false, '2022-11-12 16:04:26.658000', 20, 'updateById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1002),
(1207, false, '所有者编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '所有者编号', 7, NULL, NULL, NULL, true, '2022-11-12 16:04:26.658000', 20, 'ownerId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1002),
(1208, false, '是否删除', '2022-11-11 16:44:22.276000', 'BOOL', NULL, '是否删除', 8, NULL, NULL, NULL, true, '2022-11-12 16:04:26.658000', NULL, 'isDeleted', true, NULL, true, NULL, NULL, false, true, false, false, false, 1002),
(1209, false, '表编号', '2022-11-11 16:44:22.276000', 'BIGINT', NULL, '表编号', 9, NULL, NULL, NULL, true, '2022-11-12 16:04:26.658000', 20, 'tableId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1002),
(1210, false, '值', '2022-11-11 16:44:22.276000', 'VARCHAR', NULL, '值', 10, NULL, NULL, NULL, true, '2022-11-12 16:04:26.658000', 4000, 'value', true, NULL, true, NULL, NULL, false, true, false, false, false, 1002);

INSERT INTO "ca_meta_index" ("id", "caption", "createdDate", "description", "indexStorage", "indexType", "lastModifiedDate", "name", "tableId") VALUES
(1100, '名称唯一性索引', '2022-11-12 16:04:26.716000', '名称唯一性索引', NULL, 'UNIQUE', '2022-11-12 16:04:26.716000', 'uq_tableId_name', 1002);

INSERT INTO "ca_meta_index_line" ("id", "columnId", "indexId") VALUES
(1100, 1201, 1100),
(1101, 1209, 1100);

COMMIT;

CREATE TABLE "ca_tablePermission" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) NOT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "createById" bigint DEFAULT NULL,
  "updateById" bigint DEFAULT NULL,
  "ownerId" bigint DEFAULT NULL,
  "isDeleted" bool DEFAULT NULL,
  "tableId" bigint DEFAULT NULL,
  "value" varchar(4000) DEFAULT NULL
);

CREATE INDEX "ca_tablePermission_ftidx" ON "ca_tablePermission" ("fullTextBody");

ALTER TABLE "ca_tablePermission" ADD CONSTRAINT "uq_tableId_name" UNIQUE("tableId","name");

SELECT setval('"ca_tablePermission_id_seq"', 10000, true);

