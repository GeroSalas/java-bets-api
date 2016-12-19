package com.gen.desafio.api.domain.dto.res;


import com.gen.desafio.api.domain.dto.BaseDTO;


public class UserBatchResponseDTO extends BaseDTO {

	private Long userId;
	private String username;
	private String result;
	private String errorMessage;	
	
	public UserBatchResponseDTO() {}

	public UserBatchResponseDTO(Long userId, String username, String result, String errorMessage) {
		this.userId = userId;
		this.username = username;
		this.result = result;
		this.errorMessage = errorMessage;
	}

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
