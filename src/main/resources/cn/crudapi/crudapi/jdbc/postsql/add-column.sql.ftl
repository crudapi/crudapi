ALTER TABLE "${tableName}" ADD 
<#if columnEntity.dataType == "BOOL">
  "${columnEntity.name}" BOOL<#if columnEntity.defaultValue??> DEFAULT <#if columnEntity.defaultValue == "true">1<#else>0</#if></#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "INT">
  "${columnEntity.name}"<#if columnEntity.autoIncrement == true> SERIAL<#else> INT</#if><#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "BIGINT">
  "${columnEntity.name}"<#if columnEntity.autoIncrement == true> BIGSERIAL<#else> BIGINT</#if><#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "FLOAT">
  "${columnEntity.name}" REAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DOUBLE">
  "${columnEntity.name}" DOUBLE PRECISION<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DECIMAL">
  "${columnEntity.name}" DECIMAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DATE">
  "${columnEntity.name}" DATE<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "TIME">
  "${columnEntity.name}" TIME<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "DATETIME">
  "${columnEntity.name}" TIMESTAMP<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "TIMESTAMP">
  "${columnEntity.name}" TIMESTAMP<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "CHAR">
  "${columnEntity.name}" CHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "VARCHAR">
  "${columnEntity.name}" VARCHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "PASSWORD">
  "${columnEntity.name}" VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "ATTACHMENT">
  "${columnEntity.name}" VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "TEXT">
  "${columnEntity.name}" TEXT<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "LONGTEXT">
  "${columnEntity.name}" TEXT<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "BLOB">
  "${columnEntity.name}" BYTEA<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#elseif columnEntity.dataType == "LONGBLOB">
  "${columnEntity.name}" BYTEA<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
<#else>
  "${columnEntity.name}" VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != true> NOT NULL</#if>
</#if>
;

COMMENT ON COLUMN "${tableName}"."${columnEntity.name}" IS '${columnEntity.caption}';

<#if columnEntity.indexType?? && columnEntity.indexType == "PRIMARY">
  ALTER TABLE "${tableName}" ADD CONSTRAINT "${columnEntity.indexName}" PRIMARY KEY ("${columnEntity.name}")
</#if>

<#if columnEntity.indexType?? && columnEntity.indexType == "UNIQUE">
  ALTER TABLE "${tableName}" ADD CONSTRAINT "${columnEntity.indexName}" UNIQUE("${columnEntity.name}")
</#if>

<#if columnEntity.indexType?? && (columnEntity.indexType == "INDEX" || columnEntity.indexType == "FULLTEXT")>
  CREATE INDEX "${columnEntity.indexName}" ON "${tableName}" ("${columnEntity.name}")
</#if>
