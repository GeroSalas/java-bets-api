package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class AddMobileTokenReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese API name.")
	private String api;
	
	@NotEmpty(message = "Por favor ingrese API Token.")
	private String pbToken;
	
	@NotNull(message = "Por favor ingrese Plataforma del dispositivo móvil (0 o 1).")
	private Integer platform;
	
	
	@AssertTrue(message="DesafioApp no soporta integracion con esa API.")
    private boolean isValidAPI() {
        if(api!=null && api.toUpperCase().equals("PUSHBOTS")) {
            return true;
        } else {
            return false;
        }
    }
	
	@AssertTrue(message="DesafioApp no soporta ese codigo de plataforma móvil.")
    private boolean isValidPlatform() {
        if(platform!=null && (platform.intValue()==0 || platform.intValue()==1)) {
            return true;
        } else {
            return false;
        }
    }
	
	
	public AddMobileTokenReqDTO() {}


	public String getPbToken() {
		return pbToken;
	}

	public void setPbToken(String pbToken) {
		this.pbToken = pbToken;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	
}
