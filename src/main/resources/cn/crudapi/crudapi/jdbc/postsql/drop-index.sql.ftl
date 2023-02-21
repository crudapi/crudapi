<#if oldIndexType?? && (oldIndexType == "UNIQUE" || oldIndexType == "PRIMARY")>
  ALTER TABLE "${tableName}" DROP CONSTRAINT "${oldIndexName}"
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  DROP INDEX IF EXISTS "${oldIndexName}"
</#if>
