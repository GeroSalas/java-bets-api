package com.gen.desafio.api.domain.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;
import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="clients")
public class Client implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotEmpty
	@Column(name = "name", nullable = false, length = 50)
    private String name;
	
	@Email
	@Column(name = "email", nullable = false)
    private String email;
	
	@Column(name = "logo_image", nullable = true, length = 200)
    private String logoImage;
	
	@Column(name = "background_image", nullable = true, length = 200)
    private String backImage;
	
	@Column(name = "tutorial_image_1", nullable = true, length = 200)
    private String tutorialImage1;
	
	@Column(name = "tutorial_image_2", nullable = true, length = 200)
    private String tutorialImage2;
	
	@Column(name = "tutorial_image_3", nullable = true, length = 200)
    private String tutorialImage3;
	
	@Column(name = "tutorial_image_4", nullable = true, length = 200)
    private String tutorialImage4;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "relatedClient", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<User> users = new ArrayList<User>();

	
	/* Default Constructor */
	public Client(){ }
	
	public Client(Long id){
		this.id = id;
	}
	
	public Client(String name, String email){
		this.name = name;
		this.email = email;
	}
	
	
	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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
	
	public String getTutorialImage1() {
		return tutorialImage1;
	}

	public void setTutorialImage1(String tutorialImage1) {
		this.tutorialImage1 = tutorialImage1;
	}

	public String getTutorialImage2() {
		return tutorialImage2;
	}

	public void setTutorialImage2(String tutorialImage2) {
		this.tutorialImage2 = tutorialImage2;
	}

	public String getTutorialImage3() {
		return tutorialImage3;
	}

	public void setTutorialImage3(String tutorialImage3) {
		this.tutorialImage3 = tutorialImage3;
	}

	public String getTutorialImage4() {
		return tutorialImage4;
	}

	public void setTutorialImage4(String tutorialImage4) {
		this.tutorialImage4 = tutorialImage4;
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

        Client client = (Client) o;

        if (!name.equals(client.name)) {
            return false;
        }

        return true;
    }
	
	@Override
    public int hashCode() {
        return name.hashCode();
    }
	
	
	public User getAdminUser(){
		User admin = null;
		for(User u : this.getUsers()){
			if(u.hasRole(AuthoritiesConstants.ADMIN)){
				admin = u;
				break;
			}
		}
		
		return admin;
	}
	
}
