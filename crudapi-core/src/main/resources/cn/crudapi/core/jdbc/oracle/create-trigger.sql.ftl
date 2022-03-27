<#list columnEntityList as columnEntity>
  <#if columnEntity.autoIncrement == true>
    CREATE OR REPLACE TRIGGER "TBL_${id}_TG_INSERT" BEFORE INSERT ON "${tableName}" FOR EACH ROW
    BEGIN
      SELECT "TBL_${id}_SEQ_ID".NEXTVAL INTO:NEW."${columnEntity.name}" FROM DUAL;
    END;
  </#if>
</#list>