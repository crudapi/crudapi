<#if oldIndexType?? && oldIndexType == "PRIMARY">
  ALTER TABLE "${tableName}" DROP PRIMARY KEY
</#if>

<#if oldIndexType?? && oldIndexType == "UNIQUE">
  ALTER TABLE "${tableName}" DROP CONSTRAINT "${oldIndexName}"
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  DROP INDEX "${oldIndexName}"
</#if>
