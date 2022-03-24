SELECT  c.relname as "tableName",
       i.relname as "indexName",
       dv.description AS "comment"
FROM  pg_catalog.pg_index x
       JOIN pg_catalog.pg_class c ON c.oid = x.indrelid
       JOIN pg_catalog.pg_namespace n ON (c.relnamespace = n.oid)
       JOIN pg_catalog.pg_class i ON i.oid = x.indexrelid
       LEFT OUTER JOIN
       ((SELECT x.indexrelid as objoid, d.description
              FROM  pg_catalog.pg_index x
                     JOIN pg_catalog.pg_class c ON c.oid = x.indrelid
                     JOIN pg_catalog.pg_namespace n ON (c.relnamespace = n.oid)
                     JOIN pg_catalog.pg_description d ON x.indexrelid = d.objoid
              WHERE n.nspname = '${tableSchema}' AND c.relname = '${tableName}')
              union
              (SELECT x.conindid as objid, d.description
              FROM  pg_constraint x
                     JOIN pg_class c ON c.oid = x.conrelid
                     JOIN pg_catalog.pg_namespace n ON (c.relnamespace = n.oid)
                     LEFT OUTER JOIN pg_description d ON x.oid = d.objoid
              WHERE n.nspname = '${tableSchema}' AND c.relname = '${tableName}')
       ) as dv ON x.indexrelid = dv.objoid
WHERE n.nspname = '${tableSchema}' AND c.relname = '${tableName}'
