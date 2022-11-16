INSERT INTO "ca_meta_table_relation" ("id", "caption", "createdDate", "description", "lastModifiedDate", "name", "relationType", "fromColumnId", "fromTableId", "toColumnId", "toTableId") VALUES
(1000, '表权限', '2022-11-11 20:52:36.249000', NULL, '2022-11-11 20:52:36.249000', 'tablePermission', 'ManyToOne', 1310, 1003, 1200, 1002),
(1001, '表权限行', '2022-11-11 20:54:25.660000', NULL, '2022-11-11 20:54:25.660000', 'tablePermissionLines', 'OneToMany', 20, 2, 1309, 1003);
COMMIT;



