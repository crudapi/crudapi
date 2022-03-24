<#if oldColumnName != columnEntity.name>
  ALTER TABLE "${tableName}" RENAME "${oldColumnName}" TO "${columnEntity.name}";
</#if>

ALTER TABLE "${tableName}" ALTER COLUMN 
<#if columnEntity.dataType == "BOOL">
  "${columnEntity.name}" TYPE BOOL
<#elseif columnEntity.dataType == "INT">
  "${columnEntity.name}" TYPE<#if columnEntity.autoIncrement == true> SERIAL<#else> INT</#if>
<#elseif columnEntity.dataType == "BIGINT">
  "${columnEntity.name}" TYPE<#if columnEntity.autoIncrement == true> BIGSERIAL<#else> BIGINT</#if>
<#elseif columnEntity.dataType == "FLOAT">
  "${columnEntity.name}" TYPE REAL
<#elseif columnEntity.dataType == "DOUBLE">
  "${columnEntity.name}" TYPE DOUBLE PRECISION
<#elseif columnEntity.dataType == "DECIMAL">
  "${columnEntity.name}" TYPE DECIMAL
<#elseif columnEntity.dataType == "DATE">
  "${columnEntity.name}" TYPE DATE
<#elseif columnEntity.dataType == "TIME">
  "${columnEntity.name}" TYPE TIME
<#elseif columnEntity.dataType == "DATETIME">
  "${columnEntity.name}" TYPE TIMESTAMP
<#elseif columnEntity.dataType == "TIMESTAMP">
  "${columnEntity.name}" TYPE TIMESTAMP
<#elseif columnEntity.dataType == "CHAR">
  "${columnEntity.name}" TYPE CHAR(${columnEntity.length})
<#elseif columnEntity.dataType == "VARCHAR">
  "${columnEntity.name}" TYPE VARCHAR(${columnEntity.length})
<#elseif columnEntity.dataType == "PASSWORD">
  "${columnEntity.name}" TYPE VARCHAR(200)
<#elseif columnEntity.dataType == "ATTACHMENT">
  "${columnEntity.name}" TYPE VARCHAR(4000)
<#elseif columnEntity.dataType == "TEXT">
  "${columnEntity.name}" TYPE TEXT
<#elseif columnEntity.dataType == "LONGTEXT">
  "${columnEntity.name}" TYPE TEXT
<#elseif columnEntity.dataType == "BLOB">
  "${columnEntity.name}" TYPE BYTEA
<#elseif columnEntity.dataType == "LONGBLOB">
  "${columnEntity.name}" TYPE BYTEA
<#else>
  "${columnEntity.name}" TYPE VARCHAR(200)
</#if>
;

<#if columnEntity.defaultValue??>
  ALTER TABLE "${tableName}" ALTER COLUMN
  <#if columnEntity.dataType == "BOOL">
    "${columnEntity.name}" SET DEFAULT <#if columnEntity.defaultValue == "true">1<#else>0</#if>
  <#elseif columnEntity.dataType == "INT">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "BIGINT">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "FLOAT">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "DOUBLE">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "DECIMAL">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "DATE">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "TIME">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "DATETIME">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "TIMESTAMP">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "CHAR">
    "${columnEntity.name}" SET DEFAULT '${columnEntity.defaultValue}'
  <#elseif columnEntity.dataType == "VARCHAR">
    "${columnEntity.name}" SET DEFAULT '${columnEntity.defaultValue}'
  <#elseif columnEntity.dataType == "PASSWORD">
    "${columnEntity.name}" SET DEFAULT '${columnEntity.defaultValue}'
  <#elseif columnEntity.dataType == "ATTACHMENT">
    "${columnEntity.name}" SET DEFAULT '${columnEntity.defaultValue}'
  <#elseif columnEntity.dataType == "TEXT">
    "${columnEntity.name}" SET DEFAULT '${columnEntity.defaultValue}'
  <#elseif columnEntity.dataType == "LONGTEXT">
    "${columnEntity.name}" SET DEFAULT '${columnEntity.defaultValue}''
  <#elseif columnEntity.dataType == "BLOB">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#elseif columnEntity.dataType == "LONGBLOB">
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  <#else>
    "${columnEntity.name}" SET DEFAULT ${columnEntity.defaultValue}
  </#if>
  ;
</#if>

<#if columnEntity.nullable != oldColumnNullable>
  <#if columnEntity.nullable != true>
    ALTER TABLE "${tableName}" ALTER COLUMN "${columnEntity.name}" SET NOT NULL;
  <#else>
    ALTER TABLE "${tableName}" ALTER COLUMN "${columnEntity.name}" DROP NOT NULL;
  </#if>
</#if>

COMMENT ON COLUMN "${tableName}"."${columnEntity.name}" IS '${columnEntity.caption}'

