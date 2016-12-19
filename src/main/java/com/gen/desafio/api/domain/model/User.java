package com.gen.desafio.api.domain.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.GsonBuilder;


 
/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="d_users")
@JsonInclude(Include.NON_NULL)
public class User implements Serializable, UserDetails {
	    
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private Long id;
		
		@NotEmpty
		@Column(name = "firstname", nullable = false, length = 50)
	    private String firstname;
		
		@NotEmpty
		@Column(name = "lastname", nullable = false, length = 50)
	    private String lastname;
	
		@NotEmpty
		@Column(name = "username", unique = true, nullable = false, length = 50)
	    private String username;
	    
	    @NotEmpty
	    @Size(min=6, max=100)
		@Column(name = "password", nullable = false, length = 100)
	    private String password;
	    
	    @Column(name = "enabled", nullable = false)
	    private byte enabled;
	    
		@Column(name = "pb_mobile_token", nullable = true, length = 100)
	    private String pbToken;
		
		@Column(name = "pb_platform", nullable = true)
	    private Integer pbPlatform;
	    
	    @Column(name = "on_play", nullable = false)
	    private boolean onPlay;
	    
		@Column(name = "gender", nullable = true, length = 1)
	    private String gender;
	    
		@Column(name = "age", nullable = true)
	    private Integer age;
	    
		@Column(name = "ranking_points", nullable = true)
	    private Integer points;
		
		@Column(name = "profile_image", nullable = true, length = 200)
	    private String image;
		
	    @ManyToOne(cascade=CascadeType.MERGE)
	    @JoinColumn(name = "client_id", nullable=true)
	    private Client relatedClient;
	    
	    @ManyToOne
	    @JoinColumn(name = "sector_id", nullable=true)
	    private Sector relatedSector;
	    
	    @ManyToOne
	    @JoinColumn(name = "reward_id", nullable=true)
	    private Reward relatedReward;
	    
	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relatedUser", fetch = FetchType.EAGER, orphanRemoval = true)
	    private List<AuthorityRole> roles = new ArrayList<AuthorityRole>();
	    
	    @OneToOne(cascade=CascadeType.MERGE) 
	    @JoinColumn(name="user_preferences_id", nullable=true)
	    private UserPreferences relatedSettings;  
	    
//	    @OneToMany(mappedBy = "relatedUser", cascade = CascadeType.ALL)
//	    private List<UserBet> relatedUserBets = new ArrayList<UserBet>();
	    
	    @Column(name = "agree_tutorial", nullable = false)
	    private boolean agreeTutorial;
	    
	    /* Default Constructor */
	    public User(){  }
	    
	    public User(String username, String password, String gender, Integer age) {
			this.username = username;
			this.password = password;
			this.enabled = 1;          // Active User
			this.gender = gender;
			this.age = age;
		}


		/* Getters & Setters */ 
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		@Override
	    public String getUsername() {
			return username;
		}
		
		@Required
		public void setUsername(String username) {
			this.username = username;
		}	
		
		@Override
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
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

		public byte getEnabled() {
			return enabled;
		}
		public void setEnabled(byte enabled) {
			this.enabled = enabled;
		}
		
		public String getPbToken() {
			return pbToken;
		}
		public void setPbToken(String pbToken) {
			this.pbToken = pbToken;
		}
		
		public Integer getPbPlatform() {
			return pbPlatform;
		}

		public void setPbPlatform(Integer pbPlatform) {
			this.pbPlatform = pbPlatform;
		}

		public boolean isOnPlay() {
			return onPlay;
		}
		public void setOnPlay(boolean plays) {
			this.onPlay = plays;
		}

		public Client getRelatedClient() {
			return relatedClient;
		}

		public void setRelatedClient(Client relatedClient) {
			this.relatedClient = relatedClient;
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
		
		public Integer getPoints() {
			return points;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public void setPoints(Integer points) {
			this.points = points;
		}

		public Sector getRelatedSector() {
			return relatedSector;
		}

		public void setRelatedSector(Sector relatedSector) {
			this.relatedSector = relatedSector;
		}

		public List<AuthorityRole> getRoles() {
			return roles;
		}

		public void setRoles(List<AuthorityRole> roles) {
			this.roles = roles;
		}
		
		public UserPreferences getRelatedSettings() {
			return relatedSettings;
		}

		public void setRelatedSettings(UserPreferences relatedSettings) {
			this.relatedSettings = relatedSettings;
		}

		public Reward getRelatedReward() {
			return relatedReward;
		}

		public void setRelatedReward(Reward relatedReward) {
			this.relatedReward = relatedReward;
		}
		
		public boolean isAgreeTutorial() {
			return agreeTutorial;
		}

		public void setAgreeTutorial(boolean agreeTutorial) {
			this.agreeTutorial = agreeTutorial;
		}
		
//		public List<UserBet> getRelatedUserBets() {
//			return relatedUserBets;
//		}
//
//		public void setRelatedUserBets(List<UserBet> relatedUserBets) {
//			this.relatedUserBets = relatedUserBets;
//		}
		
		public String getFullName(){
			return firstname+" "+lastname;
		}
		

		public boolean hasRole(String role){
			for(AuthorityRole r : this.getRoles()){
				if(r.getRolename().equals(role)) return true;
			}
			return false;
		}


		
		/*******************
	     *  BEAN METHODS   *
	     *******************/
		public String toGson(){
			String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
			return gson.toString();
		}
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	        User user = (User) o;

	        if (!username.equals(user.username)) {
	            return false;
	        }

	        return true;
	    }		

		
		@Override
		@JsonIgnore
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return roles;
		}

		@Override
		@JsonIgnore
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		@JsonIgnore
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		@JsonIgnore
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		@JsonIgnore
		public boolean isEnabled() {
			if(enabled == 1) return true;
			else return false;
		}


}
