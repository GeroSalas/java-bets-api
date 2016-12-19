package com.gen.desafio.api.domain.dto.req;


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class PostReqDTO extends BaseDTO {
		
	@NotEmpty(message = "Por favor ingrese un texto de contenido en su publicación.")
	@Size(max=255)
	private String textContent;
	
	private List<String> images = new ArrayList<String>();
	
	private List<Integer> sectors = new ArrayList<Integer>();  // Optional param for Admin Client
	
	// isComment ==> FALSE
	
	public PostReqDTO() {}


	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<Integer> getSectors() {
		return sectors;
	}

	public void setSectors(List<Integer> sectors) {
		this.sectors = sectors;
	}

	
}
