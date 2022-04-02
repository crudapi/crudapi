package cn.crudapi.core.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
		
			Map<String, Object> seqMap = crudService.getForUpdate(SEQUENCE_TABLE_NAME, sequenceId);
					
			Boolean currentTime = Boolean.valueOf(String.valueOf(seqMap.get("currentTime")));
			SequenceTypeEnum sequenceType = SequenceTypeEnum.valueOf(String.valueOf(seqMap.get("sequenceType")));
			Object fromatObj = seqMap.get("format");
			String format = (fromatObj != null ? fromatObj.toString() : null);

			if (sequenceType == SequenceTypeEnum.GUID) {
				for (int count = 0; count < size; ++count) {
					UUID uuid = UUID.randomUUID();
					Object retValue = uuid.toString().replace("-", "");
					retValueList.add(retValue);
				}
			} else {
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
					
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("nextValue", nextValue);

					crudService.patch(SEQUENCE_TABLE_NAME, sequenceId, dataMap);
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
