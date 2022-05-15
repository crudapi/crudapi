CREATE TABLE `${tableName}` (
<#list columnEntityList as columnEntity>
  <#if columnEntity.dataType == "BOOL">
    `${columnEntity.name}` BIT(1)<#if columnEntity.defaultValue??> DEFAULT <#if columnEntity.defaultValue == "true">1<#else>0</#if></#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "INT">
    `${columnEntity.name}` INT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.autoIncrement == true> AUTO_INCREMENT</#if><#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY"> PRIMARY KEY</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "BIGINT">
    `${columnEntity.name}` BIGINT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.autoIncrement == true> AUTO_INCREMENT</#if><#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY"> PRIMARY KEY</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "FLOAT">
    `${columnEntity.name}` FLOAT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "DOUBLE">
    `${columnEntity.name}` DOUBLE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "DECIMAL">
    `${columnEntity.name}` DECIMAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "DATE">
    `${columnEntity.name}` DATE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "TIME">
    `${columnEntity.name}` TIME<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "DATETIME">
    `${columnEntity.name}` DATETIME<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "TIMESTAMP">
    `${columnEntity.name}` TIMESTAMP<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "CHAR">
    `${columnEntity.name}` CHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY"> PRIMARY KEY</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "VARCHAR">
    `${columnEntity.name}` VARCHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY"> PRIMARY KEY</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "PASSWORD">
    `${columnEntity.name}` VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "ATTACHMENT">
    `${columnEntity.name}` VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "TEXT">
    `${columnEntity.name}` TEXT<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "LONGTEXT">
    `${columnEntity.name}` LONGTEXT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "BLOB">
    `${columnEntity.name}` BLOB<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#elseif columnEntity.dataType == "LONGBLOB">
    `${columnEntity.name}` LONGBLOB<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  <#else>
    `${columnEntity.name}` VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY"> PRIMARY KEY</#if> COMMENT '${columnEntity.caption}'<#if columnEntity_has_next>,</#if>
  </#if>
</#list>
) ENGINE=${engine} DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

<#list columnEntityList as columnEntity>
  <#if columnEntity.indexType?? && columnEntity.indexType == "UNIQUE">
    ALTER TABLE `${tableName}` ADD CONSTRAINT `${columnEntity.indexName}` UNIQUE(`${columnEntity.name}`);
  </#if>

  <#if columnEntity.indexType?? && (columnEntity.indexType == "INDEX" || columnEntity.indexType == "FULLTEXT")>
    ALTER TABLE `${tableName}` ADD ${columnEntity.indexType} `${columnEntity.indexName}` (`${columnEntity.name}`);
  </#if>
</#list>

<#if indexEntityList??>
  <#list indexEntityList as indexEntity>
    <#if indexEntity.indexType?? && indexEntity.indexType == "PRIMARY">
      ALTER TABLE `${tableName}` ADD PRIMARY KEY (<#list indexEntity.indexLineEntityList as indexLineEntity>`${indexLineEntity.columnEntity.name}`<#if indexLineEntity_has_next>,</#if></#list>);
    </#if>

    <#if indexEntity.indexType?? && indexEntity.indexType == "UNIQUE">
      ALTER TABLE `${tableName}` ADD CONSTRAINT `${indexEntity.name}` UNIQUE(<#list indexEntity.indexLineEntityList as indexLineEntity>`${indexLineEntity.columnEntity.name}`<#if indexLineEntity_has_next>,</#if></#list>);
    </#if>

    <#if indexEntity.indexType?? && (indexEntity.indexType == "INDEX" || indexEntity.indexType == "FULLTEXT")>
      ALTER TABLE `${tableName}` ADD  ${indexEntity.indexType} `${indexEntity.name}`(<#list indexEntity.indexLineEntityList as indexLineEntity>`${indexLineEntity.columnEntity.name}`<#if indexLineEntity_has_next>,</#if></#list>);
    </#if>
  </#list>
</#if>

ALTER TABLE `${tableName}` COMMENT '${caption}';

  