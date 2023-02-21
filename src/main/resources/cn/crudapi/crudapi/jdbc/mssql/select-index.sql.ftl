SELECT si.name AS indexName,
sc.name AS columnName,
t.name AS tableName,
soi.xtype AS indexType
FROM sysindexes si
INNER JOIN sysindexkeys sik ON si.id = sik.id AND si.indid = sik.indid
INNER JOIN syscolumns sc ON sc.id = sik.id AND sc.colid = sik.colid
INNER JOIN sys.tables t ON t.object_id = si.id
LEFT JOIN sysobjects soi ON soi.name = si.name
WHERE  t.name='${tableName}'
AND t.schema_id=(SELECT schema_id FROM sys.schemas WHERE name='${tableSchema}')

