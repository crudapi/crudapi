SELECT n.nspname AS "tableSchema", c.relname AS "tableName",
CAST(obj_description(relfilenode,'pg_class') AS varchar) AS "comment"
FROM pg_class c
JOIN pg_catalog.pg_namespace n ON (c.relnamespace = n.oid)
WHERE  n.nspname = '${tableSchema}' AND relname = '${tableName}';
