package cn.crudapi.core.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.IndexLineEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;

public final class DbUtils {
    public static Boolean isNumber(DataTypeEnum dataType) {
        Boolean ret = false;

        switch (dataType) {
        case BIT:
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case BIGINT:
        case FLOAT:
        case DOUBLE:
        case DECIMAL:
        case BOOL:
            ret = true;
            break;
        default:
            break;
        }

        return ret;
    }

    public static String toNameSql(String name) {
        StringBuilder sb = new StringBuilder();

        if (name.equals("*")) {
          return name;
        } else {
    	  sb.append("`");
          sb.append(name.toString());
          sb.append("`");
        }
       
        return sb.toString();
    }

    public static String toDefaultValueSql(DataTypeEnum dataType, String defaultValue) {
        StringBuilder sb = new StringBuilder();

        switch (dataType) {
        case BIT:    
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case BIGINT:
        case FLOAT:
        case DOUBLE:
        case DECIMAL:
        case BOOL:
            sb.append(defaultValue.toString());
            break;
        case DATE:
        case TIME:
        case YEAR:
        case DATETIME:
        case TIMESTAMP:
            sb.append("''");
            sb.append(defaultValue.toString());
            sb.append("''");
            break;
        case CHAR:
        case VARCHAR:
        case TINYTEXT:
        case TEXT:
        case MEDIUMTEXT:
        case LONGTEXT:
        case PASSWORD:
        case ATTACHMENT:
            sb.append("''");
            sb.append(defaultValue.toString());
            sb.append("''");
            break;
        default:
            break;
        }

        return sb.toString();
    }

    public static String toSql(DataTypeEnum dataType, Integer length, Integer precision, Integer scale) {
        StringBuilder sb = new StringBuilder();

        switch (dataType) {
        case BIT:    
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case BIGINT:
            if (length != null) {
                sb.append("(");
                sb.append(length.toString());
                sb.append(")");
            }
            break;
        case FLOAT:
        case DOUBLE:
        case DECIMAL:
            if (precision != null && scale != null) {
                sb.append("(");
                sb.append(precision.toString());
                sb.append(",");
                sb.append(scale.toString());
                sb.append(")");
            }
            break;
        case DATE:
        case TIME:
        case YEAR:
        case DATETIME:
        case TIMESTAMP:
            break;
        case CHAR:
        case VARCHAR:
        case TINYTEXT:
        case TEXT:
        case MEDIUMTEXT:
        case LONGTEXT:
        case PASSWORD:
        case ATTACHMENT:
            if (length != null) {
                sb.append("(");
                sb.append(length.toString());
                sb.append(")");
            }
            break;
        default:
            break;
        }

        return sb.toString();
    }

    public static String toIndexSql(IndexTypeEnum indexType, IndexStorageEnum indexStorage, String columnName, String indexName, String indexCaption) {
        List<String> columnNameList = new ArrayList<String>();
        columnNameList.add(columnName);

        return toIndexSql(indexType, indexStorage, indexName, indexCaption, columnNameList);
    }

    public static String toIndexSql(IndexTypeEnum indexType, IndexStorageEnum indexStorage, List<IndexLineEntity> indexLineEntityList, String indexName, String indexCaption) {
        List<String> columnNameList = new ArrayList<String>();
        indexLineEntityList.stream().forEach(t -> columnNameList.add(t.getColumnEntity().getName()));

        return toIndexSql(indexType, indexStorage, indexName, indexCaption, columnNameList);
    }

    public static String toRenameTableSql(String oldTableName, String newTableName) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} RENAME {1}");

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(oldTableName), toNameSql(newTableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toSetTableEngineSql(String tableName, EngineEnum engine) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ENGINE={1}");

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), engine.getCode() };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toDeleteColumnSql(String tableName, String columnName) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} DROP {1}");

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), toNameSql(columnName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toAddColumnSql(String tableName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ADD ");
        sb.append(columnEntity.toSql());

        String indexSql = columnEntity.toIndexSql();
        if (!StringUtils.isBlank(indexSql)) {
            sb.append(", ADD ");
            sb.append(indexSql);
        }

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toUpdateColumnSql(String tableName, String oldColumnName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} CHANGE {1} ");
        sb.append(columnEntity.toSql());

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), toNameSql(oldColumnName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        String indexSql = columnEntity.toIndexSql();
        if (StringUtils.isAllEmpty(oldIndexName)) {
        	 if (!StringUtils.isBlank(indexSql)) {
        		 sb.append("ALTER TABLE {0} ADD ");
            	 sb.append(indexSql);
             }
        } else {
        	 sb.append("ALTER TABLE {0} DROP INDEX {1}");
        	 if (!StringUtils.isBlank(indexSql)) {
                 sb.append(", ADD ");
                 sb.append(indexSql);
             }
        }
        
        String sql = "";
    	String pattern = sb.toString();
        if (StringUtils.isAllEmpty(oldIndexName)) {
            Object[] arguments = { toNameSql(tableName)};
            sql = MessageFormat.format(pattern, arguments);
        } else {
            Object[] arguments = { toNameSql(tableName), toNameSql(oldIndexName) };
            sql = MessageFormat.format(pattern, arguments);
        }
        
        return sql;
    }

    public static String toDeleteIndexSql(String tableName, String oldIndexName) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} DROP INDEX {1}");

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), toNameSql(oldIndexName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toAddIndexSql(String tableName, IndexEntity indexEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ADD ");
       
        String indexSql = indexEntity.toSql();

        if (StringUtils.isBlank(indexSql)) {
            return null;
        }
        sb.append(indexSql);

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} DROP INDEX {1}");

        String indexSql = indexEntity.toSql();
        if (!StringUtils.isBlank(indexSql)) {
            sb.append(", ADD ");
            sb.append(indexSql);
        }

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), toNameSql(oldIndexName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    private static String toIndexSql(IndexTypeEnum indexType, IndexStorageEnum indexStorage, String indexName,  String indexCaption, List<String> columnNameList) {
        if (indexType == null || indexType.equals(IndexTypeEnum.NONE)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        List<String> newColumnNameList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> newColumnNameList.add(toNameSql(t)));

        String columnNames = "(" + String.join(",", newColumnNameList) + ")";
        String newIndeName = StringUtils.isBlank(indexName) ? "" : toNameSql(indexName) + " ";

        switch (indexType) {
        case PRIMARY:
            sb.append("PRIMARY KEY ");
            sb.append(columnNames);
            break;
        case UNIQUE:
            sb.append("UNIQUE ");
            sb.append(newIndeName);
            sb.append(columnNames);
            break;
        case INDEX:
            sb.append("INDEX ");
            sb.append(newIndeName);
            sb.append(columnNames);
            break;
        case FULLTEXT:
            sb.append("FULLTEXT ");
            sb.append(newIndeName);
            sb.append(columnNames);
            break;
        default:
            break;
        }

        if (indexStorage != null) {
            sb.append(" USING ");
            sb.append(indexStorage.toString());
        }
        
        
        if (!StringUtils.isEmpty(indexCaption)) {
        	sb.append(" COMMENT ''");
     		sb.append(indexCaption);
     		sb.append("''");
        }
       
        return sb.toString();
    }
    
    public static String toInserSql(String tableName, List<String> columnNameList, Boolean isReplace) {
        StringBuilder sb = new StringBuilder();
        if (isReplace) {
        	 sb.append("INSERT INTO {0}({1}) VALUES({2})");
        } else {
        	 sb.append("INSERT INTO {0}({1}) VALUES({2})");
        }
       
        List<String> nameList = new ArrayList<String>();
        List<String> valueList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> {
            nameList.add(toNameSql(t));
            valueList.add("?");
        });
        String columnNames = String.join(",", nameList);
        String columnValues = String.join(",", valueList);

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), columnNames, columnValues };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    // UPDATE `product` SET
    // `id`=?,`name_2`=?,`price`=?,`ats`=?,`col_6_6`=?,`model`=? WHERE 1
    public static String toUpdateSql(String tableName, List<String> columnNameList) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE {0} SET {1}");

        List<String> nameAndValueList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> {
            nameAndValueList.add(toNameSql(t) + "= ?");
        });
        String nameAndValues = String.join(",", nameAndValueList);

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName), nameAndValues};

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    // DELETE FROM `product` WHERE 0
    public static String toDeleteSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM {0}");

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName)};

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    // SELECT `id`, `name_2`, `price`, `ats`, `col_6_6`, `model` FROM `product`
    // WHERE 1
    public static String toSelectSql(String tableName, List<String> selectColumnNameList) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT {0} FROM {1}");

        List<String> nameList = new ArrayList<String>();
        selectColumnNameList.stream().forEach(t -> {
            nameList.add(toNameSql(t));
        });
        String selectColumnNames = String.join(",", nameList);

        String pattern = sb.toString();
        Object[] arguments = { selectColumnNames, toNameSql(tableName)};

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public static String toSelectSql(String tableName, String selectColumnName) {
        List<String> selectColumnNameList = new ArrayList<String>();
        selectColumnNameList.add(selectColumnName);
        return toSelectSql(tableName, selectColumnNameList);
    }


    public static String toCountSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM {0}");

        String pattern = sb.toString();
        Object[] arguments = { toNameSql(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
}
