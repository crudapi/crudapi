package cn.crudapi.core.service;

import java.io.File;
import java.util.List;

import cn.crudapi.core.dto.MetadataDTO;

public interface MetadataService {
	void importData(File file);

	String getExportFile(String name, List<Long> ids);

	void importData(MetadataDTO metadataDTO);
}
