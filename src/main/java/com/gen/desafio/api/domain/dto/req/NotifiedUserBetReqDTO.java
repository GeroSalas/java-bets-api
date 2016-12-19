package com.gen.desafio.api.domain.dto.req;


import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class NotifiedUserBetReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese el ID de la apuesta de usuario.")
	private Long userBetId;
	
	@NotEmpty(message = "Por favor ingrese si ya fue notificada en su muro.")
	private Boolean isNotified;
	
	
	public NotifiedUserBetReqDTO() {}


	public Long getUserBetId() {
		return userBetId;
	}

	public void setUserBetId(Long userBetId) {
		this.userBetId = userBetId;
	}

	public Boolean getIsNotified() {
		return isNotified;
	}

	public void setIsNotified(Boolean isNotified) {
		this.isNotified = isNotified;
	}

	
}
