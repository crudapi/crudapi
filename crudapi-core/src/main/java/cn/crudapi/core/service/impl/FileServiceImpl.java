package cn.crudapi.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

	@Override
    public String getOssFilePath() {
		return ossFilePath;
	}

	@Override
	public String getOssUploadPath() {
		return ossUploadPath;
	}

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
		
		// 3. 获取并验证原始文件名（过滤路径穿越字符）
	    String originalFilename = file.getOriginalFilename();
	    if (originalFilename == null || originalFilename.trim().isEmpty()) {
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "文件名不合法！");
	    }
	    
	    // 过滤路径穿越字符
	    originalFilename = originalFilename.replaceAll("[/\\\\:;*?\"<>|]", "_");
	    
	    // 4. 服务端二次文件类型校验
	    String contentType = file.getContentType();
	    String extension = getFileExt(originalFilename);
	    
	    // 4.1 定义白名单（根据实际业务调整）
	    Set<String> allowedExtensions = new HashSet<>(Arrays.asList(
	        "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "txt"
	    ));
	    Set<String> allowedContentTypes = new HashSet<>(Arrays.asList(
	        "image/jpeg", "image/png", "image/gif", "application/pdf",
	        "application/msword", 
	        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
	        "application/vnd.ms-excel",
	        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
	        "text/plain"
	    ));
	    
	    if (!allowedExtensions.contains(extension.toLowerCase())) {
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, 
	            "不支持的文件类型！仅支持：" + allowedExtensions);
	    }
	    
	    if (!allowedContentTypes.contains(contentType)) {
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "文件MIME类型不合法！");
	    }
	    
	    // 5. 文件头魔数校验（防止伪造Content-Type）
	    validateFileMagicNumber(file);
		
	    long fileSize = file.getSize();
		String fileName = getRandomFileName(originalFilename);
		   
		// 7. 安全构建文件路径（防止路径穿越）
	    String basePath = ossFilePath + "/" + ossUploadPath;
	    if (basePath.contains("..")) {
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "路径不合法！");
	    }
	    
	    File dest = new File(basePath, fileName); // 使用双参数构造函数更安全
	    String canonicalPath;
	    String expectedBasePath;
		try {
			canonicalPath = dest.getCanonicalPath();
			expectedBasePath = new File(basePath).getCanonicalPath();

		    // 8. 二次校验：确保目标文件在预期目录内（防止路径穿越）
		    if (!canonicalPath.startsWith(expectedBasePath)) {
		        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "非法路径！");
		    }
		} catch (IOException e) {
			 throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "非法路径！");
		}
	    
	    // 9. 创建目录
	    if (!dest.getParentFile().exists()) {
	        dest.getParentFile().mkdirs();
	    }
		
	    Map<String, Object> fileInfo = new HashMap<String, Object>();
	    fileInfo.put("size", fileSize);
	    fileInfo.put("name", fileName);
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
				log.warn("删除失败，可能文件不存在！");
				//throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "删除失败，可能文件不存在！");
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
		    if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
		        dest.getParentFile().mkdirs();
		    }
			return dest;
		} catch (Exception e) {
	        e.printStackTrace();
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
	    }
	}
	
	
	@Override
	public String getRandomFileName(String fileName) {
		String ext = getFileExt(fileName);
		 
	    DateTime dt = DateTime.now();
	    DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd");
	    String subPath = dt.toString(f);
	    
	    DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd-HHmmssSSS");
	    String timePart = dt.toString(timeFormatter);
	    
	    int randomNum = (int)(Math.random() * 900000) + 100000;
	    
	    return subPath + "/" + timePart + randomNum + "." + ext;
	}
	
	@Override
	public String getExportRandomFileName(String fileName) {
		String ext = getFileExt(fileName);
		 
	    DateTime dt = DateTime.now();
	    DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd");
	    String subPath = dt.toString(f);
	    
	    DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd-HHmmssSSS");
	    String timePart = dt.toString(timeFormatter);
	    
	    int randomNum = (int)(Math.random() * 900000) + 100000;
	    
	    
	    return "exportTmp/" + subPath + "/" + timePart + randomNum + "." + ext;
	}
	
	@Override
	public String getUploadFullPath(String fileName) {
		return ossFilePath + "/" + ossUploadPath + "/" + fileName;
    }
	
	@Override
	public String getUploadFullPathByUrl(String url) {
		return ossFilePath + url;
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
	
	private void validateFileMagicNumber(MultipartFile file) {
	    try (InputStream is = file.getInputStream()) {
	        byte[] header = new byte[8];
	        int read = is.read(header);
	        if (read < 8) {
	            throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "文件内容不合法！");
	        }
	        
	        String hexHeader = bytesToHex(header);
	        
	        // 定义常见文件的魔数白名单
	        Map<String, String> magicNumbers = new HashMap<>();
	        magicNumbers.put("FFD8FF", "jpg/jpeg");  // JPEG
	        magicNumbers.put("89504E47", "png");      // PNG
	        magicNumbers.put("47494638", "gif");      // GIF
	        magicNumbers.put("25504446", "pdf");      // PDF
	        magicNumbers.put("D0CF11E0", "doc/xls");  // Office旧格式
	        
	        boolean isValid = false;
	        for (String magic : magicNumbers.keySet()) {
	            if (hexHeader.startsWith(magic)) {
	                isValid = true;
	                break;
	            }
	        }
	        
	        if (!isValid) {
	            throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, 
	                "文件内容与实际类型不符，可能存在伪装风险！");
	        }
	    } catch (BusinessException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "文件校验失败！");
	    }
	}

	/**
	 * 字节数组转十六进制字符串
	 */
	private String bytesToHex(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	        sb.append(String.format("%02X", b));
	    }
	    return sb.toString();
	}
}