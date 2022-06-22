package cn.crudapi.security.service;

import java.util.List;

import cn.crudapi.core.dto.GrantedAuthorityDTO;
import cn.crudapi.core.dto.ResourceDTO;

public interface ResourceService {

    List<ResourceDTO> listAll();

    List<GrantedAuthorityDTO> getAuthorities(List<ResourceDTO> resourceList);
}
