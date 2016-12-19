package com.gen.desafio.api.domain.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="teams")
public class Team implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotEmpty
	@Column(name = "name", nullable = false, length = 50)
    private String name;
	
	@NotEmpty
	@Column(name = "flag_img", nullable = false, length = 200)
    private String flag;
	
	@NotEmpty
	@Column(name = "shield_img", nullable = false, length = 200)
    private String shield;
	
	@NotNull
	@Column(name = "team_group", nullable = false)
    private int group;
	
	@NotNull
	@Column(name = "points_group", nullable = false)
    private int points;

	
	/* Default Constructor */
	public Team(){ }
	
	
	public Team(Long id) {
		this.id = id;
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
	
	public String getShield() {
		return shield;
	}

	public void setShield(String shield) {
		this.shield = shield;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	

	/*******************
     *  BEAN METHODS   *
     *******************/
		
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
