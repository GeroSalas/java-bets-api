package com.gen.desafio.api.domain.dto.res;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gen.desafio.api.domain.dto.BaseDTO;


@JsonInclude(Include.NON_NULL)
public class ClientResponseDTO extends BaseDTO {

	private long clientId;
	private String name;
	private String email;
	private String logoImage;
	private String backImage;
	private String adminUsername;
	private String adminName;
	private int usersCount;
	private SettingsResponseDTO relatedSettings;
	//private List<UserResponseDTO> users = new ArrayList<UserResponseDTO>();
	
	
	public ClientResponseDTO() {}
	

	public ClientResponseDTO(long id, String name, String email) {
		this.clientId = id;
		this.name = name;
		this.email = email;
	}


	public long getClientId() {
		return clientId;
	}
	public void setClientId(long id) {
		this.clientId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public List<UserResponseDTO> getUsers() {
//		return users;
//	}
//	public void setUsers(List<UserResponseDTO> users) {
//		this.users = users;
//	}
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

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	public int getUsersCount() {
		return usersCount;
	}
	
	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public SettingsResponseDTO getRelatedSettings() {
		return relatedSettings;
	}

	public void setRelatedSettings(SettingsResponseDTO relatedSettings) {
		this.relatedSettings = relatedSettings;
	}
	
}
