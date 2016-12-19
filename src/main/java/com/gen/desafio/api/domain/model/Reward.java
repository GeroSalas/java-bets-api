package com.gen.desafio.api.domain.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="rewards")
public class Reward implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotEmpty
	@Column(name = "description", nullable = false, length = 80)
    private String description;
	
	@Column(name = "winner_position", nullable = false)
    private int winnerPosition;
	
	@Column(name = "custom_award_name", nullable = true, length = 80)
    private String customAwardName;
	

	
	/* Default Constructor */
	public Reward(){ }
	
	
	
	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWinnerPosition() {
		return winnerPosition;
	}

	public void setWinnerPosition(int winnerPosition) {
		this.winnerPosition = winnerPosition;
	}

	public String getCustomAwardName() {
		return customAwardName;
	}

	public void setCustomAwardName(String customAwardName) {
		this.customAwardName = customAwardName;
	}



	/*******************
     *  BEAN METHODS   *
     *******************/
		
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
