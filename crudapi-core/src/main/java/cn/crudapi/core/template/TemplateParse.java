package cn.crudapi.core.template;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.exception.BusinessException;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class TemplateParse {
	private static final Logger log = LoggerFactory.getLogger(TemplateParse.class);
	
	private static final String TEMPLTE_BASE = "cn/crudapi/core/jdbc";
	
	private String readInputStream(ClassPathResource resource) {
		String value =  null;
		try {
	    	log.info(resource.getPath());
	    	
	    	InputStream inStream = resource.getInputStream();
        	byte[] bytes = new byte[0];
        	bytes = new byte[inStream.available()];
        	inStream.read(bytes);
        	value = new String(bytes);
		} catch (FileNotFoundException e) {
			log.warn(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}
         
        return value;   
	}
	
	private String getTemplate(String database, String templateName) {
		ClassPathResource resource = null;
		String templateValue = null;
        try {
        	resource = new ClassPathResource(TEMPLTE_BASE + "/" + database + "/" + templateName);
        	log.info(resource.getPath());
        	
        	templateValue = readInputStream(resource);
        	
        	if (database != "sql" && templateValue == null) {
        		resource = new ClassPathResource(TEMPLTE_BASE + "/sql/" + templateName);
            	log.info("retry read:" + resource.getPath());
            	
            	templateValue = readInputStream(resource);
        	}
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
        }
        
        return templateValue;
    }
	
	public String processTemplateToString(String database, String templateName, String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		return processTemplateToString(database, templateName, map);
	}
	
	public String processTemplateToString(String database, String templateName, Object dataModel) {
		String str = null;
        StringWriter stringWriter = new StringWriter();
        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_31);
            String templateValue = getTemplate(database, templateName);
        	if (templateValue == null) {
        		return str;
        	}
            
    		Template template = new Template(templateName, templateValue, config);
    		template.process(dataModel, stringWriter);

    		str = stringWriter.getBuffer().toString().trim();
            log.info(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
        }
        
        return str;
    }
	
}
