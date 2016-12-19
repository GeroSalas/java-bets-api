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
@Table(name="user_settings")
public class UserPreferences implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotEmpty
	@Column(name = "style_1_color", nullable = false, length = 40)
    private String style1Color;
	
	@NotEmpty
	@Column(name = "style_2_color", nullable = false, length = 40)
    private String style2Color;
	
	@NotEmpty
	@Column(name = "style_3_color", nullable = false, length = 40)
    private String style3Color;
	
	@NotEmpty
	@Column(name = "style_4_color", nullable = false, length = 40)
    private String style4Color;
	
	@NotEmpty
	@Column(name = "style_5_color", nullable = false, length = 40)
    private String style5Color;

	
	/* Default Constructor */
	public UserPreferences(){ }
	
	
	public UserPreferences(Long id) {
		this.id = id;
	}


	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	

	public String getStyle1Color() {
		return style1Color;
	}


	public void setStyle1Color(String style1Color) {
		this.style1Color = style1Color;
	}


	public String getStyle2Color() {
		return style2Color;
	}


	public void setStyle2Color(String style2Color) {
		this.style2Color = style2Color;
	}


	public String getStyle3Color() {
		return style3Color;
	}


	public void setStyle3Color(String style3Color) {
		this.style3Color = style3Color;
	}


	public String getStyle4Color() {
		return style4Color;
	}


	public void setStyle4Color(String style4Color) {
		this.style4Color = style4Color;
	}


	public String getStyle5Color() {
		return style5Color;
	}


	public void setStyle5Color(String style5Color) {
		this.style5Color = style5Color;
	}

	

	/*******************
     *  BEAN METHODS   *
     *******************/			
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
