package com.gen.desafio.api.domain.dto.req;


import com.gen.desafio.api.domain.dto.BaseDTO;


public class UpdateClientProfileReqDTO extends BaseDTO {
	
	
	private String logoImage;   
	private String backImage; 
	
	
	public UpdateClientProfileReqDTO() {}


	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	
	
}
