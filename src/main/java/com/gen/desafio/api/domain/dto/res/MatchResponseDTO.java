package com.gen.desafio.api.domain.dto.res;


import com.gen.desafio.api.domain.dto.BaseDTO;


public class MatchResponseDTO extends BaseDTO {

	private long matchId;
	private TeamResponseDTO local;
	private TeamResponseDTO visitor;
	private String scheduledDate;
	private boolean isPlayoff;
	private String stageDescription;
	private Integer localScore;
	private Integer visitorScore;
	private boolean hasExtraTime;
	private Integer localScoreET;
	private Integer visitorScoreET;
	private boolean hasPenalties;
	private Integer localScorePEN;
	private Integer visitorScorePEN;
	private String result;
	private String resultExtra;
	private boolean isClosed;
	
	
	public MatchResponseDTO() {
		super();
	}
	

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long id) {
		this.matchId = id;
	}

	public TeamResponseDTO getLocal() {
		return local;
	}

	public void setLocal(TeamResponseDTO local) {
		this.local = local;
	}

	public TeamResponseDTO getVisitor() {
		return visitor;
	}

	public void setVisitor(TeamResponseDTO visitor) {
		this.visitor = visitor;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public boolean isPlayoff() {
		return isPlayoff;
	}

	public void setPlayoff(boolean isPlayoff) {
		this.isPlayoff = isPlayoff;
	}

	public String getStageDescription() {
		return stageDescription;
	}

	public void setStageDescription(String stageDescription) {
		this.stageDescription = stageDescription;
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

	public boolean isHasExtraTime() {
		return hasExtraTime;
	}

	public void setHasExtraTime(boolean hasExtraTime) {
		this.hasExtraTime = hasExtraTime;
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

	public boolean isHasPenalties() {
		return hasPenalties;
	}

	public void setHasPenalties(boolean hasPenalties) {
		this.hasPenalties = hasPenalties;
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

	public boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	

	
}
