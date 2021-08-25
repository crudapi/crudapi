package cn.crudapi.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.crudapi.core.service.TableService;
import cn.crudapi.security.dto.GrantedAuthorityDTO;
import cn.crudapi.security.dto.ResourceDTO;
import cn.crudapi.security.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private TableService tableService;
	
    @Override
    public List<GrantedAuthorityDTO> getAuthorities(List<ResourceDTO> resourceList) {
        return resourceList.stream()
                .map(role -> new GrantedAuthorityDTO(role.getCode()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ResourceDTO> listAll() {
		List<ResourceDTO> resourceDTOs = new  ArrayList<ResourceDTO>();
		
    	List<Map<String, Object>> mapList = tableService.list("resource", null, null, null, null, null, 0, 99999, null);
    	
    	
    	for (Map<String, Object> resourceMap : mapList) {
			ResourceDTO resourceDTO = new ResourceDTO();
			resourceDTO.setId(Long.parseLong(resourceMap.get("id").toString()));
			resourceDTO.setCode(Objects.toString(resourceMap.get("code")));
			resourceDTO.setName(Objects.toString(resourceMap.get("name")));
			resourceDTO.setUrl(Objects.toString(resourceMap.get("url")));
			resourceDTO.setAction(Objects.toString(resourceMap.get("action")));
			resourceDTO.setRemark(Objects.toString(resourceMap.get("remark")));
			
			resourceDTOs.add(resourceDTO);
		}
		
    	return resourceDTOs;
    }

}
