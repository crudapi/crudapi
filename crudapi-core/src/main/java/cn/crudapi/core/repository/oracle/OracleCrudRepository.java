package cn.crudapi.core.repository.oracle;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.repository.CrudAbstractRepository;
import cn.crudapi.core.util.ToolUtils;

@Component
public class OracleCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(OracleCrudRepository.class);

	
	@Override
	public String getSqlQuotation() {
		return "\"";
	}
	
	@Override
	public String getLimitOffsetSql() {
		return ""; //LIMIT :limit OFFSET :offset
	}
	
	@Override
	public String toCreateTableSql(TableEntity tableEntity) {
        List<ColumnEntity> columnEntityList = tableEntity.getColumnEntityList();
        String caption = tableEntity.getCaption();
        String name = tableEntity.getName();
        String tableName = tableEntity.getTableName();
        EngineEnum engine = tableEntity.getEngine();
        
		StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE {0} (");
        sb.append(ToolUtils.getLineSeparator());

        List<String> sqlList = new ArrayList<String>();
        for (ColumnEntity columnEntity : columnEntityList) {
            sqlList.add(toColumnSql(columnEntity));
        }

        List<String> indexSqlList = new ArrayList<String>();
        for (ColumnEntity columnEntity : columnEntityList) {
        	String indexSql = toColumnIndexSql(tableName, columnEntity);
            if (StringUtils.isNotBlank(indexSql)) {
            	 indexSqlList.add(indexSql);
            }
        }

        String delimiter = "," + ToolUtils.getLineSeparator();

        sb.append(String.join(delimiter, sqlList));
        sb.append(ToolUtils.getLineSeparator());
        sb.append(");");
        
        
        //index
        if (indexSqlList.size() > 0) {
           sb.append(ToolUtils.getLineSeparator());
           delimiter = ";" + ToolUtils.getLineSeparator();
    	   sb.append(String.join(delimiter, indexSqlList));
           sb.append(";");
           sb.append(ToolUtils.getLineSeparator());
        }
       
//        sb.append(ToolUtils.getLineSeparator());
//        sb.append(" COMMENT ON TABLE ");
//        sb.append(toSqlName(tableName));
//        sb.append(" IS ''");
//		sb.append(StringUtils.isEmpty(caption) ? name : caption );
//		sb.append("''");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), engine.getCode() };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
	}
	
	@Override
	public String toSql(DataTypeEnum dataType, Integer length, Integer precision, Integer scale) {
        StringBuilder sb = new StringBuilder();

        switch (dataType) {
	        case BIT:    
	        case TINYINT:
	        case SMALLINT:
	        case MEDIUMINT:
	        case INT:
	        case BIGINT:
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
	    
	
	@Override
	public String toColumnSql(ColumnEntity columnEntity) {
		DataTypeEnum dataType = columnEntity.getDataType();
		String name = columnEntity.getName();
		String caption = columnEntity.getCaption();
		Integer length = columnEntity.getLength();
		Integer precision = columnEntity.getPrecision();
		Integer scale = columnEntity.getScale();
		Boolean autoIncrement = columnEntity.getAutoIncrement();
		Boolean nullable = columnEntity.getNullable();
		String defaultValue = columnEntity.getDefaultValue();
		
		String pattern = "{0} {1}";
		String dbDataType = dataType.toString();
		
		if (dbDataType.equals("BOOL")) {
			dbDataType = "BOOL";
		} else if (dbDataType.equals("ATTACHMENT")) {
			dbDataType = "VARCHAR";
		} else if (dbDataType.equals("PASSWORD")) {
			dbDataType = "VARCHAR";
		} else if (dbDataType.equals("DATETIME")) {
			dbDataType = "DATETIME";
		} else if (dbDataType.equals("DOUBLE")) {
			dbDataType = "DOUBLE PRECISION";
		} else if (dbDataType.equals("FLOAT")) {
			dbDataType = "real";
		} else if (dbDataType.equals("TINYINT")) {
			dbDataType = "smallint";
		}
		
		Object[] arguments = { toSqlName(name), dbDataType };

		// name, dataType
		StringBuilder sb = new StringBuilder(MessageFormat.format(pattern, arguments));
		
		// length, precision, scale
		sb.append(toSql(dataType, length, precision, scale));

		// autoIncrement
		if (Objects.equals(autoIncrement, true)) {
			sb.append(" IDENTITY(1, 1) NOT NULL");
		} else {
			if (Objects.equals(nullable, true)) {
				sb.append(" NULL");
			} else {
				sb.append(" NOT NULL");
			}

			if (defaultValue != null) {
				sb.append(" DEFAULT ");
				sb.append(toDefaultValueSql(dataType, defaultValue));
			}
		}
		
		
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	
	@Override
	public String toFullIndexSql(String tableName, IndexTypeEnum indexType, IndexStorageEnum indexStorage, String indexName,  String indexCaption, List<String> columnNameList) {
        if (indexType == null || indexType.equals(IndexTypeEnum.NONE)) {
            return null;
        }
        
        if (IndexTypeEnum.INDEX == indexType
        	|| IndexTypeEnum.UNIQUE == indexType
            || IndexTypeEnum.FULLTEXT == indexType) {
            return toNormalIndexSql(tableName, indexType, indexStorage, indexName, indexCaption, columnNameList);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE {0} ADD CONSTRAINT ");

        List<String> newColumnNameList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> newColumnNameList.add(toSqlName(t)));

        String columnNames = "(" + String.join(",", newColumnNameList) + ")";
        String newIndeName = StringUtils.isBlank(indexName) ? "PRIMARY" : indexName;
        String fullNewIndeName = tableName + "_" + newIndeName;
        
        sb.append(toSqlName(fullNewIndeName));
        sb.append(" ");
        switch (indexType) {
	        case PRIMARY:
	            sb.append("PRIMARY KEY ");
	            sb.append(columnNames);
	            break;
	        default:
	            break;
        }
        
        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName) };

        String sql = MessageFormat.format(pattern, arguments);
        return sql;
    }
	
	//CREATE INDEX index_name ON table_name (column1_name, column2_name);
	private String toNormalIndexSql(String tableName, IndexTypeEnum indexType, IndexStorageEnum indexStorage, String indexName,  String indexCaption, List<String> columnNameList) {
        if (indexType == null || indexType.equals(IndexTypeEnum.NONE)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE ");
        
        switch (indexType) {
        	case UNIQUE:
	            sb.append("UNIQUE INDEX ");
	            break;
        	case INDEX:
	        case FULLTEXT:
	            sb.append("INDEX ");
	            break;
	        default:
	            break;
	    }

        List<String> newColumnNameList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> newColumnNameList.add(toSqlName(t)));

        String columnNames = "(" + String.join(",", newColumnNameList) + ")";
        String fullNewIndeName = tableName + "_" + indexName;
        
        sb.append(toSqlName(fullNewIndeName));
        sb.append(" ON ");
        sb.append(toSqlName(tableName));
        sb.append(" ");
        sb.append(columnNames);
       
        return sb.toString();
    }
	   
	@Override
	public boolean isExistTable(String tableName) {
		//select   * from sysObjects where Id=OBJECT_ID(N'ca_file') and xtype='U'
		LeafCondition condition1 = new LeafCondition();
		condition1.setColumnName("name");
		condition1.setValue(tableName);
		condition1.setOperatorType(OperatorTypeEnum.EQ);
		
		LeafCondition condition2 = new LeafCondition();
		condition2.setColumnName("type");
		condition2.setValue("U");
		condition2.setOperatorType(OperatorTypeEnum.EQ);
		
		CompositeCondition condition = new CompositeCondition();
		condition.add(condition1);
		condition.add(condition2);
		
		Long count = this.count("sysObjects", condition);
		
        return count > 0;
	}
	
	@Override
	public String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("DROP INDEX IF EXISTS {0}");
        
        String indexSql = toIndexSql(tableName, indexEntity);
        if (!StringUtils.isBlank(indexSql)) {
        	sb.append(";");
            sb.append(indexSql);
        }

        String pattern = sb.toString();
        String fullOldIndexName = tableName + "_" + oldIndexName;
        Object[] arguments = { toSqlName(fullOldIndexName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	@Override
    public String toAddIndexSql(String tableName, IndexEntity indexEntity) {
        //ALTER TABLE ca_newtable22 ADD CONSTRAINT qqq UNIQUE (col5, col6);
        String indexSql = toIndexSql(tableName, indexEntity);
        if (StringUtils.isBlank(indexSql)) {
            return null;
        }
        return indexSql;
    }
	
	@Override
	public String toDeleteIndexSql(String tableName, String oldIndexName) {
        StringBuilder sb = new StringBuilder();

        sb.append("DROP INDEX IF EXISTS {0}");

        String pattern = sb.toString();
        String fullOldIndexName = tableName + "_" + oldIndexName;
        Object[] arguments = { toSqlName(fullOldIndexName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	@Override
	public String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        String indexSql = toColumnIndexSql(tableName, columnEntity);
        if (StringUtils.isAllEmpty(oldIndexName)) {
        	 if (!StringUtils.isBlank(indexSql)) {
            	 sb.append(indexSql);
             }
        } else {
        	 sb.append("DROP INDEX IF EXISTS {0}");
        	 if (!StringUtils.isBlank(indexSql)) {
        		 sb.append(";");
                 sb.append(indexSql);
             }
        }
        
        String sql = "";
    	String pattern = sb.toString();
        if (StringUtils.isAllEmpty(oldIndexName)) {
            Object[] arguments = { toSqlName(tableName)};
            sql = MessageFormat.format(pattern, arguments);
        } else {
            Object[] arguments = { toSqlName(oldIndexName) };
            sql = MessageFormat.format(pattern, arguments);
        }
        
        return sql;
    }

	@Override
	public String toAddColumnSql(String tableName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ADD ");
        sb.append(toColumnSql(columnEntity));

        String indexSql = toColumnIndexSql(tableName, columnEntity);
        if (!StringUtils.isBlank(indexSql)) {
        	sb.append(";");
            sb.append(indexSql);
        }

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	@Override
	public Long create(String tableName, Map<String, Object> map) {
		log.info("OracleCrudRepository->create {}", tableName);
		
		KeyHolder keyHolder = insert(tableName, map,  new String[] { getSqlQuotation() + COLUMN_ID + getSqlQuotation() });
		
		return Long.parseLong(keyHolder.getKeyList().get(0).get(COLUMN_ID).toString());
	}
}
