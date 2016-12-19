package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class CreateSupportTicketReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese un Título.")
	@Size(max=60)
	private String title;
	
	@NotEmpty(message = "Por favor ingrese un texto de contenido como descripción del Ticket.")
	@Size(max=255)
	private String description;
	
	
	public CreateSupportTicketReqDTO() {}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
