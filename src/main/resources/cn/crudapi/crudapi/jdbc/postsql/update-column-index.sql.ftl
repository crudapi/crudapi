<#if oldIndexType?? && (oldIndexType == "UNIQUE" || oldIndexType == "PRIMARY")>
  ALTER TABLE "${tableName}" DROP CONSTRAINT "${oldIndexName}";
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  DROP INDEX IF EXISTS "${oldIndexName}";
</#if>

<#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY">
  ALTER TABLE "${tableName}" ADD CONSTRAINT "${columnEntity.indexName}" PRIMARY KEY ("${columnEntity.name}")
</#if>

<#if columnEntity.indexType?? && columnEntity.indexType == "UNIQUE">
  ALTER TABLE "${tableName}" ADD CONSTRAINT "${columnEntity.indexName}" UNIQUE("${columnEntity.name}")
</#if>

<#if columnEntity.indexType?? && (columnEntity.indexType == "INDEX" || columnEntity.indexType == "FULLTEXT")>
  CREATE INDEX "${columnEntity.indexName}" ON "${tableName}" ("${columnEntity.name}")
</#if>
