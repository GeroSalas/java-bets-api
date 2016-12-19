package com.gen.desafio.api.domain.dto.req;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class BetResultReqDTO extends BaseDTO {
	
	// Required usage only when Modify Bet
	private Long id;
	
	//@NotNull(message = "Por favor ingrese ID Partido a apostar.")
	private Long matchId;
	
	@NotNull(message = "Por favor ingrese Tipo de Apuesta (1 o 2).")
	@Min(1)
	@Max(2)
	private Integer betType;
	
	private Integer localScore;
	
	private Integer visitorScore;
	
	private String betResult;
	
	private List<String> groupWinners = new ArrayList<String>();
	
	
	@AssertTrue(message="Por favor ingresa valores válidos para la Apuesta que deseas realizar.")
    private boolean isValidBet() {
        if(betType.equals(1) && localScore!=null && visitorScore!=null) {
            return true;
        }
        else if(betType.equals(2) && !groupWinners.isEmpty()){
        	return true;
        }
        else {
            return false;
        }
    }
	
	
	public BetResultReqDTO() {}


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Integer getBetType() {
		return betType;
	}

	public void setBetType(Integer betType) {
		this.betType = betType;
	}

	public Integer getLocalScore() {
		return localScore;
	}

	public void setLocalScore(Integer localScore) {
		this.localScore = localScore;
	}

	public Integer getVisitorScore() {
		return visitorScore;
	}

	public void setVisitorScore(Integer visitorScore) {
		this.visitorScore = visitorScore;
	}

	public String getBetResult() {
		return betResult;
	}

	public void setBetResult(String betResult) {
		this.betResult = betResult;
	}

	public List<String> getGroupWinners() {
		return groupWinners;
	}

	public void setGroupWinners(List<String> groupWinners) {
		this.groupWinners = groupWinners;
	}

}
