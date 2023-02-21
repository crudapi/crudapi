<#if oldIndexType?? && oldIndexType == "UNIQUE"  || oldIndexType == "PRIMARY">
  ALTER TABLE "${tableName}" DROP CONSTRAINT "${oldIndexName}";
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  DROP INDEX IF EXISTS "${oldIndexName}";
</#if>

<#if indexEntity.indexType?? && indexEntity.indexType == "PRIMARY">
  ALTER TABLE "${tableName}" ADD CONSTRAINT "${indexEntity.name}" PRIMARY KEY (<#list indexEntity.indexLineEntityList as indexLineEntity>"${indexLineEntity.columnEntity.name}"<#if indexLineEntity_has_next>,</#if></#list>);

  COMMENT ON CONSTRAINT "${indexEntity.name}" ON "${tableName}" IS '${indexEntity.caption}';
</#if>

<#if indexEntity.indexType?? && indexEntity.indexType == "UNIQUE">
  ALTER TABLE "${tableName}" ADD CONSTRAINT "${indexEntity.name}" UNIQUE(<#list indexEntity.indexLineEntityList as indexLineEntity>"${indexLineEntity.columnEntity.name}"<#if indexLineEntity_has_next>,</#if></#list>);

  COMMENT ON CONSTRAINT "${indexEntity.name}" ON "${tableName}" IS '${indexEntity.caption}';
</#if>

<#if indexEntity.indexType?? && (indexEntity.indexType == "INDEX" || indexEntity.indexType == "FULLTEXT")>
  CREATE INDEX "${indexEntity.name}" ON "${tableName}" (<#list indexEntity.indexLineEntityList as indexLineEntity>"${indexLineEntity.columnEntity.name}"<#if indexLineEntity_has_next>,</#if></#list>);

  COMMENT ON INDEX "${indexEntity.name}" IS '${indexEntity.caption}';
</#if>
