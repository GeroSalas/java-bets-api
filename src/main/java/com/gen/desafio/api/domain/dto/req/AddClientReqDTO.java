package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;
import com.gen.desafio.api.utils.APIConstants;


public class AddClientReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese Nombre de la Empresa cliente.")
    @Size(max=50)
	private String companyName;
		
	@NotEmpty(message = "Por favor ingrese Email de contacto de la Empresa.")
	@Pattern(regexp = APIConstants.PATTERN_EMAIL, message = "Invalid email format.")
    @Size(min=6, max=50)
	private String companyEmail;
	
	@NotEmpty(message = "Por favor ingrese Nombre del Administrador de la Cuenta Cliente.")
	private String adminFirstname;
	
	@NotEmpty(message = "Por favor ingrese Apellido del Administrador de la Cuenta Cliente.")
	private String adminLastname;
	
	@NotEmpty(message = "Por favor ingrese Nombre de Usuario (email) del Administrador de la Cuenta Cliente.")
	@Pattern(regexp = APIConstants.PATTERN_EMAIL, message = "Formato Incorrecto")
    @Size(min=6, max=50)
	private String adminUsername;
	
	@NotEmpty(message = "Por favor ingrese una imagen como Logo de la Empresa.")
	private String logoImage;
	
	@NotEmpty(message = "Por favor ingrese una imagen de fondo para identificar la Empresa.")
	private String backImage;
	
	// Client color settings
	private String style1;
	private String style2;
	private String style3;
	private String style4;
	private String style5;
	
	private String tutoImage1;
	private String tutoImage2;
	private String tutoImage3;
	private String tutoImage4;
	
	
	public AddClientReqDTO() {}

	
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

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminFirstname() {
		return adminFirstname;
	}

	public void setAdminFirstname(String adminFirstname) {
		this.adminFirstname = adminFirstname;
	}

	public String getAdminLastname() {
		return adminLastname;
	}

	public void setAdminLastname(String adminLastname) {
		this.adminLastname = adminLastname;
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


	public String getTutoImage1() {
		return tutoImage1;
	}

	public void setTutoImage1(String tutoImage1) {
		this.tutoImage1 = tutoImage1;
	}

	public String getTutoImage2() {
		return tutoImage2;
	}

	public void setTutoImage2(String tutoImage2) {
		this.tutoImage2 = tutoImage2;
	}

	public String getTutoImage3() {
		return tutoImage3;
	}

	public void setTutoImage3(String tutoImage3) {
		this.tutoImage3 = tutoImage3;
	}

	public String getTutoImage4() {
		return tutoImage4;
	}

	public void setTutoImage4(String tutoImage4) {
		this.tutoImage4 = tutoImage4;
	}
	
	
}
