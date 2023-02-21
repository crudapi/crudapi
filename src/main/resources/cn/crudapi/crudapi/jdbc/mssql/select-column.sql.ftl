SELECT * FROM (
SELECT sc.colid AS columnId, sc.name as columnName, COLUMNPROPERTY( sc.id, sc.name, 'IsIdentity') as isIdentity
FROM syscolumns sc
INNER JOIN sys.tables t ON t.object_id = sc.id
WHERE  t.name='${tableName}'
AND t.schema_id=(SELECT schema_id FROM sys.schemas WHERE name='${tableSchema}')
) tt
WHERE tt.isIdentity = 1
