<#list columnEntityList as columnEntity>
  <#if columnEntity.autoIncrement == true>
    create sequence "TBL_${id}_SEQ_ID" minvalue 1 nomaxvalue increment by 1 start with 1 nocache
  </#if>
</#list>      

  