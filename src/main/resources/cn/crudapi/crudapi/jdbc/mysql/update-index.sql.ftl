<#if oldIndexType?? && oldIndexType == "PRIMARY">
  ALTER TABLE `${tableName}` DROP PRIMARY KEY;
</#if>

<#if oldIndexType?? && oldIndexType == "UNIQUE">
  ALTER TABLE `${tableName}` DROP INDEX `${oldIndexName}`;
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  ALTER TABLE `${tableName}` DROP INDEX `${oldIndexName}`;
</#if>

<#if indexEntity.indexType?? && indexEntity.indexType == "PRIMARY">
  ALTER TABLE `${tableName}` ADD PRIMARY KEY (<#list indexEntity.indexLineEntityList as indexLineEntity>`${indexLineEntity.columnEntity.name}`<#if indexLineEntity_has_next>,</#if></#list>)
</#if>

<#if indexEntity.indexType?? && indexEntity.indexType == "UNIQUE">
  ALTER TABLE `${tableName}` ADD CONSTRAINT `${indexEntity.name}` UNIQUE(<#list indexEntity.indexLineEntityList as indexLineEntity>`${indexLineEntity.columnEntity.name}`<#if indexLineEntity_has_next>,</#if></#list>)
</#if>

<#if indexEntity.indexType?? && (indexEntity.indexType == "INDEX" || indexEntity.indexType == "FULLTEXT")>
  ALTER TABLE `${tableName}` ADD ${indexEntity.indexType} `${indexEntity.name}`(<#list indexEntity.indexLineEntityList as indexLineEntity>`${indexLineEntity.columnEntity.name}`<#if indexLineEntity_has_next>,</#if></#list>)
</#if>
