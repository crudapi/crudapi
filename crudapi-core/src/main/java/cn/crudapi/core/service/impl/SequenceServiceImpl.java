package cn.crudapi.core.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.crudapi.core.util.DateTimeUtils;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.SequenceDTO;
import cn.crudapi.core.enumeration.SequenceTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.service.SequenceMetadataService;
import cn.crudapi.core.service.SequenceService;

@Service
public class SequenceServiceImpl implements SequenceService {
	private static final Logger log = LoggerFactory.getLogger(SequenceServiceImpl.class);
	
	private static final String SEQUENCE_TABLE_NAME = "ca_meta_sequence";
	
    @Autowired
    private SequenceMetadataService sequenceMetadataService;

    @Autowired
    private CrudService crudService;
  
	@Override
	public Object getNextValue(String sequenceName) {
		SequenceDTO sequenceDTO = sequenceMetadataService.get(sequenceName);
		Long sequenceId = sequenceDTO.getId();

		return getNextValue(sequenceId);
	}

	@Override
	public Object getNextValue(Long sequenceId) {
		List<Object> retValueList = new ArrayList<Object>();

		try {
			retValueList = getNextValueById(sequenceId, 1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}

		return retValueList.get(0);
	}

	@Override
	public List<Object> getNextValues(Long sequenceId, int size) {
		List<Object> retValueList = new ArrayList<Object>();

		try {
			retValueList = getNextValueById(sequenceId, size);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}

		return retValueList;
	}

	private List<Object> getNextValueById(Long sequenceId, int size) throws SQLException {
		JdbcTemplate jdbcTemplate = crudService.getJdbcTemplate();
		Connection conn = null;
		List<Object> retValueList = new ArrayList<Object>();
		String dataBaseTableName = SEQUENCE_TABLE_NAME;

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
				
			StringBuilder sb = new StringBuilder("SELECT * FROM `");
			sb.append(dataBaseTableName);
			sb.append("` WHERE `id` = ? FOR UPDATE");

			Map<String, Object> seqMap = jdbcTemplate.queryForMap(sb.toString(), sequenceId);
			Boolean currentTime = Boolean.valueOf(String.valueOf(seqMap.get("currentTime")));
			SequenceTypeEnum sequenceType = SequenceTypeEnum.valueOf(String.valueOf(seqMap.get("sequenceType")));
			Object fromatObj = seqMap.get("format");
			String format = (fromatObj != null ? fromatObj.toString() : null);

			if (currentTime) {
				DateTime now = DateTimeUtils.now();
				Object retValue = null;
				if (sequenceType == SequenceTypeEnum.LONG) {
					retValue = now.getMillis();
				} else {
					retValue = now.toString(format);
				}

				for (int count = 0; count < size; ++count) {
					retValueList.add(retValue);
				}
			} else {
				Long nextValue = Long.parseLong(String.valueOf(seqMap.get("nextValue"))) ;
				Long incrementBy = Long.parseLong(String.valueOf(seqMap.get("incrementBy"))) ;

				for (int count = 0; count < size; ++count) {
					Object retValue = null;
					if (sequenceType == SequenceTypeEnum.LONG) {
						retValue = nextValue;
					} else {
						retValue = String.format(format, nextValue);
					}

					retValueList.add(retValue);
					nextValue += incrementBy;
				}

				sb = new StringBuilder("UPDATE `");
				sb.append(dataBaseTableName);
				sb.append("` SET `");
				sb.append("nextValue");
				sb.append("` = ? WHERE `id` = ?");

				int row = jdbcTemplate.update(sb.toString(), nextValue, sequenceId);

				log.info("row = " +  row);
		        if (row == 0) {
		        	throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, sequenceId);
		        }
			}

			log.info("conn commit...");
			conn.commit();
		} catch (Exception e) {
			log.error(e.getMessage());
			conn.rollback();
		} finally {
			conn.setAutoCommit(true);
			conn.close();
		}

		return retValueList;
	}
}
