package com.gen.desafio.api.domain.dto.req;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class LoginReqDTO extends BaseDTO {
	
	@Email
	@NotEmpty(message = "Por favor ingrese su Nombre de Usuario.")
	private String username;
	
	@NotEmpty(message = "Por favor ingrese su Contraseña.")
	private String password;	
	
	
//	@AssertTrue(message="Username y Contraseña no pueden ser iguales. Revise por favor.")
//    private boolean isValidCredentials() {
//        if ((username!=null) && (username.equalsIgnoreCase(password))) {
//            return false;
//        } else {
//            return true;
//        }
//    }
	
	
	public LoginReqDTO() {
		super();
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
