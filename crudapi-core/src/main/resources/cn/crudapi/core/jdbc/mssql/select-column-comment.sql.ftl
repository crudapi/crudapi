select a.name as tableName, b.name as columName, c.value as comment

from sys.tables a left join sys.columns b on a.object_id=b.object_id

left join sys.extended_properties c on a.object_id=c.major_id

where a.name='${tableName}' and c.minor_id<>0 and b.column_id=c.minor_id

and a.schema_id=(select schema_id from sys.schemas where name='${tableSchema}')
