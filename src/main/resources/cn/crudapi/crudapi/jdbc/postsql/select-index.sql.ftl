SELECT "tableSchema","tableName","columnName","indexName","indexSeq","isPrimary","isUnique"
FROM (SELECT n.nspname                                        AS "tableSchema",
             ct.relname                                       AS "tableName",
             a.attname                                        AS "columnName",
             ci.relname                                       AS "indexName",
             (information_schema._pg_expandarray(i.indkey)).n AS "indexSeq",
             information_schema._pg_expandarray(i.indkey)     AS "keys",
             a.attnum                                         AS "attnum",
             i.indisprimary                                   AS "isPrimary",
             i.indisunique                                    AS "isUnique"
      FROM pg_catalog.pg_class ct
         JOIN pg_catalog.pg_attribute a ON (ct.oid = a.attrelid)
         JOIN pg_catalog.pg_namespace n ON (ct.relnamespace = n.oid)
         JOIN pg_catalog.pg_index i ON (a.attrelid = i.indrelid)
         JOIN pg_catalog.pg_class ci ON (ci.oid = i.indexrelid)
      WHERE n.nspname = '${tableSchema}'
        AND ct.relname = '${tableName}') result
where result."attnum" = (result."keys").x
ORDER BY result."tableName", result."indexName", result."indexSeq";
