package com.gen.desafio.api.domain.dto.res;


import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gen.desafio.api.domain.dto.BaseDTO;

@JsonInclude(Include.NON_EMPTY)
public class UserResponseDTO extends BaseDTO {

	private long userId;
	private String firstname;
	private String lastname;
	private String username;
	private Integer age;
	private String gender;
	private String profileImage;
	private Integer rankingPoints;
	private Integer rankingPosition;
	private String clientName;
	private ClientResponseDTO clientOwner;
	private String companySector;
	private SettingsResponseDTO relatedSettings;
	//private List<UserBetResponseDTO> relatedUserBets = new ArrayList<UserBetResponseDTO>();
	private List<String> roles = new ArrayList<String>();
	private boolean agreeTutorial;
	private List<String> tutorialImages = new ArrayList<String>();
	
	
	public UserResponseDTO() {}
	

	public long getUserId() {
		return userId;
	}
	public void setUserId(long id) {
		this.userId = id;
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public Integer getRankingPoints() {
		return rankingPoints;
	}
	public void setRankingPosition(Integer rankingPosition) {
		this.rankingPosition = rankingPosition;
	}
	public Integer getRankingPosition() {
		return rankingPosition;
	}
	public void setRankingPoints(Integer rankingPoints) {
		this.rankingPoints = rankingPoints;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public ClientResponseDTO getClientOwner() {
		return clientOwner;
	}
	public void setClientOwner(ClientResponseDTO clientOwner) {
		this.clientOwner = clientOwner;
	}
	public String getCompanySector() {
		return companySector;
	}
	public void setCompanySector(String companySector) {
		this.companySector = companySector;
	}
	public SettingsResponseDTO getRelatedSettings() {
		return relatedSettings;
	}
	public void setRelatedSettings(SettingsResponseDTO relatedSettings) {
		this.relatedSettings = relatedSettings;
	}
//	public List<UserBetResponseDTO> getRelatedUserBets() {
//		return relatedUserBets;
//	}
//	public void setRelatedUserBets(List<UserBetResponseDTO> relatedUserBets) {
//		this.relatedUserBets = relatedUserBets;
//	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isAgreeTutorial() {
		return agreeTutorial;
	}

	public void setAgreeTutorial(boolean agreeTutorial) {
		this.agreeTutorial = agreeTutorial;
	}

	public List<String> getTutorialImages() {
		return tutorialImages;
	}

	public void setTutorialImages(List<String> tutorialImages) {
		this.tutorialImages = tutorialImages;
	}
	
	
	
}
