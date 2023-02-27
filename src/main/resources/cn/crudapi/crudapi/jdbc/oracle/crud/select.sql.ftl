SELECT ${columnNames}
FROM (
    SELECT ROWNUM AS ROWNO, t.*
    FROM (${sql}) t
) t1
WHERE t1.ROWNO > (:offset)
<#if limit??>
AND t1.ROWNO <= (:offset + :limit)
</#if>
