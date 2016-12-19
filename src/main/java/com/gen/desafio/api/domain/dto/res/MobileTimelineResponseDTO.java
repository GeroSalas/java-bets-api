package com.gen.desafio.api.domain.dto.res;


import java.util.List;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class MobileTimelineResponseDTO extends BaseDTO {

	private List<PostNotificationResponseDTO> postNotifications;
	private List<UserBetNotificationResponseDTO> betNotifications;
	private List<PostResponseDTO> recentPosts;
	
	
	public MobileTimelineResponseDTO() {
		super();
	}


	public List<PostResponseDTO> getRecentPosts() {
		return recentPosts;
	}

	public void setRecentPosts(List<PostResponseDTO> recentPosts) {
		this.recentPosts = recentPosts;
	}

	public List<UserBetNotificationResponseDTO> getBetNotifications() {
		return betNotifications;
	}

	public void setBetNotifications(List<UserBetNotificationResponseDTO> betNotifications) {
		this.betNotifications = betNotifications;
	}

	public List<PostNotificationResponseDTO> getPostNotifications() {
		return postNotifications;
	}

	public void setPostNotifications(List<PostNotificationResponseDTO> postNotifications) {
		this.postNotifications = postNotifications;
	}
	
	
}
