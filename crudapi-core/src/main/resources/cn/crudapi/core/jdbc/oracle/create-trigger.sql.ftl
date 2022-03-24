<#list columnEntityList as columnEntity>
  <#if columnEntity.autoIncrement == true>
    CREATE OR REPLACE TRIGGER "${tableName}_tg_insert" BEFORE INSERT ON "${tableName}" FOR EACH ROW
    BEGIN
      SELECT "SEQ_${tableName}_${columnEntity.name}".NEXTVAL INTO:NEW."${columnEntity.name}" FROM DUAL;
    END;
  </#if>
</#list>