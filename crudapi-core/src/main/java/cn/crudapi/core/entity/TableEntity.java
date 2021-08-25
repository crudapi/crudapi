package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.util.DbUtils;
import cn.crudapi.core.util.ToolUtils;



public class TableEntity implements Sqlable, BaseEntity {
	private static final String TABLE_NAME = "ca_meta_table";

	private Long id;

	private String name;

	private String caption;

	private String description;

	private Timestamp createdDate;

	private Timestamp lastModifiedDate;

	private String pluralName;

	private String tableName;

    private EngineEnum engine;

    private Boolean createPhysicalTable;
    
    private Boolean reverse;
    
    private Boolean systemable;

    private List<ColumnEntity> columnEntityList;

    private List<IndexEntity> indexEntityList;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public EngineEnum getEngine() {
		return engine;
	}

	public void setEngine(EngineEnum engine) {
		this.engine = engine;
	}

	public Boolean getCreatePhysicalTable() {
		return createPhysicalTable;
	}

	public void setCreatePhysicalTable(Boolean createPhysicalTable) {
		this.createPhysicalTable = createPhysicalTable;
	}

	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public Boolean getSystemable() {
		return systemable;
	}

	public void setSystemable(Boolean systemable) {
		this.systemable = systemable;
	}

	public List<ColumnEntity> getColumnEntityList() {
		return columnEntityList;
	}

	public void setColumnEntityList(List<ColumnEntity> columnEntityList) {
		this.columnEntityList = columnEntityList;
	}

	public List<IndexEntity> getIndexEntityList() {
		return indexEntityList;
	}

	public void setIndexEntityList(List<IndexEntity> indexEntityList) {
		this.indexEntityList = indexEntityList;
	}
	

	@Override
	public String toString() {
		return "TableEntity [id=" + id + ", name=" + name + ", caption=" + caption + ", description=" + description
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", pluralName="
				+ pluralName + ", tableName=" + tableName + ", engine=" + engine + ", createPhysicalTable="
				+ createPhysicalTable + ", reverse=" + reverse + ", systemable=" + systemable + ", columnEntityList="
				+ columnEntityList + ", indexEntityList=" + indexEntityList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableEntity other = (TableEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE {0} (");
        sb.append(ToolUtils.getLineSeparator());

        List<String> sqlList = new ArrayList<String>();
        for (ColumnEntity columnEntity : columnEntityList) {
            sqlList.add(columnEntity.toSql());
        }

        for (ColumnEntity columnEntity : columnEntityList) {
        	String indexSql = columnEntity.toIndexSql();
            if (StringUtils.isNotBlank(indexSql)) {
                sqlList.add(indexSql);
            }
        }

        String delimiter = "," + ToolUtils.getLineSeparator();

        sb.append(String.join(delimiter, sqlList));
        sb.append(ToolUtils.getLineSeparator());
        sb.append(") ENGINE={1} DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        
        sb.append(" COMMENT ''");
		sb.append(StringUtils.isEmpty(caption) ? name : caption );
		sb.append("''");

        String pattern = sb.toString();
        Object[] arguments = { DbUtils.toNameSql(tableName), engine.getCode() };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public List<String> toIndexSqlList() {
		List<String> sqlList = new ArrayList<String>();
		
		if (indexEntityList != null) {
			for (IndexEntity indexEntity : indexEntityList) {
				String sql = DbUtils.toAddIndexSql(tableName, indexEntity);
				
				if (!StringUtils.isBlank(sql)) {
					sqlList.add(sql);
				}
			}	
		}
		
		
		return sqlList;
	}
	
	@Override
	public String getDataBaseTableName() {
		return TABLE_NAME;
	}

	@Override
	public Long getRecId() {
		return id;
	}

	@Override
	public List<String> getColumnNames(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("name");
		columnNames.add("caption");
		columnNames.add("description");
		if (isUpdate == false) {
			columnNames.add("createdDate");
		}
		columnNames.add("lastModifiedDate");
		columnNames.add("pluralName");
		columnNames.add("tableName");
		columnNames.add("engine");
		columnNames.add("createPhysicalTable");
		return columnNames;
	}

	@Override
	public List<Object> getColumnValues(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		columnValues.add(name);
		columnValues.add(caption);
		columnValues.add(description);
		if (isUpdate == false) {
			columnValues.add(createdDate);
		}
		columnValues.add(lastModifiedDate);
		columnValues.add(pluralName);
		columnValues.add(tableName);
		columnValues.add(engine.toString());
		columnValues.add(createPhysicalTable);
		return columnValues;
	}

	@Override
	public List<String> getColumnNamesIgnoreNull(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		if (name != null) { columnNames.add("name"); }
		if (caption != null) { columnNames.add("caption"); }
		if (description != null) { columnNames.add("description"); }
		if (createdDate != null && isUpdate == false) { columnNames.add("createdDate"); }
		if (lastModifiedDate != null) { columnNames.add("lastModifiedDate"); }
		if (pluralName != null) { columnNames.add("pluralName"); }
		if (tableName != null) { columnNames.add("tableName"); }
		if (engine != null) { columnNames.add("engine"); }
		if (createPhysicalTable != null) { columnNames.add("createPhysicalTable"); }
		return columnNames;
	}

	@Override
	public List<Object> getColumnValuesIgnoreNull(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		if (name != null) { columnValues.add(name); }
		if (caption != null) { columnValues.add(caption); }
		if (description != null) { columnValues.add(description); }
		if (createdDate != null && isUpdate == false) { columnValues.add(createdDate); }
		if (lastModifiedDate != null) { columnValues.add(lastModifiedDate); }
		if (pluralName != null) { columnValues.add(pluralName); }
		if (tableName != null) { columnValues.add(tableName); }
		if (engine != null) { columnValues.add(engine.toString()); }
		if (createPhysicalTable != null) { columnValues.add(createPhysicalTable); }
		return columnValues;
	}
}
