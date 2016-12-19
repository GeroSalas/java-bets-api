package com.gen.desafio.api.domain.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="bets")
public class Bet implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "bet_type", nullable = false)
    private int betType;
	
	@Column(name = "exact_score_result", nullable = true, length = 10)
    private String scoreResult;
	
	@Column(name = "exact_team_result", nullable = true, length = 20)
    private String teamResult;
	
	@Column(name = "exact_group_winner_result", nullable = true, length = 50)
    private String groupWinnerResult;

	@OneToMany(mappedBy = "relatedBet", cascade = CascadeType.MERGE)
    private List<UserBet> relatedUserBets = new ArrayList<UserBet>();
	
    @ManyToOne
    @JoinColumn(name = "related_match_id", nullable = true)
    private Match relatedMatch;
    
    @Column(name = "related_group_name", nullable = true)
    private Integer relatedGroup;
	
	
	/* Default Constructor */
	public Bet(){ }
	
	
	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public int getBetType() {
		return betType;
	}

	public void setBetType(int betType) {
		this.betType = betType;
	}

	public Match getRelatedMatch() {
		return relatedMatch;
	}

	public void setRelatedMatch(Match relatedMatch) {
		this.relatedMatch = relatedMatch;
	}

	public String getScoreResult() {
		return scoreResult;
	}

	public void setScoreResult(String scoreResult) {
		this.scoreResult = scoreResult;
	}

	public String getTeamResult() {
		return teamResult;
	}

	public void setTeamResult(String teamResult) {
		this.teamResult = teamResult;
	}

	public String getGroupWinnerResult() {
		return groupWinnerResult;
	}

	public void setGroupWinnerResult(String groupWinnerResult) {
		this.groupWinnerResult = groupWinnerResult;
	}
	
	public List<UserBet> getRelatedBets() {
		return relatedUserBets;
	}

	public void setRelatedBets(List<UserBet> relatedUserBets) {
		this.relatedUserBets = relatedUserBets;
	}

	public List<UserBet> getRelatedUserBets() {
		return relatedUserBets;
	}

	public void setRelatedUserBets(List<UserBet> relatedUserBets) {
		this.relatedUserBets = relatedUserBets;
	}

	public Integer getRelatedGroup() {
		return relatedGroup;
	}

	public void setRelatedGroup(Integer relatedGroup) {
		this.relatedGroup = relatedGroup;
	}

	

	/*******************
     *  BEAN METHODS   *
     *******************/			
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
