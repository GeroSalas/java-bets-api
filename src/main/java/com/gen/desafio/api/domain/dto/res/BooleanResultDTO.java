package com.gen.desafio.api.domain.dto.res;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class BooleanResultDTO extends BaseDTO {

	private boolean result;
	
	public BooleanResultDTO() {
		super();
	}

	public BooleanResultDTO(boolean result) {
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
