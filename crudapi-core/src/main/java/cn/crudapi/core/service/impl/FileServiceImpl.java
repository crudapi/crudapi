package cn.crudapi.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.service.FileService;
import cn.crudapi.core.util.FileUtils;


import static cn.crudapi.core.util.UploadUtils.*;

@Service
public class FileServiceImpl implements FileService {
	private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Value("${oss.file.path}")
	private String ossFilePath;
	
	@Value("${oss.upload.domain}")
	private String ossUploadDomain;
	
	@Value("${oss.upload.path}")
	private String ossUploadPath;
	
    @Autowired
    private TableService tableService;
    
    /**
     * 分块上传文件
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
	@Override
    public Map<String, Object> uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file)  {
	  log.info("name = {}, md5 = {}, size = {}, chunks = {}, chunk = {}", name, md5, size, chunks, chunk);	
		
	   Map<String, Object> fileInfo = new HashMap<String, Object>();
	   try {
		    String fileName = getFileName(md5, chunks) + "." + getFileExt(name);

		    fileInfo.put("size", size);
		    fileInfo.put("name", fileName);
		    fileInfo.put("fullUrl", getFullUrl(fileName));
		    fileInfo.put("url", getUrl(fileName));
		    fileInfo.put("isFinished", false);
		    
	        FileUtils.writeWithBlok(getUploadFullPath(fileName), size, file.getInputStream(), file.getSize(), chunks, chunk);
	        addChunk(md5,chunk);
	        if (isUploaded(md5)) {
	            removeKey(md5);
	            fileInfo.put("isFinished", true);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
	    }
	   
	    log.info(fileInfo.toString());
	    
	    return fileInfo;
    }

    /**
     * 检查Md5判断文件是否已上传
     * @param md5
     * @return
     */
	@Override
    public boolean checkMd5(String md5) {
        return true;
    }
    
	@Override
	public Map<String, Object> upload(MultipartFile file) {
		if (file.isEmpty()) {
			 throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "文件不能为空！");
		}
		
	    long fileSize = file.getSize();
		String originalFilename = file.getOriginalFilename();
		String fileName = getRandomFileName(originalFilename);
		   
	    Map<String, Object> fileInfo = new HashMap<String, Object>();
	    fileInfo.put("size", fileSize);
	    fileInfo.put("name", fileName);
	  
	    File dest = new File(ossFilePath + "/" + ossUploadPath + "/" + fileName);
	    if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
	        dest.getParentFile().mkdir();
	    }
	    
	    fileInfo.put("fullUrl", getFullUrl(fileName));
	    fileInfo.put("url", getUrl(fileName));
	    
	    try {
	    	file.transferTo(dest); //保存文件
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
	    }

	    log.info(fileInfo.toString());
	    
	    return fileInfo;
	}
	
	@Override
	public void delete(String fileName) {
		try {
			File dest = new File(ossFilePath + "/" + ossUploadPath + "/" + fileName);
			log.info("delete " +  dest.getAbsolutePath());
			boolean ret = FileSystemUtils.deleteRecursively(dest);
			if (!ret) {
				throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "删除失败，可能文件不存在！");
			}
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
	    }
	}
	
	@Override
	public File getFile(String fileName) {
		try {
			File dest = new File(ossFilePath + "/" + ossUploadPath + "/" + fileName);
			return dest;
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
	    }
	}
	
	@Override
	public void clean() {
		File folder = new File(ossFilePath + "/" + ossUploadPath);
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
            	String url = getUrl(fileName);
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
	
	@Override
	public String getRandomFileName(String fileName) {
		UUID uuid = UUID.randomUUID();
		String ext = getFileExt(fileName);
		return uuid.toString().replace("-", "") + "." + ext;
	}
	
	@Override
	public String getUploadFullPath(String fileName) {
		return ossFilePath + "/" + ossUploadPath + "/" + fileName;
    }
	
	@Override
	public String getFullUrl(String fileName) {
		return ossUploadDomain + "/" + ossUploadPath + "/" + fileName;
    }
	
	@Override
	public String getUrl(String fileName) {
		return "/" + ossUploadPath + "/" + fileName;
    }
	
	private String getFileExt(String filename) {
        int index = filename.lastIndexOf(".");
 
        if (index == -1) {
            return "";
        }
        
        String result = filename.substring(index + 1);
        return result;
    }
}