ALTER TABLE `${tableName}` CHANGE `${oldColumnName}`
<#if columnEntity.dataType == "BOOL">
  `${columnEntity.name}` BIT(1)<#if columnEntity.defaultValue??> DEFAULT <#if columnEntity.defaultValue == "true">1<#else>0</#if></#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "INT">
  `${columnEntity.name}` INT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.autoIncrement == true> AUTO_INCREMENT</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "BIGINT">
  `${columnEntity.name}` BIGINT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if><#if columnEntity.autoIncrement == true> AUTO_INCREMENT</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "FLOAT">
  `${columnEntity.name}` FLOAT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "DOUBLE">
  `${columnEntity.name}` DOUBLE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "DECIMAL">
  `${columnEntity.name}` DECIMAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "DATE">
  `${columnEntity.name}` DATE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "TIME">
  `${columnEntity.name}` TIME<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "DATETIME">
  `${columnEntity.name}` DATETIME<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "TIMESTAMP">
  `${columnEntity.name}` TIMESTAMP<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "CHAR">
  `${columnEntity.name}` CHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "VARCHAR">
  `${columnEntity.name}` VARCHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "PASSWORD">
  `${columnEntity.name}` VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "ATTACHMENT">
  `${columnEntity.name}` VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "TEXT">
  `${columnEntity.name}` TEXT<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "LONGTEXT">
  `${columnEntity.name}` LONGTEXT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "BLOB">
  `${columnEntity.name}` BLOB<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#elseif columnEntity.dataType == "LONGBLOB">
  `${columnEntity.name}` LONGBLOB<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
<#else>
  `${columnEntity.name}` VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if> COMMENT '${columnEntity.caption}'
</#if>