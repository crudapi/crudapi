package cn.crudapi.core.dto;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cn.crudapi.core.dto.AuditDTO;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO extends AuditDTO  implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String openId;
	
	private String realname;
	
	private String username;
	
	private String mobile;
	
	private String email;
	
	private String password;
	
	private String cleartextPwd;
	
	private String token;
	
	private boolean enabled;
	
	private boolean accountNonExpired;

	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private List<RoleDTO> roles;
	
	private List<ResourceDTO> resources;
	
	private List<GrantedAuthorityDTO> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCleartextPwd() {
		return cleartextPwd;
	}

	public void setCleartextPwd(String cleartextPwd) {
		this.cleartextPwd = cleartextPwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public List<ResourceDTO> getResources() {
		return resources;
	}

	public void setResources(List<ResourceDTO> resources) {
		this.resources = resources;
	}

	public List<GrantedAuthorityDTO> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthorityDTO> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", openId=" + openId + ", realname=" + realname + ", username="
				+ username + ", mobile=" + mobile + ", email=" + email + ", password=" + password + ", cleartextPwd="
				+ cleartextPwd + ", token=" + token + ", enabled=" + enabled + ", accountNonExpired="
				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired="
				+ credentialsNonExpired + ", roles=" + roles + ", resources=" + resources + ", authorities="
				+ authorities + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
