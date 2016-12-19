package com.gen.desafio.api.domain.dto.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gen.desafio.api.domain.dto.BaseDTO;

@JsonInclude(Include.NON_NULL)
public class UserBetResponseDTO extends BaseDTO {

	private long betId; 
	private int betType;               //  1=SCORE MATCH WINNER  /  2=GROUP WINNER
	private String scoreResult;
	private String groupWinnerResult;
	private String relatedObject;      // 'Resultado de Partido'  /  'Ganador de Grupo'
	private Long relatedId;            // Match / Group
	private int points;
	
	
	public UserBetResponseDTO() {}

	
	public long getBetId() {
		return betId;
	}

	public void setBetId(long betId) {
		this.betId = betId;
	}

	public int getBetType() {
		return betType;
	}

	public void setBetType(int betType) {
		this.betType = betType;
	}

	public String getScoreResult() {
		return scoreResult;
	}

	public void setScoreResult(String scoreResult) {
		this.scoreResult = scoreResult;
	}

	public String getGroupWinnerResult() {
		return groupWinnerResult;
	}

	public void setGroupWinnerResult(String groupWinnerResult) {
		this.groupWinnerResult = groupWinnerResult;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public String getRelatedObject() {
		return relatedObject;
	}

	public void setRelatedObject(String relatedObject) {
		this.relatedObject = relatedObject;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}

	
	
}
