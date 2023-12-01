DROP TABLE "${tableName}"

<#list columnEntityList as columnEntity>
  <#if columnEntity.autoIncrement == true>
    ;DROP SEQUENCE "SEQ_${tableName}_${columnEntity.name}"
  </#if>
</#list>
