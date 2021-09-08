package cn.crudapi.core.service;

import java.io.File;
import java.util.List;

public interface MetadataService {
	void importData(File file);

	String getExportFile(String name, List<Long> ids);
}
