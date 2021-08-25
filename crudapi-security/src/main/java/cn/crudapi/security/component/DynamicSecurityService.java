package cn.crudapi.security.component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Component;

import cn.crudapi.security.dto.ResourceDTO;
import cn.crudapi.security.service.ResourceService;

@Component
public class DynamicSecurityService {
    @Autowired
    private ResourceService resourceService;

	public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        
        List<ResourceDTO> resourceList = resourceService.listAll();
        for (ResourceDTO resource : resourceList) {
            map.put(resource.getUrl() + "_" + resource.getAction(), new org.springframework.security.access.SecurityConfig(resource.getCode()));
        }
        
        return map;
    }
}
