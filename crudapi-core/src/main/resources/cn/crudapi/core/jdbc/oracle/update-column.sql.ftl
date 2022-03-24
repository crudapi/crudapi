<#if oldColumnName != columnEntity.name>
  ALTER TABLE "${tableName}" RENAME COLUMN "${oldColumnName}" TO "${columnEntity.name}";
</#if>

ALTER TABLE "${tableName}" MODIFY (
<#if columnEntity.dataType == "BOOL">
  "${columnEntity.name}" NUMBER(1)<#if columnEntity.defaultValue??> DEFAULT <#if columnEntity.defaultValue == "true">1<#else>0</#if></#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "INT">
  "${columnEntity.name}" INT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "BIGINT">
  "${columnEntity.name}" INT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "FLOAT">
  "${columnEntity.name}" FLOAT<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "DOUBLE">
  "${columnEntity.name}" REAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "DECIMAL">
  "${columnEntity.name}" DECIMAL<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "DATE">
  "${columnEntity.name}" DATE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "TIME">
  "${columnEntity.name}" CHAR(8)<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "DATETIME">
  "${columnEntity.name}" DATE<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "TIMESTAMP">
  "${columnEntity.name}" TIMESTAMP<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "CHAR">
  "${columnEntity.name}" CHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "VARCHAR">
  "${columnEntity.name}" VARCHAR(${columnEntity.length})<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "PASSWORD">
  "${columnEntity.name}" VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "ATTACHMENT">
  "${columnEntity.name}" VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "TEXT">
  "${columnEntity.name}" VARCHAR(4000)<#if columnEntity.defaultValue??> DEFAULT '${columnEntity.defaultValue}'</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "LONGTEXT">
  "${columnEntity.name}" LONG<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "BLOB">
  "${columnEntity.name}" BLOB<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#elseif columnEntity.dataType == "LONGBLOB">
  "${columnEntity.name}" BLOB<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
<#else>
  "${columnEntity.name}" VARCHAR(200)<#if columnEntity.defaultValue??> DEFAULT ${columnEntity.defaultValue}</#if><#if columnEntity.nullable != oldColumnNullable><#if columnEntity.nullable != true> NOT</#if> NULL</#if>
</#if>
);

COMMENT ON COLUMN "${tableName}"."${columnEntity.name}" IS '${columnEntity.caption}'
