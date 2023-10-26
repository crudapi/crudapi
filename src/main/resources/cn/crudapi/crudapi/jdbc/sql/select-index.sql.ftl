SELECT `TABLE_SCHEMA`, `TABLE_NAME`, `NON_UNIQUE`, `INDEX_SCHEMA`, `INDEX_NAME`, `SEQ_IN_INDEX`, `COLUMN_NAME`, `NULLABLE`, `INDEX_TYPE`, `COMMENT`, `INDEX_COMMENT`
FROM `INFORMATION_SCHEMA`.`STATISTICS`
WHERE `TABLE_SCHEMA` = '${tableSchema}' AND `TABLE_NAME` = '${tableName}'
