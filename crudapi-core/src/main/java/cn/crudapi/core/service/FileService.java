package cn.crudapi.core.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	Map<String, Object> upload(MultipartFile file);
	
	void delete(String fileName);

	/**
	 * 分块上传文件
	 * @param md5
	 * @param size
	 * @param chunks
	 * @param chunk
	 * @param file
	 * @throws IOException
	 */
	Map<String, Object> uploadWithBlock(String name, String md5, Long size, Integer chunks, Integer chunk, MultipartFile file);

	/**
	 * 检查Md5判断文件是否已上传
	 * @param md5
	 * @return
	 */
	boolean checkMd5(String md5);

	File getFile(String fileName);

	String getRandomFileName(String fileName);

	String getFullUrl(String fileName);

	String getUploadFullPath(String fileName);

	String getUrl(String fileName);

	String getUploadFullPathByUrl(String url);

	String getOssFilePath();

	String getOssUploadPath();
}
