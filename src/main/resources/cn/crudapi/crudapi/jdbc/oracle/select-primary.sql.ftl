SELECT cu.*
FROM user_cons_columns cu, user_constraints au
WHERE cu.TABLE_NAME = '${tableName}' AND
cu.constraint_name = au.constraint_name
AND au.constraint_type = 'P'
