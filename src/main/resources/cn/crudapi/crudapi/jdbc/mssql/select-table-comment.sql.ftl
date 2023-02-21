SELECT t.name as tableName, value as comment
FROM sys.tables t
LEFT JOIN sys.extended_properties c ON t.object_id= c.major_id
WHERE t.name='${tableName}' AND t.type= 'U' AND c.minor_id= 0
AND t.schema_id=(select schema_id from sys.schemas where name='${tableSchema}')

