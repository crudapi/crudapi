SELECT `CONSTRAINT_NAME`, `TABLE_SCHEMA`, `TABLE_NAME`, `CONSTRAINT_TYPE`
FROM `INFORMATION_SCHEMA`.`TABLE_CONSTRAINTS`
WHERE `TABLE_SCHEMA` = '${tableSchema}' AND `TABLE_NAME` = '${tableName}'