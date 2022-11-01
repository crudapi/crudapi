INSERT INTO "ca_meta_table" ("id", "caption", "createPhysicalTable", "createdDate", "description", "engine", "lastModifiedDate", "name", "pluralName", "tableName", "systemable", "readOnly") VALUES
(1001, '字段扩展属性', true, '2022-09-18 21:20:51.146000', '', 'INNODB', '2022-09-18 21:21:56.273000', 'columnExtProperty', 'columnExtPropertys', 'ca_columnExtProperty', true, NULL);

INSERT INTO "ca_meta_column" ("id", "autoIncrement", "caption", "createdDate", "dataType", "defaultValue", "description", "displayOrder", "indexName", "indexStorage", "indexType", "insertable", "lastModifiedDate", "length", "name", "nullable", "precision", "queryable", "scale", "seqId", "unsigned", "updatable", "displayable", "systemable", "multipleValue", "tableId") VALUES
(1100, true, '编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '主键', 0, 'ca_columnExtProperty_key', NULL, 'PRIMARY', false, '2022-09-18 21:21:56.279000', 20, 'id', false, NULL, false, NULL, NULL, true, false, false, false, false, 1001),
(1101, false, '名称', '2022-09-18 21:20:51.172000', 'VARCHAR', NULL, '名称', 1, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', 200, 'name', true, NULL, true, NULL, NULL, false, true, true, false, false, 1001),
(1102, false, '全文索引', '2022-09-18 21:20:51.172000', 'TEXT', NULL, '全文索引', 2, 'ca_columnExtProperty_ftidx', NULL, 'FULLTEXT', false, '2022-09-18 21:21:56.279000', NULL, 'fullTextBody', true, NULL, false, NULL, NULL, false, false, false, false, false, 1001),
(1103, false, '创建时间', '2022-09-18 21:20:51.172000', 'DATETIME', NULL, '创建时间', 3, NULL, NULL, NULL, false, '2022-09-18 21:21:56.279000', NULL, 'createdDate', false, NULL, false, NULL, NULL, false, false, false, false, false, 1001),
(1104, false, '修改时间', '2022-09-18 21:20:51.172000', 'DATETIME', NULL, '修改时间', 4, NULL, NULL, NULL, false, '2022-09-18 21:21:56.279000', NULL, 'lastModifiedDate', true, NULL, false, NULL, NULL, false, false, false, false, false, 1001),
(1105, false, '创建者编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '创建者编号', 5, NULL, NULL, NULL, false, '2022-09-18 21:21:56.279000', 20, 'createById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1001),
(1106, false, '修改者编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '修改者编号', 6, NULL, NULL, NULL, false, '2022-09-18 21:21:56.279000', 20, 'updateById', true, NULL, true, NULL, NULL, false, false, false, false, false, 1001),
(1107, false, '所有者编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '所有者编号', 7, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', 20, 'ownerId', true, NULL, true, NULL, NULL, false, true, false, false, false, 1001),
(1108, false, '是否删除', '2022-09-18 21:20:51.172000', 'BOOL', NULL, '是否删除', 8, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', NULL, 'isDeleted', true, NULL, true, NULL, NULL, false, true, false, false, false, 1001),
(1109, false, '表编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '表编号', 9, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', 20, 'tableId', false, NULL, true, NULL, NULL, false, true, false, false, false, 1001),
(1110, false, '列编号', '2022-09-18 21:20:51.172000', 'BIGINT', NULL, '列编号', 10, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', 20, 'columnId', false, NULL, true, NULL, NULL, false, true, false, false, false, 1001),
(1111, false, '键', '2022-09-18 21:20:51.172000', 'VARCHAR', NULL, '键', 11, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', 200, 'key', false, NULL, true, NULL, NULL, false, true, false, false, false, 1001),
(1112, false, '值', '2022-09-18 21:20:51.172000', 'VARCHAR', NULL, '值', 12, NULL, NULL, NULL, true, '2022-09-18 21:21:56.279000', 2000, 'value', false, NULL, true, NULL, NULL, false, true, false, false, false, 1001);


INSERT INTO "ca_meta_index" ("id", "caption", "createdDate", "description", "indexStorage", "indexType", "lastModifiedDate", "name", "tableId") VALUES
(1000, '表列键唯一索引', '2022-09-18 21:20:51.204000', '表列键唯一索引', NULL, 'UNIQUE', '2022-09-18 21:21:56.320000', 'uq_table_column_key', 1001);

INSERT INTO "ca_meta_index_line" ("id", "columnId", "indexId") VALUES
(1000, 1109, 1000),
(1001, 1110, 1000),
(1002, 1111, 1000);

CREATE TABLE "ca_columnExtProperty" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "name" varchar(200) DEFAULT NULL,
  "fullTextBody" text,
  "createdDate" TIMESTAMP NOT NULL,
  "lastModifiedDate" TIMESTAMP DEFAULT NULL,
  "createById" bigint DEFAULT NULL,
  "updateById" bigint DEFAULT NULL,
  "ownerId" bigint DEFAULT NULL,
  "isDeleted" bool DEFAULT NULL,
  "tableId" bigint NOT NULL,
  "columnId" bigint NOT NULL,
  "key" varchar(200) NOT NULL,
  "value" varchar(2000) NOT NULL
);

CREATE INDEX "ca_columnExtProperty_ftidx" ON "ca_columnExtProperty" ("fullTextBody");

ALTER TABLE "ca_columnExtProperty" ADD CONSTRAINT "uq_table_column_key" UNIQUE("tableId","columnId","key");

SELECT setval('"ca_columnExtProperty_id_seq"', 10000, true);

