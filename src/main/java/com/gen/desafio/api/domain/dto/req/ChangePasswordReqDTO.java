package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class ChangePasswordReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese Nombre de Usuario (email).")
	private String username;
	
	@NotEmpty(message = "Por favor ingrese la Contraseña actual.")
	private String currentPassword;
	
	@NotEmpty(message = "Por favor ingrese la Nueva Contraseña.")
	private String newPassword;
	
	
	@AssertTrue(message="La contraseña nueva debe ser diferente a la anterior.")
    private boolean isValidPassword() {
        if(currentPassword.equalsIgnoreCase(newPassword)) {
            return false;
        } else {
            return true;
        }
    }
	
	
	public ChangePasswordReqDTO() {}

	
	public ChangePasswordReqDTO(String username, String currentPassword, String newPassword) {
		this.username = username;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
