package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class EditUserReqDTO extends BaseDTO {
	
	
	private String firstname;
	
	private String lastname;
	
	private String username;
	
	private Integer age;
	
    @Size(max=1)
	private String gender;
	
	private Long companySector;
	
	
	public EditUserReqDTO() {}
	
	
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Long getCompanySector() {
		return companySector;
	}
	public void setCompanySector(Long companySector) {
		this.companySector = companySector;
	}
	
}
