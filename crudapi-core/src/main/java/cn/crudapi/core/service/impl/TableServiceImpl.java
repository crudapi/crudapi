package cn.crudapi.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.dto.TableRelationDTO;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.enumeration.TableRelationTypeEnum;
import cn.crudapi.core.event.BusinessEvent;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.model.QueryData;
import cn.crudapi.core.model.QueryModel;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.service.FileService;
import cn.crudapi.core.service.SequenceService;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.service.TableRelationMetadataService;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.DateTimeUtils;

@SuppressWarnings("unchecked")
@Service
public class TableServiceImpl implements TableService {
	private static final Logger log = LoggerFactory.getLogger(TableServiceImpl.class);
	
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	
    public static final String COLUMN_CRAEAED_DATE = "createdDate";
    public static final String COLUMN_LAST_MODIFIED_DATE = "lastModifiedDate";

    private Pattern BCRYPT_PATTERN = Pattern
			.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
    
    @Autowired
    private TableMetadataService tableMetadataService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private TableRelationMetadataService tableRelationService;

    @Autowired
    private CrudService crudService;

    @Autowired
    private ApplicationContext applicationContext;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FileService fileService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(String name, Map<String, Object> map) {
        tableMetadataService.checkTable();
        
        TableDTO tableDTO = tableMetadataService.get(name);
        
        Map<String, Object> recId = insertRecursion(tableDTO, map);

        BusinessEvent businessEvent = new BusinessEvent(this, name);
        applicationContext.publishEvent(businessEvent);
        
        return convertToId(tableDTO, recId);
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(String name, List<Map<String, Object>> mapList) {
    	tableMetadataService.checkTable();
    	 
    	TableDTO tableDTO = tableMetadataService.get(name);

    	for (Map<String, Object> paramMap :  mapList) {
    		 Map<String, Object> fullTextBodyMap = getFullTextBody(tableDTO, paramMap);

	        if (fullTextBodyMap != null) {
	        	Entry<String, Object> item = fullTextBodyMap.entrySet().iterator().next();
	        	paramMap.put(item.getKey(), item.getValue());
	        }
    	}
     
        batchInsert(tableDTO, mapList);
        
        BusinessEvent businessEvent = new BusinessEvent(this, name);
	    applicationContext.publishEvent(businessEvent);
    }
    
    @Override
	public void importData(String name, String fileName) {
		File tempFile = fileService.getFile(fileName);
		importData(name, tempFile);
	}

	@Override
	public void importData(String name, File file) {
		try {
			Workbook wb = null;
	        FileInputStream in = new FileInputStream(file);
	        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
	            wb = new HSSFWorkbook(in);
	        } else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
	            wb = new XSSFWorkbook(in);
	        }
	        
	        // Excel的页签数量
	        int sheet_size = wb.getNumberOfSheets();
	        if (sheet_size == 0) {
				throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "Sheet页不能为0");
	        }
	        
	        Sheet sheet = wb.getSheetAt(0);
	        
	        int maxRow = sheet.getLastRowNum();
            log.info("总行数为：" + maxRow);
            
            List<String> columnCaptionList = new ArrayList<String>();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            
            TableDTO tableDTO = tableMetadataService.get(name);
            
            for (int row = 0; row <= maxRow; row++) {
                int maxCol = sheet.getRow(row).getLastCellNum();
                
                Map<String, Object> map = new HashMap<String, Object>();
            
                for (int col = 0; col < maxCol; col++) {
                	if (row == 0) {
                		String columnCaption = sheet.getRow(row).getCell(col).toString();
                		columnCaptionList.add(columnCaption);
                		log.info(columnCaption);
                	} else {
                		String caption = columnCaptionList.get(col);
                		ColumnDTO columnDTO = tableDTO.getColumnDTOList()
                		.stream()
                		.filter(t -> t.getCaption().equalsIgnoreCase(caption))
                		.findFirst().get();
                		String key = columnDTO.getName();
                		
                		Cell cell = sheet.getRow(row).getCell(col);
                		Object newObj = null;
                		if (cell != null) {
                			if (columnDTO.getDataType().equals(DataTypeEnum.BIGINT)) {
                    			newObj = getLong(cell);
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.INT)) {
                    			newObj = getInteger(cell);
                    		}  else if (columnDTO.getDataType().equals(DataTypeEnum.TINYINT)) {
                    			newObj = getInteger(cell);
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.DOUBLE)) {
                    			newObj = getDouble(cell);
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.FLOAT)) {
                    			newObj = getFloat(cell);
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.DECIMAL)) {
                    			newObj = getBigDecimal(cell);
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.PASSWORD)) {
                    			newObj = encodePassword(getString(cell));
                            } else if (columnDTO.getDataType().equals(DataTypeEnum.DATETIME)) {
                            	Long dateLong = getDate(cell);
                    			newObj = dateLong != null ? new Timestamp(dateLong) : null;
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.DATE)) {
                    			Long dateLong = getDate(cell);
                    			newObj = dateLong != null ? new Date(dateLong) : null;
                    		} else if (columnDTO.getDataType().equals(DataTypeEnum.TIME)) {
                    			Long dateLong = getDate(cell);
                    			newObj = dateLong != null ? new Time(dateLong) : null;
                    		} else {
                    			newObj = getString(cell);
                    		}
                		}
                		
                		if (caption.equalsIgnoreCase("名称")) {
                			map.put("name", newObj);
                		}
            			map.put(key, newObj);
                	}
                }
               
                if (row > 0) {
                	mapList.add(map);
                }
            }
           
            log.info(mapList.toString());
            importData(name, mapList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}
	}
	
	@Override
	public String getImportTemplate(String name, String type) {
		String fileName = null;
		try {
			if (StringUtils.isEmpty(type)) {
				type = EXCEL_XLSX;
			}
			
			fileName = fileService.getRandomFileName(name + "." + type);
			File file = fileService.getFile(fileName);
			
			log.info(file.getAbsolutePath());
			
			Workbook wb = null;
	        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
	            wb = new HSSFWorkbook();
	        } else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
	            wb = new XSSFWorkbook();
	        }
	        
	        Sheet sheet = wb.createSheet(name);
	        Row row = sheet.createRow(0);
	        
	        TableDTO tableDTO = tableMetadataService.get(name);
	        int i = 0;
	        for (ColumnDTO columnDTO : tableDTO.getColumnDTOList()) {
	        	 if (columnDTO.getInsertable()) {
	        		 Cell cell = row.createCell(i++);
		        	 cell.setCellValue(columnDTO.getCaption());
	        	 }
	        }
	        
 			FileOutputStream output;
 			output = new FileOutputStream(file.getAbsolutePath());

 			wb.write(output);

 			output.flush();
 			output.close();
 			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}
	}
	

	@Override
	public String exportData(String name, String type, String filter, String search, Condition condition) {
		String fileName = null;
		try {
			if (StringUtils.isEmpty(type)) {
				type = EXCEL_XLSX;
			}
			
			fileName = fileService.getRandomFileName(name + "." + type);
			File file = fileService.getFile(fileName);
			
			log.info(file.getAbsolutePath());
			
			Workbook wb = null;
	        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
	            wb = new HSSFWorkbook();
	        } else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
	            wb = new XSSFWorkbook();
	        }
	        
	        Sheet sheet = wb.createSheet(name);
	        Row row = sheet.createRow(0);
	        
	        TableDTO tableDTO = tableMetadataService.get(name);
	        int i = 0;
	        for (ColumnDTO columnDTO : tableDTO.getColumnDTOList()) {
	        	 Cell cell = row.createCell(i++);
	        	 cell.setCellValue(columnDTO.getCaption());
	        }
	        
	        int rowIndex = 1;
	        List<Map<String, Object>> mapList = listMain(name, null, null, filter, search, condition, null, null, null);
	        for (Map<String, Object> map : mapList) {
	        	 Row dataRow = sheet.createRow(rowIndex++);
	        	 
	        	 int cellIndex = 0;
	        	 for (ColumnDTO columnDTO : tableDTO.getColumnDTOList()) {
	        		 Cell dataCell = dataRow.createCell(cellIndex++);
	        		 Object value = map.get(columnDTO.getName());
	        		 dataCell.setCellValue(value != null ? value.toString() : "");
		         }
	        }
	        
 			FileOutputStream output;
 			output = new FileOutputStream(file.getAbsolutePath());

 			wb.write(output);

 			output.flush();
 			output.close();
 			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}
	}


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(String name, String id, Map<String, Object> newMap) {
        TableDTO tableDTO = tableMetadataService.get(name);
        
        log.info("newMap = " + newMap.toString());
        
        Map<String, Object> recId = convertToRecId(tableDTO, id);
        
        updateRecursion(tableDTO, recId, newMap);
        
        BusinessEvent businessEvent = new BusinessEvent(this, name);
        applicationContext.publishEvent(businessEvent);
    }

    @Override
    public void delete(String name, String id) {
        TableDTO tableDTO = tableMetadataService.get(name);

        Map<String, Object> recId = convertToRecId(tableDTO, id);
        
        deleteRecursion(tableDTO, recId);
        
        BusinessEvent businessEvent = new BusinessEvent(this, name);
        applicationContext.publishEvent(businessEvent);
    }

	@Override
	public void delete(String name, List<String> idList) {
		TableDTO tableDTO = tableMetadataService.get(name);
		 for (String id : idList) { 
			 Map<String, Object> recId = convertToRecId(tableDTO, id);
			 deleteRecursion(tableDTO, recId);
		 }
		 
		 BusinessEvent businessEvent = new BusinessEvent(this, name);
	     applicationContext.publishEvent(businessEvent);
	}
	
    @Override
    public Map<String, Object> get(String name, String id, String select, String expand) {
      	List<String> selectColumnNameList = convertSelect(select);
    	List<String> expandList = convertExpand(expand);

    	TableDTO tableDTO = tableMetadataService.get(name);
    	
    	Map<String, Object> recId = convertToRecId(tableDTO, id);
    	
        return selectRecursion(tableDTO, recId, selectColumnNameList, expandList);
    }
    
    @Override
    public List<Map<String, Object>> listAllByIds(String name, List<String> idList, String select, String expand) {
        TableDTO tableDTO = tableMetadataService.get(name);
      
        List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();

      	List<String> selectColumnNameList = convertSelect(select);
    	List<String> expandList = convertExpand(expand);

    	for (String id : idList) {
            log.info("listAllByIds->id = " + id);
            Map<String, Object> recId = convertToRecId(tableDTO, id);
            Map<String, Object> map = selectRecursion(tableDTO, recId, selectColumnNameList, expandList);
            maplist.add(map);
        }

        return maplist;
    }
    
    private List<Object> getValuesByColumnName(TableDTO tableDTO, List<Map<String, Object>> mapList, String columnName) {
    	 List<Object> values = new ArrayList<>();
    	 for (Map<String, Object> map : mapList) {
         	Object value = map.get(columnName);
         	if (value != null) {
         		if (tableDTO.getColumn(columnName).getMultipleValue()) {
         			String[] valueArr = value.toString().trim().split(",");
        			for (String v : valueArr) { 
        				if (!v.isEmpty()) {
        					values.add(v);
        				}
        			}
         		} else {
         			values.add(value);
         		}
         	}
         }
    	 
    	 return values.stream().distinct().collect(Collectors.toList());
    	 
    	 //return values;
    }
    
    private List<Map<String, Object>> getListDataByColumnName(List<Map<String, Object>> mapList, String columnName, Object value) {
    	 List<Map<String, Object>> filterMapList = new ArrayList<Map<String, Object>>();
    	 for (Map<String, Object> t : mapList) {
    		Object oldValue = t.get(columnName);
          	if (oldValue != null && oldValue.toString().equals(value.toString())) {
          		filterMapList.add(t);
          	}
         }
    	 
    	 return filterMapList;
    }
    
    private List<Map<String, Object>> getListDataByColumnName(List<Map<String, Object>> mapList, String columnName, List<Object> values) {
   	 	List<Map<String, Object>> filterMapList = new ArrayList<Map<String, Object>>();
   	 	for (Map<String, Object> t : mapList) {
	   	 	Object oldValue = t.get(columnName);
	     	if (oldValue != null
	     		&& values.stream().anyMatch(v -> oldValue.toString().equals(v.toString()))) {
	     		filterMapList.add(t);
	     	}
        }
   	 
   	 	return filterMapList;
    }
    
    private Map<String, Object> getOneDataByColumnName(List<Map<String, Object>> mapList, String columnName, Object value) {
      if (value == null) {
    	  return null;
      }
      
   	  for (Map<String, Object> t : mapList) {
   		Object oldValue = t.get(columnName);
     	if (oldValue != null && oldValue.toString().equals(value.toString())) {
     		return t;
     	}
      }
   	 
   	  return null;
    }
    
    private List<Map<String, Object>> getMultipleDataByColumnName(List<Map<String, Object>> mapList, String columnName, Object value) {
        List<Object> values = new ArrayList<>();
        
        if (value != null) {
        	String[] valueArr = value.toString().trim().split(",");
    		for (String v : valueArr) { 
    			if (!v.isEmpty()) {
    				values.add(v);
    			}
    		}
        } else {
        	return new ArrayList<Map<String, Object>>();
        }
    	
     	return this.getListDataByColumnName(mapList, columnName, values);
    }
    
    private List<Object> getRightValues (List<Object> allValues, List<Object> joinValues) {
    	 List<Object> rightValues = new ArrayList<>();
    	 for (Object t : allValues) {
    		 if (!joinValues.stream().anyMatch( v -> t.toString().equals(v.toString()))) {
    			 rightValues.add(t);
    		 }
         }
    	 
    	 return rightValues;
    }
    
    private List<String> getMainSelectAndSubSelectMap(List<String> selectList, Map<String, List<String>> subSelectMap) {
    	List<String> mainSelectList = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(selectList)) {
     	   for (String t : selectList) {
            	if (t.contains(".")) {
            		String key = getSubExpandKey(t);
            		String value = getSubExpandValue(t);
            		List<String> subSelectList = null;
            		if (subSelectMap.get(key) == null) {
            			subSelectList = new ArrayList<String>();
            			subSelectList.add(value);
            			subSelectMap.put(key, subSelectList);
            			mainSelectList.add(key);
            		} else {
            			subSelectList = subSelectMap.get(key);
            			subSelectList.add(value);
            		}
            	} else {
            		mainSelectList.add(t);
            	}
            }
        }
        
        return mainSelectList.stream().distinct().collect(Collectors.toList());
    }
    
    private void supplementFkColumnName(List<TableRelationDTO> tableRelationDTOList, List<String> mainSelectList) {
    	if (!CollectionUtils.isEmpty(mainSelectList)) {
  		  for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
  		      String relationName = tableRelationDTO.getName();

  		      if (mainSelectList.contains(relationName)
  		    		  && (tableRelationDTO.getRelationType() == TableRelationTypeEnum.ManyToOne
  		    		  || tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneSubToMain)) {
  		          String fkColumnName = tableRelationDTO.getFromColumnDTO().getName();
  		          mainSelectList.add(fkColumnName);
  		      }
  		  }
        }
    }
   
	@Override
    public List<Map<String, Object>> list(String name, String select, String expand, String filter,
    		String search, Condition condition, Integer offset, Integer limit, String orderby) {
        TableDTO mainTableDTO = tableMetadataService.get(name);
        
		Map<String, List<Map<String, Object>>> dicTableDataCacheMap = new HashMap<String, List<Map<String, Object>>>();
		
    	Stack<QueryData> tableQueryDataStack = new Stack<QueryData>();
    	
    	//解析select and expand
    	List<String> selectList = convertSelect(select);
    	List<String> expandList = convertExpand(expand);
        Map<String, List<String>> subSelectMap = new HashMap<String, List<String>>();
        List<String> mainSelectList = getMainSelectAndSubSelectMap(selectList, subSelectMap);
        
        supplementFkColumnName(tableRelationService.getFromTable(mainTableDTO.getId()), mainSelectList);
        
    	//主表查询参数
        QueryModel mainTableQueryModel = new QueryModel();
        Condition newCondition = convertConditon(mainTableDTO, filter, search, condition);
        mainTableQueryModel.setTableDTO(mainTableDTO);
        mainTableQueryModel.setSelectList(mainSelectList);
        mainTableQueryModel.setCondition(newCondition);
        mainTableQueryModel.setOffset(offset);
        mainTableQueryModel.setLimit(limit);
        mainTableQueryModel.setOrderby(orderby);

        //主表数据
    	List<Map<String, Object>> mainTableDataMapList = queryForList(mainTableQueryModel);
       
    	//主表入栈
    	QueryData mainTableQueryData = new QueryData();
    	mainTableQueryData.setTableDTO(mainTableDTO);
    	mainTableQueryData.setSelectList(mainSelectList);
    	mainTableQueryData.setSubSelectMap(subSelectMap);
    	mainTableQueryData.setTableDataMapList(mainTableDataMapList);
    	tableQueryDataStack.push(mainTableQueryData);
      
    	int count = 0;
        //遍历栈
        while (!tableQueryDataStack.isEmpty()) {
        	if (count > 9999) {
        		throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "表关系出现死循环，异常终止！");
        	}
        	++count;
        	//top出栈
        	QueryData topTableQueryData = tableQueryDataStack.pop();
        	TableDTO topTableDTO = topTableQueryData.getTableDTO();
        	log.info("************visit stack top: " + topTableDTO.getName());
        	
        	Long topTableId = topTableDTO.getId();
        	
        	List<Map<String, Object>> topMapList = topTableQueryData.getTableDataMapList();
        	
        	List<String> topSelectList = topTableQueryData.getSelectList();
        	Map<String, List<String>> topSubSelectMap = topTableQueryData.getSubSelectMap();
        	
    		//查询关联表
        	List<TableRelationDTO> tableRelationDTOList = tableRelationService.getFromTable(topTableId);
            for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            	String relationName = tableRelationDTO.getName();
                
            	//select 过滤
                if (!CollectionUtils.isEmpty(topSelectList) && !topSelectList.contains(relationName)) {
                	continue;
                }
                
                //关联表入栈
                Long relationTableId = tableRelationDTO.getToTableDTO().getId();
                TableDTO relationTableDTO = tableMetadataService.get(relationTableId);
                
                QueryModel relationQueryModel = new QueryModel();
                relationQueryModel.setTableDTO(relationTableDTO);
                
                if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToMany
                	|| tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneMainToSub) {
                	String pkColumnName = tableRelationDTO.getFromColumnDTO().getName();
                    String fkColumnName = tableRelationDTO.getToColumnDTO().getName();
                    
                    List<String> relationSelectList = topSubSelectMap.get(relationName);
                    
                    Map<String, List<String>> relationSubSelectMap = new HashMap<String, List<String>>();
                    List<String> relationMainSelectList = getMainSelectAndSubSelectMap(relationSelectList, relationSubSelectMap);
                    supplementFkColumnName(tableRelationService.getFromTable(relationTableId), relationMainSelectList);
                    
                    QueryData relationQueryData = new QueryData();
                    relationQueryData.setTableDTO(relationTableDTO);
                    relationQueryData.setSubSelectMap(relationSubSelectMap);
                    
                    //关联字段需要查询
                    if (!CollectionUtils.isEmpty(relationMainSelectList) && !relationMainSelectList.contains(relationName)) {
                    	relationMainSelectList.add(fkColumnName);
                    }
                    
                    relationQueryData.setSelectList(relationMainSelectList);
                    relationQueryModel.setSelectList(relationMainSelectList);
                    
                    Condition relationCondition = ConditionUtils.toCondition(fkColumnName, 
                    		getValuesByColumnName(topTableDTO, topMapList, pkColumnName));
                    relationQueryModel.setCondition(relationCondition);
                    
                    List<Map<String, Object>> relationTableDataMapList = new ArrayList<Map<String, Object>>();
                    if (relationCondition != null) {
                    	relationTableDataMapList = queryForList(relationQueryModel);
                    }
                    
                    //更新主表关联字段
                    for (Map<String, Object> topMap : topMapList) {
                    	Object value = topMap.get(pkColumnName);
                     	if (value != null) {
                     		 if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToMany) {
                         		List<Map<String, Object>> subRelationTableDataMapList = 
                         				getListDataByColumnName(relationTableDataMapList, fkColumnName, value);
                         		 
                     			topMap.put(relationName, subRelationTableDataMapList);
                     		 } else {
                     			Map<String, Object> subRelationTableDataMap = 
                         				getOneDataByColumnName(relationTableDataMapList, fkColumnName, value);
                     			
                     			topMap.put(relationName, subRelationTableDataMap);
                     		 }
                     	}
                    }
                    
                    relationQueryData.setTableDataMapList(relationTableDataMapList);
                    
                    //如果关联表数据为空，不再入栈
                    if (relationTableDataMapList.size() > 0) {
                    	 tableQueryDataStack.push(relationQueryData);
                    }
                } else if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.ManyToOne
                	|| tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneSubToMain) {
                     String fkColumnName = tableRelationDTO.getFromColumnDTO().getName();
                	 String pkColumnName = tableRelationDTO.getToColumnDTO().getName();
                	 String relatiobTableName = relationTableDTO.getName();
                	 
                	 List<String> relationMainSelectList = new ArrayList<String>();
                	 
                	 if (CollectionUtils.isEmpty(expandList) || !expandList.contains(relatiobTableName)) {
                		 relationMainSelectList.addAll(relationTableDTO.getPrimaryNameList());
                    	 relationMainSelectList.add(pkColumnName);
                         for (ColumnDTO t : relationTableDTO.getColumnDTOList()) {
                         	if (t.getDisplayable()) {
                         		relationMainSelectList.add(t.getName());
                         	}
                         }
                	 }
                	 
                     
                     relationMainSelectList = relationMainSelectList.stream().distinct().collect(Collectors.toList());
                     relationQueryModel.setSelectList(relationMainSelectList);
                	 
                     
                     
                	 //字典表主键
                	 List<Object> values = getValuesByColumnName(topTableDTO, topMapList, fkColumnName);
                	 log.info(relatiobTableName + " values" + values.toString());
                	 
                     List<Map<String, Object>> relationTableDataMapList = null;
                     
                     //查询旧的字典表数据
                     List<Map<String, Object>> relationTableCacheMapList = dicTableDataCacheMap.get(relatiobTableName);
                     
                     if (relationTableCacheMapList == null) {
                    	 Condition relationCondition = ConditionUtils.toCondition(pkColumnName, values);
                         relationQueryModel.setCondition(relationCondition);
                         
                    	 relationTableDataMapList = new ArrayList<Map<String, Object>>();
                         if (relationCondition != null) {
                        	 relationTableDataMapList = queryForList(relationQueryModel);
                        	 
                        	 //缓存dic
                             dicTableDataCacheMap.put(relatiobTableName, relationTableDataMapList);
                         }
                     } else {
                    	 log.info(relatiobTableName + " use cache!");
                    	 List<Object> cacheValues = getValuesByColumnName(relationTableDTO, relationTableCacheMapList, pkColumnName);
                    	 log.info(relatiobTableName + " cacheValues" + cacheValues.toString());
                    	 
                    	 List<Map<String, Object>> joinRelationTableDataMapList = getListDataByColumnName(relationTableCacheMapList, pkColumnName, values);
                    	 
                    	 List<Object> joinValues = getValuesByColumnName(relationTableDTO, joinRelationTableDataMapList, pkColumnName);
                    	 log.info(relatiobTableName + " joinValues" + joinValues.toString());
                    	 
                    	 List<Object> rightValues = getRightValues(values, joinValues);
                    	 log.info(relatiobTableName + " rightValues" + rightValues.toString());
                    	 
                    	 Condition relationCondition = ConditionUtils.toCondition(pkColumnName, rightValues);
                         relationQueryModel.setCondition(relationCondition);
                         
                         List<Map<String, Object>> rightRelationTableDataMapList = new ArrayList<Map<String, Object>>();
                         if (relationCondition != null) {
                        	 rightRelationTableDataMapList = queryForList(relationQueryModel);
                         }
                        
                         relationTableCacheMapList.addAll(rightRelationTableDataMapList);
                         
                         joinRelationTableDataMapList.addAll(rightRelationTableDataMapList);
                         
                         relationTableDataMapList = joinRelationTableDataMapList;
                     }
                     
                     //更新主表关联字段
                     for (Map<String, Object> topMap : topMapList) {
                     	Object value = topMap.get(fkColumnName);
                      	if (value != null) {
                      		
                      		if (topTableDTO.getColumn(fkColumnName).getMultipleValue()) {
                      			List<Map<String, Object>> subRelationTableDataMapList = 
                      					getMultipleDataByColumnName(relationTableDataMapList, pkColumnName, value);
                     			
                     			topMap.put(relationName, subRelationTableDataMapList);
                      		} else {
                      			Map<String, Object> subRelationTableDataMap = 
                         				getOneDataByColumnName(relationTableDataMapList, pkColumnName, value);
                     			
                     			topMap.put(relationName, subRelationTableDataMap);
                      		}
                      	}
                     }
                }
            }
        }
        
        log.info("visit stack count: " + count);
        
        return mainTableDataMapList;
    }
    
	@Override
    public List<Map<String, Object>> listMain(String name, String select, String expand, String filter,
    		String search, Condition condition, Integer offset, Integer limit, String orderby) {
        TableDTO tableDTO = tableMetadataService.get(name);
      
      	List<String> selectColumnNameList = convertSelect(select);
    	
    	Condition newCond = convertConditon(tableDTO, filter, search, condition);

    	List<Map<String, Object>> mapList = queryForList(tableDTO.getTableName(),  tableDTO.toDataTypeMap(), tableDTO.getPrimaryNameList(), selectColumnNameList, newCond, offset, limit, orderby);
        
        return mapList;
    }
    
    @Override
    public Condition convertConditon(String name, String filter, String search, Condition condition) {
        TableDTO tableDTO = tableMetadataService.get(name);

    	Condition newCond = convertConditon(tableDTO, filter, search, condition);

        return newCond;
    }

	@Override
	public Long count(String name, String filter, String search,  Condition condition) {
		TableDTO tableDTO = tableMetadataService.get(name);

		Condition newCond = convertConditon(tableDTO, filter, search, condition);

		return queryForCount(tableDTO.getTableName(), tableDTO.toDataTypeMap(), newCond);
	}

    @Override
    public void deleteAll(List<String> nameList) {
        for (String name : nameList) {
        	TableDTO tableDTO = tableMetadataService.get(name);
        	if (tableDTO != null) {
        		crudService.delete(tableDTO.getTableName());
        	}
        }
    }
    
    private String getStringByToString(Cell cell) {
		Object obj = cell;
		String objStr = null;
		if (obj != null) {
			objStr = obj.toString().replace((char) 12288, ' ').trim();
			objStr = objStr.replace(" ", "");
		}
		
		return objStr;
	}
    
    private String getString(Cell cell) {
    	String value = null;
		try {
			value = cell.getStringCellValue();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return value;
	}
	
	private BigDecimal getBigDecimal(Cell cell) {
		Object obj = cell;
		BigDecimal decimal = null;
		String objStr = null;
		try {
			if (obj != null) {
				objStr = obj.toString().replace((char) 12288, ' ').trim();
				if (!objStr.isEmpty()) {
					decimal = new BigDecimal(objStr);
				}
			}
		} catch (Exception e) {
			log.info(objStr);
		}
		
		return decimal;
	}
	
	private Integer getInteger(Cell cell) {
		Integer value = null;
		try {
			Double obj = cell.getNumericCellValue();
			value = obj.intValue();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return value;
	}
	
	private Long getLong(Cell cell) {
		Long value = null;
		try {
			Double obj = cell.getNumericCellValue();
			value = obj.longValue();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return value;
	}
	
	private Double getDouble(Cell cell) {
		Double value = null;
		try {
			value = cell.getNumericCellValue();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return value;
	}
	
	private Float getFloat(Cell cell) {
		Float value = null;
		try {
			Double obj = cell.getNumericCellValue();
			value = obj.floatValue();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return value;
	}
	
	private Long convertCellToLong(Cell cell) {
		Long value = null;
		String str = cell.toString();
		try {
			value = Long.parseLong(str);
		} catch (Exception e) {
			log.warn("CellType.NUMERIC try parase long:" + e.getMessage());
		}
		
		if (value == null) {
			try {
				BigDecimal b = new BigDecimal(str);
				value = b.longValue();
			} catch (Exception e) {
				log.warn("CellType.NUMERIC try parase BigDecimal long:" + e.getMessage());
			}
		}
		
		return value;
	}
	
	private Long getDate(Cell cell) {
		Long value = null;
		java.util.Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (cell.getCellType().equals(CellType.STRING)) {
				String str = cell.getStringCellValue();
				if (str != null) {
					try {
						value = Long.parseLong(str);
					} catch (Exception e) {
						log.warn("CellType.STRING try parase long:" + e.getMessage());
					}
					
					if (value == null) {
						date = sdf.parse(str);
						value = date.getTime();
					}
				}
			} else {
				date  = cell.getDateCellValue();
				log.info(sdf.format(date));
				value = date.getTime();
				
				Long newValue = convertCellToLong(cell);
				if (newValue != null) {
					value = newValue;
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return value;
	}
	
    private String convertToId(TableDTO tableDTO, Map<String, Object> recId) {
    	List<String> primaryNameList = tableDTO.getPrimaryNameList();
        if (primaryNameList.size() == 1) {
          return recId.get(primaryNameList.get(0)).toString();
        } else {
          List<String> recIdList = new ArrayList<String>();
          
          for (Map.Entry<String, Object> entry : recId.entrySet()) {
        	  recIdList.add(entry.getKey() + "=" + entry.getValue());
          }
          return String.join(",", recIdList);
        }
    }
    
    private String generateId(TableDTO tableDTO, Map<String, Object> data) {
    	List<String> primaryNameList = tableDTO.getPrimaryNameList();
        if (primaryNameList.size() == 1) {
          Object value = data.get(primaryNameList.get(0));
          if (value != null) {
        	  return value.toString();
          } else {
        	  return "";
          }
        } else {
          List<String> recIdList = new ArrayList<String>();
          
          for (String primaryName: primaryNameList) {
        	  Object value = data.get(primaryName);
        	  if (value != null) {
        		  recIdList.add(primaryName + "=" + value.toString());
              }
          }
          return String.join(",", recIdList);
        }
    }
    
    private Map<String, Object> generateRecId(TableDTO tableDTO, Map<String, Object> data) {
    	Map<String, Object> recId = new HashMap<String, Object>();
    	
    	List<String> primaryNameList = tableDTO.getPrimaryNameList();
        if (primaryNameList.size() == 1) {
          String primaryName = primaryNameList.get(0);
          Object value = data.get(primaryName);
          recId.put(primaryName, value);
        } else {
          for (String primaryName: primaryNameList) {
        	  Object value = data.get(primaryName);
        	  recId.put(primaryName, value);
          }
        }
        
        return recId;
    }
    
    private Map<String, Object> convertToRecId(TableDTO tableDTO, String id) {
    	String[] idArry = id.toString().trim().split(",");
    	
    	Map<String, Object> recId = new HashMap<String, Object>();
    	for (String t : idArry) {
    		String[] valueArray = t.split("=");
    		
    		String columnName = null;
    		String columnValue = null;
    		if (valueArray.length == 2) {
    			columnName = valueArray[0].trim();
        		columnValue = valueArray[1].trim();
    		} else {
    			columnName = tableDTO.getPrimaryNameList().get(0);
    			columnValue = valueArray[0].trim();
    		}
    		
    		Object value = columnValue;
    		
    		ColumnDTO columnDTO = tableDTO.getColumn(columnName);
    		switch (columnDTO.getDataType()) {
	            case BIT:    
	            case TINYINT:
	            case SMALLINT:
	            case MEDIUMINT:
	            case INT:
	            	value = Integer.parseInt(columnValue);
	            	break;
	            case BIGINT:
	            	value = Long.parseLong(columnValue);
	            	break;
	            case FLOAT:
	            case DOUBLE:
	            case DECIMAL:
	            	break;
	            case BOOL:
	            	value = Boolean.parseBoolean(columnValue);
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
	                break;
	            default:
	                break;
	        }
    		
    		recId.put(columnName, value);
	    }
    	return recId;
    }
    
    private String encodePassword(Object value) {
    	 String password = null;
    	 if (value != null) {
    		 password = value.toString();
        	 if (!BCRYPT_PATTERN.matcher(value.toString()).matches()){
        		 password = passwordEncoder.encode(password);
             }
    	 }
    	
    	 return password;
    }
    

    private void updateMainOnly(TableDTO tableDTO, Map<String, Object> recId, Map<String, Object> newMap) {
        List<String> columnNameList = new ArrayList<String>();
        List<Object> valueList = new ArrayList<Object>();
        tableDTO.getColumnDTOList().stream().forEach(t -> {
            if (t.getName().equalsIgnoreCase(COLUMN_LAST_MODIFIED_DATE)) {
                columnNameList.add(t.getName());
                valueList.add(DateTimeUtils.sqlTimestamp());
            } else if (newMap.containsKey(t.getName()) && !t.getName().equalsIgnoreCase(COLUMN_CRAEAED_DATE)) {
                columnNameList.add(t.getName());
                
                Object obj = newMap.get(t.getName());
                Object newObj = obj;
        		if (obj != null && !obj.toString().isEmpty()) {
        			String objStr = obj.toString();
        			if (t.getDataType().equals(DataTypeEnum.BIGINT)) {
            			newObj = Long.parseLong(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.INT)) {
            			newObj = Integer.parseInt(objStr);
            		}  else if (t.getDataType().equals(DataTypeEnum.TINYINT)) {
            			newObj = Integer.parseInt(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DOUBLE)) {
            			newObj = Double.parseDouble(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.FLOAT)) {
            			newObj = Float.parseFloat(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DECIMAL)) {
            			newObj = new BigDecimal(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.PASSWORD)) {
            			newObj = encodePassword(objStr);
                    } else if (t.getDataType().equals(DataTypeEnum.DATETIME)) {
            			newObj = Timestamp.valueOf(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DATE)) {
            			newObj = Date.valueOf(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.TIME)) {
            			newObj = Time.valueOf(objStr);
            		}
        		}
                
                valueList.add(newObj);
            }
        });

        String physicalTableName = tableDTO.getTableName();
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
    	for (int i = 0; i < columnNameList.size(); ++i) {
    		dataMap.put(columnNameList.get(i), valueList.get(i));
    	}
        
    	crudService.patch(physicalTableName, recId, dataMap);
    }
    
    private void updateRecursion(TableDTO tableDTO, Map<String, Object> recId, Map<String, Object> newMap) {
    	//获取旧表数据
        Map<String, Object> oldMap = selectRecursion(tableDTO, recId, null, null);
        log.info("oldMap = " + oldMap.toString());
        
        Map<String, Object> oldMainMap = queryForMap(tableDTO, null, recId);
        log.info("oldMainMap = " + oldMainMap.toString());

        //1. 多对一，一对一：本表字段平铺
    	List<TableRelationDTO> tableRelationDTOList = tableRelationService.getFromTable(tableDTO.getId());
        for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            String relationName = tableRelationDTO.getName();
            if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.ManyToOne 
            		|| tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneSubToMain) {
                String fkName = tableRelationDTO.getFromColumnDTO().getName();
                Object obj = newMap.get(relationName);
                if (obj != null) {
                	if (tableDTO.getColumn(fkName).getMultipleValue()) {
                		List<Map<String, Object>> relationMapList = (List<Map<String, Object>>) obj;
                		List<String> valueList =  new ArrayList<String>();
                		for (Map<String, Object> relationMap : relationMapList) {
                			Object valueObj = relationMap.get(tableRelationDTO.getToColumnDTO().getName());
                			if (valueObj != null) {
                				valueList.add(valueObj.toString());
                			}
                		}
                		
                		newMap.put(fkName, String.join(",", valueList));
                	} else {
                		Map<String, Object> relationMap = (Map<String, Object>) obj;
                        newMap.put(fkName, relationMap.get(tableRelationDTO.getToColumnDTO().getName()));
                    }
                }
            }
        }
        log.info("newMap = " + newMap.toString());
        
        //2. 把新表字段合并到旧表，然后计算fullText
        for(Map.Entry<String, Object> entry : newMap.entrySet()) {
        	oldMainMap.put(entry.getKey(), entry.getValue());
        }
        log.info("oldMainMap = " + oldMainMap.toString());

        Map<String, Object> fullTextBodyMap = getFullTextBody(tableDTO, oldMainMap);
        if (fullTextBodyMap != null) {
        	Entry<String, Object> item = fullTextBodyMap.entrySet().iterator().next();
        	newMap.put(item.getKey(), item.getValue());
        	log.info("newMap = " + newMap.toString());
        }
        
        //3. 修改本表字段
        updateMainOnly(tableDTO, recId, newMap);
        
        //4. 一对多，删除，修改，添加, 一对一，修改，添加
        for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToMany) {
                Long relationTableId = tableRelationDTO.getToTableDTO().getId();
                String relationName = tableRelationDTO.getName();
                TableDTO relationTableDTO = tableMetadataService.get(relationTableId);

                Object obj = newMap.get(relationName);
                if (obj != null) {
                	List<String> deleteIds = new ArrayList<String>();
                	List<Map<String, Object>> insertMapList = new ArrayList<Map<String, Object>>();
                	List<Map<String, Object>> updateMapList = new ArrayList<Map<String, Object>>();
                    
                    List<Map<String, Object>> oldMapList = (List<Map<String, Object>>)oldMap.get(relationName);
                    List<Map<String, Object>> newMapList = (List<Map<String, Object>>)newMap.get(relationName);
                 
                    //1. delete
            		for (Map<String, Object> oldItem : oldMapList) {
            			log.info(oldItem.toString());
            			
            			String oldItemId = generateId(relationTableDTO, oldItem);
            			if (!newMapList.stream().anyMatch(t -> oldItemId.equals(generateId(relationTableDTO, t)))) {
            				deleteIds.add(oldItemId);
            			}
            		}
            		
            		// 2. add and update
            		Optional<Map<String, Object>> optional = null;
            		for (Map<String, Object> newItem : newMapList) {
            			log.info(newItem.toString());
            			String newItemId = generateId(relationTableDTO, newItem);
            			if (StringUtils.isAllEmpty(newItemId)) {
            				insertMapList.add(newItem);
            			} else {
            				optional = oldMapList.stream().filter(t -> newItemId.equals(generateId(relationTableDTO, t))).findFirst();
                			if (optional.isPresent()) {
                				updateMapList.add(newItem);
                			} else {
                				insertMapList.add(newItem);
                			}
            			}
            		}
            		
            		String pkName = tableRelationDTO.getFromColumnDTO().getName();
                    String fkName = tableRelationDTO.getToColumnDTO().getName();
                    for (String deleteId : deleteIds) {
                    	 Map<String, Object> deleteRecId = convertToRecId(relationTableDTO, deleteId);
                    	 deleteRecursion(relationTableDTO, deleteRecId);
                    }
                    
                    updateMapList.stream().forEach(t -> {
                        t.put(fkName, recId.get(pkName));
                        Map<String, Object> relationRecId = generateRecId(relationTableDTO, t);
                        log.info("updateRecursion relationRecId: " + relationRecId);
                        updateRecursion(relationTableDTO, relationRecId, t);
                    });
                    
            		insertMapList.stream().forEach(t -> {
                        t.put(fkName, recId.get(pkName));
                        Map<String, Object> relationRecId = insertRecursion(relationTableDTO, t);
                        log.info("insertRecursion relationRecId: " + relationRecId);
                    });
                }
            } else if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneMainToSub) {
                Long relationTableId = tableRelationDTO.getToTableDTO().getId();
                String relationName = tableRelationDTO.getName();
                TableDTO relationTableDTO = tableMetadataService.get(relationTableId);

                Map<String, Object> obj = (Map<String, Object>)newMap.get(relationName);
                if (obj != null) {
                	String pkName = tableRelationDTO.getFromColumnDTO().getName();
                	String fkName = tableRelationDTO.getToColumnDTO().getName();
                    obj.put(fkName, recId.get(pkName));
                    
                    String newItemId = generateId(relationTableDTO, obj);
        			if (StringUtils.isAllEmpty(newItemId)) {
                		Map<String, Object> relationRecId = insertRecursion(relationTableDTO, obj);
                        log.info("insertRecursion relationRecId: " + relationRecId);
                	} else {
                		Map<String, Object> relationRecId = generateRecId(relationTableDTO, obj);
                        log.info("updateRecursion relationRecId: " + relationRecId);
                        updateRecursion(relationTableDTO, relationRecId, obj);
                	}
                }
            } 
        }
    }	
    
    private List<String> convertSelect(String select) {
     	List<String> selectColumnNameList = null;
    	if (!StringUtils.isEmpty(select)) {
    		selectColumnNameList = Arrays.stream(select.split(",")).map(String::trim).collect(Collectors.toList());
    	}
    	return selectColumnNameList;
    }

    private List<String> convertExpand(String expand) {
    	List<String> expandList = null;
    	if (!StringUtils.isEmpty(expand)) {
    		expandList = Arrays.stream(expand.split(",")).map(String::trim).collect(Collectors.toList());
    	}
    	return expandList;
    }

    private Condition convertConditon(TableDTO tableDTO, String filter, String search, Condition condition) {
    	Condition newCond = null;

    	try {
    		//1. filter
	    	Condition filterCond = ConditionUtils.toCondition(filter);
	    	
	    	//2. search
	    	LeafCondition searchCond = null;
	    	if (!StringUtils.isEmpty(search)) {
		        Optional<ColumnDTO> fullTextColumnOptional = tableDTO.getColumnDTOList().stream()
		        		.filter(t -> IndexTypeEnum.FULLTEXT.equals(t.getIndexType()))
		        		.findFirst();
		        if (fullTextColumnOptional.isPresent()) {
		        	searchCond = new LeafCondition();
		        	searchCond.setColumnName(fullTextColumnOptional.get().getName());
		        	searchCond.setOperatorType(OperatorTypeEnum.SEARCH);
		        	searchCond.addValue(search);
		        }
			}
	    	
	    	//3. other condition
	    	if (filterCond == null && searchCond == null && condition == null) {
	    		newCond = null;
	    	} else {
	    		CompositeCondition compositeCondition = new CompositeCondition();
	    		compositeCondition.add(filterCond);
	    		compositeCondition.add(condition);
	    		compositeCondition.add(searchCond);
	    		
	    		newCond = compositeCondition;

	    		log.info(newCond.toString());
	    		log.info(newCond.toQuerySql());
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}

    	return newCond;
    }

    private Map<String, Object> getFullTextBody(TableDTO tableDTO, Map<String, Object> paramMap) {
        String body = "";

        Optional<ColumnDTO> fullTextColumnOptional = tableDTO.getColumnDTOList().stream()
        		.filter(t -> IndexTypeEnum.FULLTEXT.equals(t.getIndexType()))
        		.findFirst();

        List<String> bodyList = new ArrayList<String>();
        tableDTO.getColumnDTOList().stream().forEach(t -> {
        	Object obj = paramMap.get(t.getName());
        	if (Boolean.TRUE.equals(t.getQueryable()) && obj != null) {
        		if (!StringUtils.isEmpty(obj.toString())) {
        			bodyList.add(obj.toString());
        		}
        	}
        });

        body = String.join(" ", bodyList);

        //log.info("getFullTextBody paramMap: " + paramMap.toString());
        //log.info("getFullTextBody body: " + body);

        Map<String, Object> fullTextBodyMap = null;
        if (fullTextColumnOptional.isPresent()) {
        	fullTextBodyMap = new LinkedHashMap<String, Object>();
        	fullTextBodyMap.put(fullTextColumnOptional.get().getName(), body);
        	//log.info("getFullTextBody fullTextBodyMap: " + fullTextBodyMap.toString());
        }

        return fullTextBodyMap;
    }
    
    private Map<String, Object> insertRecursion(TableDTO tableDTO, Map<String, Object> paramMap) {
        // 1. 多对一，一对一：本表字段平铺
        Long tableId = tableDTO.getId();
        List<TableRelationDTO> tableRelationDTOList = tableRelationService.getFromTable(tableId);
        for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            String relationName = tableRelationDTO.getName();
            if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.ManyToOne || tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneSubToMain) {
                String fkName = tableRelationDTO.getFromColumnDTO().getName();
                Object obj = paramMap.get(relationName);
                if (paramMap.get(fkName) == null && obj != null) {
                	if (tableDTO.getColumn(fkName).getMultipleValue()) {
                		List<Map<String, Object>> relationMapList = (List<Map<String, Object>>) obj;
                		List<String> valueList =  new ArrayList<String>();
                		for (Map<String, Object> relationMap : relationMapList) {
                			Object valueObj = relationMap.get(tableRelationDTO.getToColumnDTO().getName());
                			if (valueObj != null) {
                				valueList.add(valueObj.toString());
                			}
                		}
                		
                		paramMap.put(fkName, String.join(",", valueList));
                	} else {
                		Map<String, Object> relationMap = (Map<String, Object>) obj;
                        paramMap.put(fkName, relationMap.get(tableRelationDTO.getToColumnDTO().getName()));
                	}
                }
            }
        }

        // 2. 插入本表数据
        Map<String, Object> recId = insertMainOnly(tableDTO, paramMap);

        // 3. 插入关联表数据
        for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToMany) {
                Long relationTableId = tableRelationDTO.getToTableDTO().getId();
                String relationName = tableRelationDTO.getName();
                TableDTO relationTableDTO = tableMetadataService.get(relationTableId);

                Object obj = paramMap.get(relationName);
                if (obj != null) {
                    String fkName = tableRelationDTO.getToColumnDTO().getName();
                    String pkName = tableRelationDTO.getFromColumnDTO().getName();
                    List<Map<String, Object>> mapList = (List<Map<String, Object>>) paramMap.get(relationName);
                    mapList.stream().forEach(t -> {
                        t.put(fkName, recId.get(pkName));
                        Map<String, Object> relationRecId = insertRecursion(relationTableDTO, t);
                        log.info("relationRecId: " + relationRecId);
                    });
                }
            } else if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneMainToSub) {
                Long relationTableId = tableRelationDTO.getToTableDTO().getId();
                String relationName = tableRelationDTO.getName();
                TableDTO relationTableDTO = tableMetadataService.get(relationTableId);

                Map<String, Object> obj = (Map<String, Object>)paramMap.get(relationName);
                if (obj != null) {
                    String pkName = tableRelationDTO.getFromColumnDTO().getName();
                    String fkName = tableRelationDTO.getToColumnDTO().getName();
                    obj.put(fkName, recId.get(pkName));
                    Map<String, Object> relationRecId = insertRecursion(relationTableDTO, obj);
                    log.info("relationRecId: " + relationRecId);
                }
            } 
        }

        return recId;
    }

    private Map<String, Object> insertMainOnly(TableDTO tableDTO, Map<String, Object> paramMap) {
    	Map<String, Object> fullTextBodyMap = getFullTextBody(tableDTO, paramMap);
        if (fullTextBodyMap != null) {
          	Entry<String, Object> item = fullTextBodyMap.entrySet().iterator().next();
          	paramMap.put(item.getKey(), item.getValue());
        }
    	
        List<String> columnNameList = getColumnNameList(tableDTO);
        Map<Long, List<Object>> seqValueListMap = getSeqValueListMap(tableDTO, paramMap);
        List<Object> valueList = getColumnValueList(tableDTO, paramMap, seqValueListMap);

        List<String> primaryNameList = tableDTO.getPrimaryNameList();	
        Map<String, Object> recId = insert(tableDTO.getTableName(), primaryNameList, tableDTO.getAutoIncrement(), columnNameList, valueList);
        if (recId == null) {
        	recId = new HashMap<String, Object>();
        	for (String primaryName : primaryNameList) {
        		recId.put(primaryName, paramMap.get(primaryName));
        	}
        }
        
        return recId;
    }

    private Map<String, Object> insert(String tableName, List<String> primaryNameList,  boolean autoIncrement, List<String> columnNameList, List<Object> valueList) {
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	for (int i = 0; i < columnNameList.size(); ++i) {
    		dataMap.put(columnNameList.get(i), valueList.get(i));
    	}
    	
    	Map<String, Object> keyMap = new HashMap<String, Object>();
    	keyMap = crudService.create(tableName, dataMap, primaryNameList.toArray(new String[0]), autoIncrement);
    	
    	return keyMap;
    }

    private int[] batchInsert(TableDTO tableDTO, List<Map<String, Object>> mapList) {
        List<String> columnNameList = getColumnNameList(tableDTO);
        Map<Long, List<Object>> seqValueListMap = getSeqValueListMap(tableDTO, mapList);
        List<List<Object>> valueListList = getColumnValueListList(tableDTO, mapList, seqValueListMap);

        return batchInsert(tableDTO.getTableName(), columnNameList, valueListList);
    }

    private int[] batchInsert(String tableName, List<String> columnNameList, List<List<Object>> valueListList) {
    	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    	
    	for (List<Object> valueList : valueListList) {
    		Map<String, Object> dataMap = new HashMap<String, Object>();
        	for (int i = 0; i < columnNameList.size(); ++i) {
        		dataMap.put(columnNameList.get(i), valueList.get(i));
        	}
    		
        	mapList.add(dataMap);
    	}
    	
    	return crudService.batchCreateMap(tableName, mapList);
    }

    private int delete(String tableName, Condition cond) {
        return crudService.delete(tableName, cond);
    }
    
    private int deleteMainOnly(String tableName, Map<String, Object> recId) {
    	return this.delete(tableName, ConditionUtils.toCondition(recId));	
    }

    private void deleteRecursion(TableDTO tableDTO, Map<String, Object> recId) {
        log.info("deleteRecursion = " + tableDTO.getName() + ", recId:" + recId);

        // 1. 一对多，一对一
        Long tableId = tableDTO.getId();
        List<TableRelationDTO> tableRelationDTOList = tableRelationService.getFromTable(tableId);

        for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            Long relationTableId = tableRelationDTO.getToTableDTO().getId();
            TableDTO relationTableDTO = tableMetadataService.get(relationTableId);

            if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneMainToSub
            	|| tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToMany) {
            	String pkColumnName = tableRelationDTO.getFromColumnDTO().getName();
                String fkColumnName = tableRelationDTO.getToColumnDTO().getName();
                List<Map<String, Object>> relationRecIdList = queryIds(relationTableDTO.getTableName(), relationTableDTO.toDataTypeMap(), relationTableDTO.getPrimaryNameList(), ConditionUtils.toCondition(fkColumnName, recId.get(pkColumnName)));
                for (Map<String, Object> relationRecId : relationRecIdList) {
                    log.info("relationRecId = " + relationRecId);
                    deleteRecursion(relationTableDTO, relationRecId);
                }
            }
        }

        // 2. 删除本表数据
        deleteMainOnly(tableDTO.getTableName(), recId);
    }
    
	private String getSubExpandKey(String expand) {
        int index = expand.indexOf(".");
 
        String result = expand.substring(0, index);
        
        return result;
    }
	
	private String getSubExpandValue(String expand) {
        int index = expand.indexOf(".");
 
        String result = expand.substring(index + 1);
        
        return result;
    }

    private Map<String, Object> selectRecursion(TableDTO tableDTO, Map<String, Object> recId,
    		List<String> selectColumnNameList,  List<String> expandList) {
        Long tableId = tableDTO.getId();

        // 1. 关联表字段select
        List<TableRelationDTO> tableRelationDTOList = tableRelationService.getFromTable(tableId);
        
        List<String> mainSelectList = new ArrayList<String>();
        Map<String, List<String>> subSelectMap = new HashMap<String, List<String>>();
        if (!CollectionUtils.isEmpty(selectColumnNameList)) {
     	   for (String t : selectColumnNameList) {
            	if (t.contains(".")) {
            		String key = getSubExpandKey(t);
            		String value = getSubExpandValue(t);
            		List<String> subSelectList = null;
            		if (subSelectMap.get(key) == null) {
            			subSelectList = new ArrayList<String>();
            			subSelectList.add(value);
            			subSelectMap.put(key, subSelectList);
            			mainSelectList.add(key);
            		} else {
            			subSelectList = subSelectMap.get(key);
            			subSelectList.add(value);
            		}
            	} else {
            		mainSelectList.add(t);
            	}
            }
        }
        
        if (!CollectionUtils.isEmpty(mainSelectList)) {
		  for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
		      String relationName = tableRelationDTO.getName();

		      if (mainSelectList.contains(relationName)
		    		  && (tableRelationDTO.getRelationType() == TableRelationTypeEnum.ManyToOne
		    		  || tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneSubToMain)) {
		          String fkColumnName = tableRelationDTO.getFromColumnDTO().getName();
		          mainSelectList.add(fkColumnName);
		      }
		  }
        }
        
        List<String> mainExpandList = new ArrayList<String>();
        Map<String, List<String>> subExpandMap = new HashMap<String, List<String>>();
       
        if (!CollectionUtils.isEmpty(expandList)) {
    	   for (String t : expandList) {
           	if (t.contains(".")) {
           		String key = getSubExpandKey(t);
           		String value = getSubExpandValue(t);
           		List<String> subExpandList = null;
           		if (subExpandMap.get(key) == null) {
           			subExpandList = new ArrayList<String>();
           			subExpandList.add(value);
           			subExpandMap.put(key, subExpandList);
           		} else {
           			subExpandList = subExpandMap.get(key);
           			subExpandList.add(value);
           		}
           	} else {
           		mainExpandList.add(t);
           	}
           }
        }
        
        // 2. 主表
        Map<String, Object> map = queryForMap(tableDTO, mainSelectList, recId);

        // 3. 关联表数据
        for (TableRelationDTO tableRelationDTO : tableRelationDTOList) {
            Long relationTableId = tableRelationDTO.getToTableDTO().getId();
            String relationName = tableRelationDTO.getName();

            if (!CollectionUtils.isEmpty(mainSelectList) && !mainSelectList.contains(relationName)) {
            	continue;
            }

            TableDTO relationTableDTO = tableMetadataService.get(relationTableId);

            if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToMany) {
            	String pkColumnName = tableRelationDTO.getFromColumnDTO().getName();
                String fkColumnName = tableRelationDTO.getToColumnDTO().getName();

                List<Map<String, Object>> relationRecIdList = queryIds(relationTableDTO.getTableName(), relationTableDTO.toDataTypeMap(), relationTableDTO.getPrimaryNameList(), ConditionUtils.toCondition(fkColumnName, recId.get(pkColumnName)));
                List<Map<String, Object>> relationMapList = new ArrayList<Map<String, Object>>();

                for (Map<String, Object> relationRecId : relationRecIdList) {
                    log.info("selectRecursion.relationRecId = " + relationRecId);
                    Map<String, Object> relationMap = selectRecursion(relationTableDTO, relationRecId, subSelectMap.get(relationName), subExpandMap.get(relationName));
                    relationMapList.add(relationMap);
                }
                map.put(relationName, relationMapList);
            } else if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneMainToSub) {
            	String pkColumnName = tableRelationDTO.getFromColumnDTO().getName();
                String fkColumnName = tableRelationDTO.getToColumnDTO().getName();
                List<Map<String, Object>> relationRecIdList = queryIds(relationTableDTO.getTableName(), relationTableDTO.toDataTypeMap(), relationTableDTO.getPrimaryNameList(), ConditionUtils.toCondition(fkColumnName, recId.get(pkColumnName)));
                
                for (Map<String, Object> relationRecId : relationRecIdList) {
                    log.info("selectRecursion.relationRecId = " + relationRecId);
                    Map<String, Object> relationMap = selectRecursion(relationTableDTO, relationRecId, subSelectMap.get(relationName), subExpandMap.get(relationName));
                    map.put(relationName, relationMap);
                    break;
                }
            } else if (tableRelationDTO.getRelationType() == TableRelationTypeEnum.ManyToOne
            	|| tableRelationDTO.getRelationType() == TableRelationTypeEnum.OneToOneSubToMain) {
                String pkColumnName = tableRelationDTO.getToColumnDTO().getName();
                String fkColumnName = tableRelationDTO.getFromColumnDTO().getName();
                Object fkUqValue = map.get(fkColumnName);
                
                if (fkUqValue != null) {
                	 List<String> relationSelectColumnNameList = new ArrayList<String>();
                     relationSelectColumnNameList.addAll(relationTableDTO.getPrimaryNameList());
                     relationSelectColumnNameList.add(pkColumnName);
                     for (ColumnDTO t : relationTableDTO.getColumnDTOList()) {
                     	if (t.getDisplayable()) {
                     		relationSelectColumnNameList.add(t.getName());
                     	}
                     }
                     
                     if (!CollectionUtils.isEmpty(mainExpandList) && mainExpandList.contains(relationName)) {
                     	relationSelectColumnNameList = null;
                     }
                     
                     if (tableDTO.getColumn(fkColumnName).getMultipleValue()) {
                    	 List<Object> fkUqValues = new ArrayList<>();
                     	 String[] fkUqValueArr = fkUqValue.toString().trim().split(",");
                 		 for (String f : fkUqValueArr) { 
                 			if (!f.isEmpty()) {
                 				fkUqValues.add(f);
                 			}
                 		 }
                 		 
                 		 List<Map<String, Object>> relationMapList = new ArrayList<Map<String, Object>>();
                 		 for (Object f : fkUqValues) { 
                 			 Map<String, Object> relationRecId = new HashMap<String, Object>();
                             relationRecId.put(pkColumnName, f);
                             
                             Map<String, Object> relationMap = queryForMap(relationTableDTO, relationSelectColumnNameList, relationRecId);
                             relationMapList.add(relationMap);
                 		 }
                 		 
                 		 map.put(relationName, relationMapList);
                     } else {
                    	 Map<String, Object> relationRecId = new HashMap<String, Object>();
                         relationRecId.put(pkColumnName, fkUqValue);
                         
                         Map<String, Object> relationMap = queryForMap(relationTableDTO, relationSelectColumnNameList, relationRecId);
                         map.put(relationName, relationMap);
                     }
                }
            }
        }

        return map;
    }
   
    private List<Map<String, Object>> queryIds(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> primaryNameList, Condition cond) {
        List<Map<String, Object>> ids = queryForList(tableName, dataTypeMap, primaryNameList, cond);
        
        return ids;
    }

    private List<Map<String, Object>> queryIds(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> primaryNameList, Condition cond, Integer offset, Integer limit, String orderby) {
        List<Map<String, Object>> ids = queryForList(tableName, dataTypeMap, primaryNameList, primaryNameList, cond, offset, limit, orderby);
       
        return ids;
    }

    private List<Map<String, Object>> queryForList(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectColumnNameList, Condition cond) {
    	 return crudService.list(tableName, dataTypeMap, selectColumnNameList, cond, null, null, null);
    }

    private List<Map<String, Object>> queryForList(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> primaryNameList, List<String> selectColumnNameList, Condition cond, Integer offset, Integer limit, String orderby) {
    	String sqlQuotation = crudService.getSqlQuotation();
        
    	String newOrderby = null;
        if (StringUtils.isEmpty(orderby)) {
        	List<String> primaryNameWithQuotationList = new ArrayList<String>();
        	for (String t : primaryNameList) {
        		primaryNameWithQuotationList.add(sqlQuotation + t + sqlQuotation);
        	}
        	
        	newOrderby = String.join(",", primaryNameWithQuotationList) + " DESC";
        } else {
        	log.info(orderby);
        	String[] orderbys = orderby.replaceAll(" +", ";").split(";");
        	
        	List<String> newOrderbys = new ArrayList<String>();
        	
        	List<String> orderbyNames = new ArrayList<String>();
        	for (String t : orderbys) {
        		if (t.equalsIgnoreCase("ASC") || t.equalsIgnoreCase("DESC")) {
        			newOrderbys.add(t);
        		} else {
        			String[] orderbyNameArr = t.split(",");
        			for (String n : orderbyNameArr) { 
        				orderbyNames.add(sqlQuotation + n + sqlQuotation);
        			}
        		}
        	}
        	newOrderby = String.join(",", orderbyNames) + " " +  String.join(" ", newOrderbys);
        	
        	log.info(newOrderby);
        }

        return crudService.list(tableName, dataTypeMap, selectColumnNameList, cond, newOrderby, offset, limit);
    }

    private List<Map<String, Object>> queryForList(QueryModel queryModel) {
    	TableDTO tableDTO = queryModel.getTableDTO();

    	List<String> fullSelectColumnNameList = new ArrayList<String>();
        tableDTO.getColumnDTOList().stream().forEach(t -> {
        	if (!IndexTypeEnum.FULLTEXT.equals(t.getIndexType())) {
            	fullSelectColumnNameList.add(t.getName());
        	}
        });

    	if (!CollectionUtils.isEmpty(queryModel.getSelectList())) {
        	fullSelectColumnNameList.retainAll(queryModel.getSelectList());
        }
    	
    	if (CollectionUtils.isEmpty(fullSelectColumnNameList)) {
            tableDTO.getColumnDTOList().stream().forEach(t -> {
            	if (!IndexTypeEnum.FULLTEXT.equals(t.getIndexType())) {
                	fullSelectColumnNameList.add(t.getName());
            	}
            });
    	}
    	
    	List<Map<String, Object>> mapList = queryForList(tableDTO.getTableName(),  
    			tableDTO.toDataTypeMap(), 
    			tableDTO.getPrimaryNameList(), 
    			fullSelectColumnNameList, 
    			queryModel.getCondition(),
    			queryModel.getOffset(), 
    			queryModel.getLimit(), 
    			queryModel.getOrderby());
    	
    	return mapList;

    }

    
    private Long queryForCount(String tableName,  Map<String, DataTypeEnum> dataTypeMap, Condition cond) {
        return crudService.count(tableName, dataTypeMap, cond);
    }

    private Map<String, Object> queryForMap(TableDTO tableDTO, List<String> selectColumnNameList, Map<String, Object> recId) {
    	List<String> fullSelectColumnNameList = new ArrayList<String>();
        tableDTO.getColumnDTOList().stream().forEach(t -> {
        	if (!IndexTypeEnum.FULLTEXT.equals(t.getIndexType())) {
            	fullSelectColumnNameList.add(t.getName());
        	}
        });

    	if (!CollectionUtils.isEmpty(selectColumnNameList)) {
        	fullSelectColumnNameList.retainAll(selectColumnNameList);
        }
    	
    	Condition cond = ConditionUtils.toCondition(recId);

    	return queryForMap(tableDTO.getTableName(), tableDTO.toDataTypeMap(), fullSelectColumnNameList, cond);
    }

    private Map<String, Object> queryForMap(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectColumnNameList, Condition cond) {
        List<Map<String, Object>> mapList = crudService.list(tableName, dataTypeMap, selectColumnNameList, cond, null, null, null);
        int count = mapList.size();
        if (count > 0) {
        	 if (count > 1) {
        		 log.warn(tableName + "queryForMap size:" + count);
        	 }
        	 return mapList.get(0);
        } else {
        	return null;
        }
    }

    private List<String> getColumnNameList(TableDTO tableDTO) {
        List<String> columnNameList = new ArrayList<String>();
        tableDTO.getColumnDTOList().stream().forEach(t -> {
        	if (!Boolean.TRUE.equals(t.getAutoIncrement())) {
        		columnNameList.add(t.getName());
        	}
        });
        return columnNameList;
    }

    private List<List<Object>> getColumnValueListList(TableDTO tableDTO, List<Map<String, Object>> mapList, Map<Long, List<Object>> seqValueListMap) {
        List<List<Object>> valueListList = new ArrayList<List<Object>>();

        for (Map<String, Object> paramMap : mapList) {
            List<Object> valueList = getColumnValueList(tableDTO, paramMap, seqValueListMap);
            valueListList.add(valueList);
        }

        return valueListList;
    }

    private List<Object> getColumnValueList(TableDTO tableDTO, Map<String, Object> paramMap, Map<Long, List<Object>> seqValueListMap) {
        List<Object> valueList = new ArrayList<Object>();
        tableDTO.getColumnDTOList().stream().forEach(t -> {
        	if (!Boolean.TRUE.equals(t.getAutoIncrement())) {
        		Object obj = getColumnValue(t, paramMap, seqValueListMap);
        		Object newObj = obj;
        		
        		if (obj != null && !obj.toString().isEmpty()) {
        			String objStr = obj.toString();
        			if (t.getDataType().equals(DataTypeEnum.BIGINT)) {
            			newObj = Long.parseLong(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.INT)) {
            			newObj = Integer.parseInt(objStr);
            		}  else if (t.getDataType().equals(DataTypeEnum.TINYINT)) {
            			newObj = Integer.parseInt(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DOUBLE)) {
            			newObj = Double.parseDouble(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.FLOAT)) {
            			newObj = Float.parseFloat(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DECIMAL)) {
            			newObj = new BigDecimal(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DATETIME)) {
            			newObj = Timestamp.valueOf(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.DATE)) {
            			newObj = Date.valueOf(objStr);
            		} else if (t.getDataType().equals(DataTypeEnum.TIME)) {
            			newObj = Time.valueOf(objStr);
            		}
        		}
        		
        		valueList.add(newObj);
        	}
        });

        return valueList;
    }

    private Object getColumnValue(ColumnDTO columnDTO, Map<String, Object> paramMap, Map<Long, List<Object>> seqValueListMap) {
        Object value = null;
        if (paramMap.containsKey(columnDTO.getName())) {
            value = paramMap.get(columnDTO.getName());
            if (columnDTO.getDataType().equals(DataTypeEnum.PASSWORD)) {
            	value = encodePassword(value);
            }
        } else if (columnDTO.getSeqId() != null) {
            Long key = columnDTO.getSeqId();
            List<Object> valueList = seqValueListMap.get(key);
            value = valueList.remove(0);
        } else if (columnDTO.getName().equalsIgnoreCase(COLUMN_CRAEAED_DATE)) {
            value = DateTimeUtils.sqlTimestamp();
        }
        
        if (value != null) {
        	paramMap.put(columnDTO.getName(), value);
        }

        return value;
    }

    private Map<Long, List<Object>> getSeqValueListMap(TableDTO tableDTO, List<Map<String, Object>> mapList) {
        Map<Long, Integer> seqCountMap = new HashMap<Long, Integer>();
        setSeqCountMap(seqCountMap, tableDTO, mapList);
        return getSeqValueListMap(seqCountMap);
    }

    private Map<Long, List<Object>> getSeqValueListMap(TableDTO tableDTO, Map<String, Object> paramMap) {
        Map<Long, Integer> seqCountMap = new HashMap<Long, Integer>();
        setSeqCountMap(seqCountMap, tableDTO, paramMap);
        return getSeqValueListMap(seqCountMap);
    }

    private Map<Long, List<Object>> getSeqValueListMap(Map<Long, Integer> seqCountMap) {
        Map<Long, List<Object>> seqValueListMap = new HashMap<Long, List<Object>>();
        for (Map.Entry<Long, Integer> entry : seqCountMap.entrySet()) {
            Long seqId = entry.getKey();
            Integer count = entry.getValue();
            List<Object> valueList = sequenceService.getNextValues(seqId, count);
            seqValueListMap.put(seqId, valueList);
        }

        return seqValueListMap;
    }

    private void setSeqCountMap(Map<Long, Integer> seqCountMap, TableDTO tableDTO, List<Map<String, Object>> mapList) {
        for (Map<String, Object> paramMap : mapList) {
            setSeqCountMap(seqCountMap, tableDTO, paramMap);
        }
    }

    private void setSeqCountMap(Map<Long, Integer> seqCountMap, TableDTO tableDTO, Map<String, Object> paramMap) {
        tableDTO.getColumnDTOList().stream().forEach(t -> {
            if (t.getSeqId() != null && !paramMap.containsKey(t.getName())) {
                Long key = t.getSeqId();
                if (seqCountMap.containsKey(key)) {
                    seqCountMap.put(key, seqCountMap.get(key) + 1);
                } else {
                    seqCountMap.put(key, 1);
                }
            }
        });
    }
}
