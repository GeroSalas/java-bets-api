package com.gen.desafio.api.domain.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="likes")
public class Like implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_user_liked_id")
 	private User relatedUser;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_post_id")
 	private Post relatedPost;
	
	@NotNull
	@Column(name = "notified_on_timeline", nullable = false)
    private boolean isNotified;

	
	/* Default Constructor */
	public Like(){ }

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}

	public Post getRelatedPost() {
		return relatedPost;
	}

	public void setRelatedPost(Post relatedPost) {
		this.relatedPost = relatedPost;
	}
	
	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}


	
	/*******************
     *  BEAN METHODS   *
     *******************/
			
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
