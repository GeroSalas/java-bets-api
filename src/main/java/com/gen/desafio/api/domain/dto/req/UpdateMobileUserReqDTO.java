package com.gen.desafio.api.domain.dto.req;


import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class UpdateMobileUserReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese una imagen de perfil válida para actualizar su perfil.")
	private String profileImage;   // https://desafioapp-test.s3-us-west-2.amazonaws.com/<imageName>.jpg
	
	
	public UpdateMobileUserReqDTO() {}



	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	
}
