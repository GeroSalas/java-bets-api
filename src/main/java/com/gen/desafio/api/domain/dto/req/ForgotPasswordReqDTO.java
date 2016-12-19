package com.gen.desafio.api.domain.dto.req;


import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class ForgotPasswordReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese Nombre de Usuario (email) válido.")
	private String username;
	
	
	public ForgotPasswordReqDTO() {}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
}
