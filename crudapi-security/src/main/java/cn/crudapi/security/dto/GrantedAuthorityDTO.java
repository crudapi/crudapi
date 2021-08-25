package cn.crudapi.security.dto;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantedAuthorityDTO  implements GrantedAuthority  {
	private static final long serialVersionUID = 1;

	private String authority;
	
	public GrantedAuthorityDTO() {
	}
	
	public GrantedAuthorityDTO(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.authority = role;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authority);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrantedAuthorityDTO other = (GrantedAuthorityDTO) obj;
		return Objects.equals(authority, other.authority);
	}

	@Override
	public String toString() {
		return "GrantedAuthorityDTO [authority=" + authority + "]";
	}
}
