package com.gen.desafio.api.domain.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="bet_user_points")
@IdClass(UserBetId.class)  // Composed PK (user_id X bet_id)
public class UserBet implements Serializable {
	
	@Id
    @ManyToOne
    @JoinColumn(name = "bet_id")
	private Bet relatedBet;
	
	@Id
    @ManyToOne
    @JoinColumn(name = "user_id")
	private User relatedUser;
	
	@Column(name = "bet_team_result", nullable = true, length = 20)
    private String teamResult;
	
	@Column(name = "bet_score_result", nullable = true, length = 20)
    private String ScoreResult;
	
	@Column(name = "bet_group_result", nullable = true, length = 20)
    private String groupResult;
	
	
	@Column(name = "points", nullable = true)
    private Integer points;
	
	@NotNull
	@Column(name = "notified_on_timeline", nullable = false)
    private boolean isNotified;

	
	/* Default Constructor */
	public UserBet(){ }
	
	
	/* Getters & Setters */
	public Bet getRelatedBet() {
		return relatedBet;
	}
	
	public void setRelatedBet(Bet relatedBet) {
		this.relatedBet = relatedBet;
	}

	public User getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}
	

	public String getTeamResult() {
		return teamResult;
	}

	public void setTeamResult(String teamResult) {
		this.teamResult = teamResult;
	}

	public String getScoreResult() {
		return ScoreResult;
	}

	public void setScoreResult(String scoreResult) {
		ScoreResult = scoreResult;
	}

	public String getGroupResult() {
		return groupResult;
	}

	public void setGroupResult(String groupResult) {
		this.groupResult = groupResult;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
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
