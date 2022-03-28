<#if columnEntity.autoIncrement == true>
CREATE OR REPLACE TRIGGER "TBL_${tableEntity.id}_TG_INSERT" BEFORE INSERT ON "${tableName}" FOR EACH ROW
BEGIN
  SELECT "TBL_${tableEntity.id}_SEQ_ID".NEXTVAL INTO:NEW."${columnEntity.name}" FROM DUAL;
END;
</#if>
