package com.gen.desafio.api.dal;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.desafio.api.domain.model.Match;

  // JPA REPOSITORY

@Repository
public interface MatchDAO extends JpaRepository<Match, Long>{
    
	// Custom DAO extra query methods added here...
	
	List<Match> findByStartDate(Date startdate);
	
	List<Match> findTop5ByIsClosedFalseAndStartDateAfterOrderByStartDateAsc(Date startdate);
	
	@Procedure(procedureName = "SP_Update_Match_Scores")
	void SP_Update_Match_Scores(@Param("match_id_in") Long closedMatchId, 
			      @Param("local_team_id_in") Long localTeamId,
			      @Param("visitor_team_id_in") Long visitorTeam,
			      @Param("local_score_in") Integer localScore,
			      @Param("visitor_score_in") Integer visitorScore,
			      @Param("is_extratime_in") Boolean isExtratime,
			      @Param("local_score_et_in") Integer localScoreET,
			      @Param("visitor_score_et_in") Integer visitorScoreET,
			      @Param("is_penalties_in") Boolean isPenalties,
			      @Param("local_score_pen_in") Integer localScorePEN,
			      @Param("visitor_score_pen_in") Integer visitorScorePEN,
			      @Param("result_text_in") String resultText,
			      @Param("result_extra_text_in") String resultExtraText,
			      @Param("winner_in") String resultWinner);
	
}
