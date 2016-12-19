package com.gen.desafio.api.domain.dto.res;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class ErrorResponseDTO extends BaseDTO {

	private String error;
	
	public ErrorResponseDTO() {
		super();
	}

	public ErrorResponseDTO(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
}
