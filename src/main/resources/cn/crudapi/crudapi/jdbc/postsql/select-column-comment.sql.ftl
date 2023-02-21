SELECT a.attname AS "columnName",
       d.description AS "comment"
FROM pg_catalog.pg_attribute a
     JOIN pg_catalog.pg_class c ON (a.attrelid = c.oid)
     JOIN pg_catalog.pg_namespace n ON (c.relnamespace = n.oid)
     LEFT OUTER JOIN pg_catalog.pg_description d ON (a.attrelid = d.objoid AND a.attnum = d.objsubid)
WHERE  n.nspname = '${tableSchema}'
       AND c.relname = '${tableName}'
       AND a.attnum > 0
ORDER BY a.attnum
