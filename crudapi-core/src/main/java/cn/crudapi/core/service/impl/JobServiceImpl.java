package cn.crudapi.core.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.FileService;
import cn.crudapi.core.service.JobService;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;

@Service
public class JobServiceImpl implements JobService {
	  private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
	  
	  @Autowired
	  private FileService fileService;
	  
	  @Autowired
	  private TableService tableService;
	   
	  @Override
	  public void clean() {
		File folder = new File(fileService.getOssFilePath() + "/" + fileService.getOssUploadPath());
		deleteFolder(folder);
	  }
		
	  private void deleteFolder(File folder) {
		log.info("deleteFolder " + folder.getAbsolutePath());
		File[] files = folder.listFiles();
        for (File tmpFile :files){
            if (tmpFile.isDirectory()){//当前是文件夹 继续递归
            	log.info("skip directory");
            } else {//当前是文件
            	Instant creationTime = getCreationTime(tmpFile);
            	Instant now = Instant.now();
            	Instant yestdayNow = Instant.now().minusMillis(TimeUnit.HOURS.toMillis(24));
            	
            	log.info("creationTime = " + creationTime);
            	log.info("now = " + now);
            	log.info("yestdayNow = " + yestdayNow);
            	
            	if (creationTime.compareTo(yestdayNow) > 0) {
            		log.info("creationTime > yestdayNow, skip file which created in 24 hours!");
            		continue;
            	}
            	
            	String fileName = tmpFile.getName();
            	String url = fileService.getUrl(fileName);
            	Condition cond = ConditionUtils.toCondition("url", url);
            	Long count = tableService.count("file", null, null, cond);
            	if (count.longValue() == 0) {
            		log.info("fileName = " + fileName);
            		log.info("url = " + url + " is not in file table, delete it!");
            		FileSystemUtils.deleteRecursively(tmpFile);
            	}
            }
        }
	 }
	  
	 private Instant getCreationTime(File file) {
		BasicFileAttributes attr = null;
        try {
            Path path =  file.toPath();
            attr = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建时间
        Instant instant = attr.creationTime().toInstant();
        
        //log.info("instant = " + instant);
        
        return instant;
	}
}
