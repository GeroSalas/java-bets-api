package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;
import com.gen.desafio.api.utils.APIConstants;


public class EditClientReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Debe ingresar Nombre de la Empresa cliente.")
    @Size(max=50)
	private String companyName;
		
	
	@NotEmpty(message = "Debe ingresar Email de contacto de la Empresa.")
	@Pattern(regexp = APIConstants.PATTERN_EMAIL, message = "Email inválido.")
    @Size(max=50)
	private String companyEmail;
	
	private String logoImage;
	
	private String backImage;
	
	// Client color settings
	private String style1;
	private String style2;
	private String style3;
	private String style4;
	private String style5;
	
	
	
	public EditClientReqDTO() {}

	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public String getStyle1() {
		return style1;
	}

	public void setStyle1(String style1) {
		this.style1 = style1;
	}

	public String getStyle2() {
		return style2;
	}

	public void setStyle2(String style2) {
		this.style2 = style2;
	}

	public String getStyle3() {
		return style3;
	}

	public void setStyle3(String style3) {
		this.style3 = style3;
	}

	public String getStyle4() {
		return style4;
	}

	public void setStyle4(String style4) {
		this.style4 = style4;
	}

	public String getStyle5() {
		return style5;
	}

	public void setStyle5(String style5) {
		this.style5 = style5;
	}

	
}
