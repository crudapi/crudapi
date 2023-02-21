<#if oldIndexType?? && oldIndexType == "PRIMARY">
  ALTER TABLE `${tableName}` DROP PRIMARY KEY
</#if>

<#if oldIndexType?? && oldIndexType == "UNIQUE">
  ALTER TABLE `${tableName}` DROP INDEX `${oldIndexName}`
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  ALTER TABLE `${tableName}` DROP INDEX `${oldIndexName}`
</#if>
