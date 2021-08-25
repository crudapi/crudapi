package cn.crudapi.core.util;

import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ResourceUtils;

public final class CsvUtils {
	 public static void readCsv(String file) throws Exception {
		Reader reader = null;
		try {
			String path;
			path = ResourceUtils.getFile("classpath:" + file).getPath();
			
			reader = new FileReader(path);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
			for (CSVRecord csvRecord : records) {
			    System.out.println(csvRecord.get("autoIncrement") + csvRecord.get("caption"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
    }
}
