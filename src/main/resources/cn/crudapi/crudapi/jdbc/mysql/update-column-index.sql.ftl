<#if oldIndexType?? && oldIndexType == "PRIMARY">
  ALTER TABLE `${tableName}` DROP PRIMARY KEY;
</#if>

<#if oldIndexType?? && oldIndexType == "UNIQUE">
  ALTER TABLE `${tableName}` DROP INDEX `${oldIndexName}`;
</#if>

<#if oldIndexType?? && (oldIndexType == "INDEX" || oldIndexType == "FULLTEXT")>
  ALTER TABLE `${tableName}` DROP INDEX `${oldIndexName}`;
</#if>

<#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY">
  ALTER TABLE `${tableName}` ADD PRIMARY KEY (`${columnEntity.name}`)
</#if>

<#if columnEntity.indexType?? && columnEntity.indexType == "UNIQUE">
  ALTER TABLE `${tableName}` ADD CONSTRAINT `${columnEntity.indexName}` UNIQUE(`${columnEntity.name}`);
</#if>

<#if columnEntity.indexType?? && (columnEntity.indexType == "INDEX" || columnEntity.indexType == "FULLTEXT")>
  ALTER TABLE `${tableName}` ADD ${columnEntity.indexType} `${columnEntity.indexName}` (`${columnEntity.name}`);
</#if>
