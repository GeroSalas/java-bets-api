package com.gen.desafio.api.domain.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gen.desafio.api.domain.dto.BaseDTO;

@JsonInclude(Include.NON_EMPTY)
public class StyleDTO extends BaseDTO {

	@JsonProperty("background-color")
	private String backgroundColor;
	
	@JsonProperty("color")
	private String textColor;
	
	public StyleDTO() {}

	
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	
}
