package com.gen.desafio.api.domain.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gen.desafio.api.domain.dto.BaseDTO;


@JsonInclude(Include.NON_NULL)
public class RoleResponseDTO extends BaseDTO {

	private String rolename;
	
	
	public RoleResponseDTO() {
		super();
	}

	public RoleResponseDTO(String rolename) {
		this.rolename = rolename;
	}


	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	
}
