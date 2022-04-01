UPDATE "ca_meta_column" c
SET "indexName" = (select (t."tableName" || '_pkey')
  from "ca_meta_table" t where t."id" = c."tableId")
WHERE "indexType" = 'PRIMARY'
AND c."tableId" in (SELECT id FROM "ca_meta_table" where "systemable" = true);

UPDATE "ca_meta_column" c
SET "indexName" = (select (t."tableName" || '_ftidx')
  from "ca_meta_table" t where t."id" = c."tableId")
WHERE "indexType" = 'FULLTEXT'
AND c."tableId" in (SELECT id FROM "ca_meta_table" where "systemable" = true);

ALTER TABLE "ca_meta_column" DROP CONSTRAINT "uq_bsm_column_index_name";
ALTER TABLE "ca_meta_column" ADD CONSTRAINT "uq_bsm_column_index_name" UNIQUE("indexName");

CREATE INDEX "ca_userRoleLine_ftidx" ON "ca_userRoleLine" ("fullTextBody");
CREATE INDEX "ca_roleResourceLine_ftidx" ON "ca_roleResourceLine" ("fullTextBody");
CREATE INDEX "spring_role_ftidx" ON "spring_role" ("fullTextBody");
CREATE INDEX "spring_resource_ftidx" ON "spring_resource" ("fullTextBody");
CREATE INDEX "spring_user_ftidx" ON "spring_user" ("fullTextBody");
CREATE INDEX "ca_weixinConfig_ftidx" ON "ca_weixinConfig" ("fullTextBody");
CREATE INDEX "ca_smsTemplate_ftidx" ON "ca_smsTemplate" ("fullTextBody");
CREATE INDEX "ca_smsConfig_ftidx" ON "ca_smsConfig" ("fullTextBody");
CREATE INDEX "ca_folder_ftidx" ON "ca_folder" ("fullTextBody");
CREATE INDEX "ca_file_ftidx" ON "ca_file" ("fullTextBody");
CREATE INDEX "ca_district_ftidx" ON "ca_district" ("fullTextBody");
CREATE INDEX "ca_city_ftidx" ON "ca_city" ("fullTextBody");
CREATE INDEX "ca_province_ftidx" ON "ca_province" ("fullTextBody");
CREATE INDEX "ca_tableFormBuilder_ftidx" ON "ca_tableFormBuilder" ("fullTextBody");
CREATE INDEX "ca_sys_moduleLine_ftidx" ON "ca_sys_moduleLine" ("fullTextBody");
CREATE INDEX "ca_sys_module_ftidx" ON "ca_sys_module" ("fullTextBody");
