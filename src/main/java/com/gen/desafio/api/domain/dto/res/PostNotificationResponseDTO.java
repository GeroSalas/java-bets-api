package com.gen.desafio.api.domain.dto.res;


import java.util.ArrayList;
import java.util.List;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class PostNotificationResponseDTO extends BaseDTO {

	private String action;
	private List<String> reactUsers = new ArrayList<>();
	private long relatedPost;
	
	
	public PostNotificationResponseDTO() { }


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getReactUsers() {
		return reactUsers;
	}

	public void setReactUsers(List<String> reactUser) {
		this.reactUsers = reactUser;
	}

	public long getRelatedPost() {
		return relatedPost;
	}

	public void setRelatedPost(long relatedPost) {
		this.relatedPost = relatedPost;
	}	
	
	
}
