EXEC sp_rename '${tableName}.${oldColumnName}', '${columnEntity.name}' , 'COLUMN';

ALTER TABLE "${tableName}" ALTER COLUMN 
<#if columnEntity.dataType == "BOOL">
  "${columnEntity.name}" BIT<#if columnEntity.defaultValue??> DEFAULT <#if columnEntity.defaultValue == "true">1<#else>0</#if></#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "INT">
  "${columnEntity.name}" INT<#if columnEntity.autoIncrement == true> IDENTITY(1, 1)</#if><#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "BIGINT">
  "${columnEntity.name}" BIGINT<#if columnEntity.autoIncrement == true> IDENTITY(1, 1)</#if><#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "FLOAT">
  "${columnEntity.name}" REAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DOUBLE">
  "${columnEntity.name}" DOUBLE PRECISION<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DECIMAL">
  "${columnEntity.name}" DECIMAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DATE">
  "${columnEntity.name}" DATE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "TIME">
  "${columnEntity.name}" TIME<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DATETIME">
  "${columnEntity.name}" DATETIME<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "TIMESTAMP">
  "${columnEntity.name}" TIMESTAMP<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "CHAR">
  "${columnEntity.name}" CHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "VARCHAR">
  "${columnEntity.name}" VARCHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "PASSWORD">
  "${columnEntity.name}" VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>    
<#elseif columnEntity.dataType == "ATTACHMENT">
  "${columnEntity.name}" VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "TEXT">
  "${columnEntity.name}" VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "LONGTEXT">
  "${columnEntity.name}" TEXT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "BLOB">
  "${columnEntity.name}" BINARY<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "LONGBLOB">
  "${columnEntity.name}" BINARY<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#else>
  "${columnEntity.name}" VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
</#if>
;

EXEC sp_updateextendedproperty 'MS_Description', N'${columnEntity.caption}', 'SCHEMA', N'dbo','TABLE', N'${tableName}', 'COLUMN', N'${columnEntity.name}';
