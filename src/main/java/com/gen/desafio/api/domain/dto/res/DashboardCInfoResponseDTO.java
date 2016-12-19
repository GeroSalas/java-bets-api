package com.gen.desafio.api.domain.dto.res;


import java.util.ArrayList;
import java.util.List;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class DashboardCInfoResponseDTO extends BaseDTO {

	private int usersCount;
	private int activeUsersCount;
	private List<UserResponseDTO> rankingTopUsers = new ArrayList<>();
	private int postsCount;
	private List<PostResponseDTO> lastPosts = new ArrayList<>(); 
	private int activesPreviousMatch;
	private int activesNextMatch;
	private int activesAllMatchs;
	private int porcentageActivesPreviousMatch;
	private int porcentageActivesNextMatch;
	private int porcentageActivesAllMatches;
    private List<MatchResponseDTO> nextMatches = new ArrayList<>(); 
	
	
	public DashboardCInfoResponseDTO() {}


	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public int getActiveUsersCount() {
		return activeUsersCount;
	}

	public void setActiveUsersCount(int activeUsersCount) {
		this.activeUsersCount = activeUsersCount;
	}

	public int getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(int postsCount) {
		this.postsCount = postsCount;
	}

	public List<UserResponseDTO> getRankingTopUsers() {
		return rankingTopUsers;
	}

	public void setRankingTopUsers(List<UserResponseDTO> rankingTopUsers) {
		this.rankingTopUsers = rankingTopUsers;
	}

	public int getActivesPreviousMatch() {
		return activesPreviousMatch;
	}

	public void setActivesPreviousMatch(int activesPreviousMatch) {
		this.activesPreviousMatch = activesPreviousMatch;
	}

	public int getActivesNextMatch() {
		return activesNextMatch;
	}

	public void setActivesNextMatch(int activesNextMatch) {
		this.activesNextMatch = activesNextMatch;
	}

	public int getActivesAllMatchs() {
		return activesAllMatchs;
	}

	public void setActivesAllMatchs(int activesAllMatchs) {
		this.activesAllMatchs = activesAllMatchs;
	}

	public int getPorcentageActivesPreviousMatch() {
		return porcentageActivesPreviousMatch;
	}

	public void setPorcentageActivesPreviousMatch(int porcentageActivesPreviousMatch) {
		this.porcentageActivesPreviousMatch = porcentageActivesPreviousMatch;
	}

	public int getPorcentageActivesNextMatch() {
		return porcentageActivesNextMatch;
	}

	public void setPorcentageActivesNextMatch(int porcentageActivesNextMatch) {
		this.porcentageActivesNextMatch = porcentageActivesNextMatch;
	}

	public int getPorcentageActivesAllMatches() {
		return porcentageActivesAllMatches;
	}

	public void setPorcentageActivesAllMatches(int porcentageActivesAllMatchs) {
		this.porcentageActivesAllMatches = porcentageActivesAllMatchs;
	}

	public List<MatchResponseDTO> getNextMatches() {
		return nextMatches;
	}
	
	public void setNextMatches(List<MatchResponseDTO> nextMatches) {
		this.nextMatches = nextMatches;
	}

	public List<PostResponseDTO> getLastPosts() {
		return lastPosts;
	}

	public void setLastPosts(List<PostResponseDTO> lastPosts) {
		this.lastPosts = lastPosts;
	}
		
	
}
