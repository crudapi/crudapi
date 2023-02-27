ALTER TABLE "${tableName}" DROP COLUMN "${columnName}";
<#if columnEntity.autoIncrement == true>
    DROP TRIGGER "TBL_${tableEntity.id}_TG_INSERT";
    DROP SEQUENCE "TBL_${tableEntity.id}_SEQ_ID"
</#if>