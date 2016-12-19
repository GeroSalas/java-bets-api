package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class MatchReqDTO extends BaseDTO {
	
	@NotNull(message = "ID de Partido es un campo obligatorio.")
	private Long matchId;
	
    @NotNull(message = "ID de Equipo Local es un campo obligatorio.")
	private Long localTeamId;
    
    @NotNull(message = "ID de Equipo Visitante es un campo obligatorio.")
	private Long visitorTeamId;
	
    private Integer localScore;
	
    private Integer visitorScore;
	
    private boolean isExtraTime;
	
    private Integer localScoreET;
	
    private Integer visitorScoreET;
	
    private boolean isPenalties;
	
    private Integer localScorePEN;
	
    private Integer visitorScorePEN;
	
    private String result;
    
    private String resultExtra;
    
    private String winner;

    @NotNull(message = "Es un campo obligatorio. Debe indicar si el partido ya fue terminado o no.")
    private Boolean isClosed;
    
    
    @AssertTrue(message="Si el partido no fue cerrado, debe indicar los IDs de los equipos rivales que desea definir.")
    private boolean isValidPendingMatch() {
        if(!isClosed && (localTeamId==null || visitorTeamId==null)) {
            return false;
        } else {
            return true;
        }
    }
    
    @AssertTrue(message="Si el partido tuvo prorroga, debe indicar bien su respectivo resultado en goles.")
    private boolean isValidExtraTimeResult() {
        if(isExtraTime && (localScoreET==null || visitorScoreET==null)) {
            return false;
        } else {
            return true;
        }
    }
    
    @AssertTrue(message="Si el partido tuvo penales, debe indicar bien su respectivo resultado en goles.")
    private boolean isValidPenaltiesResult() {
        if(isPenalties && (localScorePEN==null || visitorScorePEN==null)) {
            return false;
        } else {
            return true;
        }
    }
    
    
	public MatchReqDTO() {}


	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getLocalTeamId() {
		return localTeamId;
	}

	public void setLocalTeamId(Long localTeamId) {
		this.localTeamId = localTeamId;
	}

	public Long getVisitorTeamId() {
		return visitorTeamId;
	}

	public void setVisitorTeamId(Long visitorTeamId) {
		this.visitorTeamId = visitorTeamId;
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

	public boolean getIsExtraTime() {
		return isExtraTime;
	}

	public void setIsExtraTime(boolean isExtraTime) {
		this.isExtraTime = isExtraTime;
	}

	public Integer getLocalScoreET() {
		return localScoreET;
	}

	public void setLocalScoreET(Integer localScoreET) {
		this.localScoreET = localScoreET;
	}

	public Integer getVisitorScoreET() {
		return visitorScoreET;
	}

	public void setVisitorScoreET(Integer visitorScoreET) {
		this.visitorScoreET = visitorScoreET;
	}

	public boolean getIsPenalties() {
		return isPenalties;
	}

	public void setIsPenalties(boolean isPenalties) {
		this.isPenalties = isPenalties;
	}

	public Integer getLocalScorePEN() {
		return localScorePEN;
	}

	public void setLocalScorePEN(Integer localScorePEN) {
		this.localScorePEN = localScorePEN;
	}

	public Integer getVisitorScorePEN() {
		return visitorScorePEN;
	}

	public void setVisitorScorePEN(Integer visitorScorePEN) {
		this.visitorScorePEN = visitorScorePEN;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResultExtra() {
		return resultExtra;
	}

	public void setResultExtra(String resultExtra) {
		this.resultExtra = resultExtra;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public String getWinner() {
		return winner;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	
	
}
