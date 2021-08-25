package cn.crudapi.security.service;

import java.util.List;

import cn.crudapi.security.dto.GrantedAuthorityDTO;
import cn.crudapi.security.dto.ResourceDTO;

public interface ResourceService {

    List<ResourceDTO> listAll();

    List<GrantedAuthorityDTO> getAuthorities(List<ResourceDTO> resourceList);
}
