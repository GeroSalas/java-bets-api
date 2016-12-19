package com.gen.desafio.api.domain.dto.res;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class SettingsResponseDTO extends BaseDTO {

	private StyleDTO style1;
	private StyleDTO style2;
	private StyleDTO style3;
	private StyleDTO style4;
	private StyleDTO style5;
	
	
	public SettingsResponseDTO() {
		this.style1 = new StyleDTO();
		this.style2 = new StyleDTO();
		this.style3 = new StyleDTO();
		this.style4 = new StyleDTO();
		this.style5 = new StyleDTO();
		
	}


	public StyleDTO getStyle1() {
		return style1;
	}


	public void setStyle1(StyleDTO style1) {
		this.style1 = style1;
	}


	public StyleDTO getStyle2() {
		return style2;
	}


	public void setStyle2(StyleDTO style2) {
		this.style2 = style2;
	}


	public StyleDTO getStyle3() {
		return style3;
	}


	public void setStyle3(StyleDTO style3) {
		this.style3 = style3;
	}


	public StyleDTO getStyle4() {
		return style4;
	}


	public void setStyle4(StyleDTO style4) {
		this.style4 = style4;
	}


	public StyleDTO getStyle5() {
		return style5;
	}


	public void setStyle5(StyleDTO style5) {
		this.style5 = style5;
	}

	
}
