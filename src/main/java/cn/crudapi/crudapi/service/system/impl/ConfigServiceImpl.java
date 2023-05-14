package cn.crudapi.crudapi.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProvider;
import cn.crudapi.crudapi.constant.ColumnConsts;
import cn.crudapi.crudapi.constant.SqlConsts;
import cn.crudapi.crudapi.constant.Status;
import cn.crudapi.crudapi.constant.SystemConsts;
import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.system.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
	private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private CrudService crudService;

	@Override
	public Map<String, Object> getDefault() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(ColumnConsts.IS_DELETED, false);
		paramsMap.put(ColumnConsts.STATUS, Status.ACTIVE);
		paramsMap.put(ColumnConsts.IS_DEFAULT, true);
		
		StringBuilder sb = new StringBuilder();
		sb.append(SqlConsts.SELECT)
			.append(" * ")
			.append(SqlConsts.FROM)
			.append(" ")
			.append(SystemConsts.TABLE_CONFIG)
			.append(" ")
			.append(SqlConsts.WHERE)
			.append(" ")
			.append(ColumnConsts.IS_DELETED)
			.append(" = :")
			.append(ColumnConsts.IS_DELETED)
			.append(" ")
			.append(SqlConsts.AND)
			.append(" ")
			.append(ColumnConsts.STATUS)
			.append(" = :")
			.append(ColumnConsts.STATUS)
			.append(" ")
			.append(SqlConsts.AND)
			.append(" ")
			.append(ColumnConsts.IS_DEFAULT)
			.append(" = :")
			.append(ColumnConsts.IS_DEFAULT);
		
		String sql = sb.toString();
		
		log.info(sql);
		
		List<Map<String, Object>> mapList = crudService.queryForList(sql, paramsMap);
	
		return mapList.get(0);
	}
}
