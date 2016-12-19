package com.gen.desafio.api.domain.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.QueryHint;
import javax.persistence.ParameterMode;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="matches")
@NamedStoredProcedureQuery(name="UpdateMatchAndRelatedCascades", 
						   procedureName="SP_Update_Match_Scores",
						   hints = { 
		                             @QueryHint( name = "hibernate.proc.param_null_passing.local_score_et_in",    value = "true" ),
		                             @QueryHint( name = "hibernate.proc.param_null_passing.visitor_score_et_in",  value = "true" ),
		                             @QueryHint( name = "hibernate.proc.param_null_passing.local_score_pen_in",   value = "true" ),
		                             @QueryHint( name = "hibernate.proc.param_null_passing.visitor_score_pen_in", value = "true" )
                                   },
					       parameters={
									   @StoredProcedureParameter( name="match_id_in", type=Long.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="local_team_id_in", type=Long.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="visitor_team_id_in", type=Long.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="local_score_in", type=Integer.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="visitor_score_in", type=Integer.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="is_extratime_in", type=Boolean.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="local_score_et_in", type=Integer.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="visitor_score_et_in", type=Integer.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="is_penalties_in", type=Boolean.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="local_score_pen_in", type=Integer.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="visitor_score_pen_in", type=Integer.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="result_text_in", type=String.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="result_extra_text_in", type=String.class, mode=ParameterMode.IN),
									   @StoredProcedureParameter( name="winner_in", type=String.class, mode=ParameterMode.IN)
					                  }
                           )
public class Match implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "local_team_id", nullable=true)
    private Team relatedLocal;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "visitor_team_id", nullable=true)
    private Team relatedVisitor;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.NONE)
    @Column(name = "start_date", nullable = false)  // columnDefinition="DATETIME" -> @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
	
	@NotNull
	@Column(name = "playoff", nullable = false)
    private boolean isPlayOff;
	
	@NotEmpty
	@Column(name = "description", nullable = false)
    private String description;
	
	@Column(name = "local_score", nullable = true)
    private Integer localScore;
	
	@Column(name = "visitor_score", nullable = true)
    private Integer visitorScore;
	
	@NotNull
	@Column(name = "has_extra_time", nullable = false)
    private boolean isExtraTime;
	
	@Column(name = "local_score_et", nullable = true)
    private Integer localScoreET;
	
	@Column(name = "visitor_score_et", nullable = true)
    private Integer visitorScoreET;
	
	@NotNull
	@Column(name = "has_penalties", nullable = false)
    private boolean isPenalties;
	
	@Column(name = "local_score_pen", nullable = true)
    private Integer localScorePEN;
	
	@Column(name = "visitor_score_pen", nullable = true)
    private Integer visitorScorePEN;
	
	@NotEmpty
	@Column(name = "result_text", nullable = false)
    private String result;
	
	@Column(name = "result_extra_text", nullable = true)
    private String resultExtra;
	
	@Column(name = "winner", nullable = true)
    private String winner;
	
	@NotNull
	@Column(name = "is_closed", nullable = false)
    private boolean isClosed;

	
	/* Default Constructor */
	public Match(){ }
	
	
	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Team getRelatedLocal() {
		return relatedLocal;
	}

	public void setRelatedLocal(Team relatedLocal) {
		this.relatedLocal = relatedLocal;
	}

	public Team getRelatedVisitor() {
		return relatedVisitor;
	}

	public void setRelatedVisitor(Team relatedVisitor) {
		this.relatedVisitor = relatedVisitor;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isPlayOff() {
		return isPlayOff;
	}

	public void setPlayOff(boolean isPlayOff) {
		this.isPlayOff = isPlayOff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isExtraTime() {
		return isExtraTime;
	}

	public void setExtraTime(boolean isExtraTime) {
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

	public boolean isPenalties() {
		return isPenalties;
	}

	public void setPenalties(boolean isPenalties) {
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
	
	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}


	
	/*******************
     *  BEAN METHODS   *
     *******************/
		
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
