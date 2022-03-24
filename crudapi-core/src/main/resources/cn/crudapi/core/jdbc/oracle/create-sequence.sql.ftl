<#list columnEntityList as columnEntity>
  <#if columnEntity.autoIncrement == true>
    create sequence "SEQ_${tableName}_${columnEntity.name}" minvalue 1 nomaxvalue increment by 1 start with 1 nocache
  </#if>
</#list>      

  