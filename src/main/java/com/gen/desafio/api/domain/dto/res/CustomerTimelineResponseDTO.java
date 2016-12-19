package com.gen.desafio.api.domain.dto.res;


import java.util.List;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class CustomerTimelineResponseDTO extends BaseDTO {

	private List<PostNotificationResponseDTO> postNotifications;
	private List<PostResponseDTO> recentPosts;
	
	
	public CustomerTimelineResponseDTO() {
		super();
	}


	public List<PostResponseDTO> getRecentPosts() {
		return recentPosts;
	}

	public void setRecentPosts(List<PostResponseDTO> recentPosts) {
		this.recentPosts = recentPosts;
	}

	public List<PostNotificationResponseDTO> getPostNotifications() {
		return postNotifications;
	}

	public void setPostNotifications(List<PostNotificationResponseDTO> postNotifications) {
		this.postNotifications = postNotifications;
	}
	
	
}
