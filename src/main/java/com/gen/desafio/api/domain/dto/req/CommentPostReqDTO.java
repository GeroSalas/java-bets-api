package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class CommentPostReqDTO extends BaseDTO {
	
	@NotEmpty(message = "Por favor ingrese un texto de contenido en su comentario.")
	@Size(max=200)
	private String textContent;
	
	// isComment ==> TRUE
	
	public CommentPostReqDTO() {}


	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}


	
}
