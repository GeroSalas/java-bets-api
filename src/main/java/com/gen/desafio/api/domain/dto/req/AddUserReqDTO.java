package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class AddUserReqDTO extends BaseDTO {
	
	
	@NotEmpty(message = "Por favor ingrese un Nombre.")
	private String firstname;
	
	@NotEmpty(message = "Por favor ingrese un Apellido.")
	private String lastname;
	
	@NotEmpty(message = "Por favor ingrese un Nombre de Usuario (Email).")
    @Size(min=6, max=50)
	private String username;
	
	@NotNull(message = "Por favor ingrese la Edad del usuario.")
	private int age;
	
	@NotEmpty(message = "Por favor ingrese un el Género (M o F) del usuario.")
    @Size(max=1)
	private String gender;
	
	@NotNull(message = "Por favor ingrese a qué Sector pertenece el usuario dentro de la Empresa.")
	private Long companySector;
	
	
	
	public AddUserReqDTO() {
		super();
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Long getCompanySector() {
		return companySector;
	}
	public void setCompanySector(Long companySector) {
		this.companySector = companySector;
	}
	
}
