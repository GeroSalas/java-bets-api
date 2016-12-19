package com.gen.desafio.api.domain.dto.res;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class TeamResponseDTO extends BaseDTO {

	private long teamId;
	private String name;
	private String flag;
	private String shield;
	private int group;
	private int points;
	
	
	public TeamResponseDTO() {}
	
	
	public TeamResponseDTO(long id, String name, String flag, String shield, int group, int points) {
		this.teamId = id;
		this.name = name;
		this.flag = flag;
		this.shield = shield;
		this.group = group;
		this.points = points;
	}
	
	public TeamResponseDTO(String name) {
		this.name = name;
	}


	public long getTeamId() {
		return teamId;
	}
	public String getName() {
		return name;
	}
	public String getShield() {
		return shield;
	}
	public int getGroup() {
		return group;
	}
	public int getPoints() {
		return points;
	}
	public String getFlag() {
		return flag;
	}
	
}
