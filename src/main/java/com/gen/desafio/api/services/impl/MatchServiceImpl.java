package com.gen.desafio.api.services.impl;


import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gen.desafio.api.dal.MatchDAO;
import com.gen.desafio.api.dal.TeamDAO;
import com.gen.desafio.api.domain.dto.req.MatchReqDTO;
import com.gen.desafio.api.domain.exception.ApplicationException;
import com.gen.desafio.api.domain.exception.DuplicateKeyException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.Match;
import com.gen.desafio.api.domain.model.Team;
import com.gen.desafio.api.services.MatchService;
import com.gen.desafio.api.utils.PushBotsAPIService;
import com.gen.desafio.api.utils.mappers.MatchMapper;



@Service
@Transactional
public class MatchServiceImpl implements MatchService {
	
	private static final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);
 
    @Autowired 
    private MatchDAO matchRepository;
    
    @Autowired 
    private TeamDAO teamRepository;

    
    /**
     * GET ONE - FIND
     */
    @Override
	public Match find(long id) {
    	Match match = matchRepository.findOne(id);
		
    	if (match == null) {
			throw new RecordNotFoundException("Match info not found for ID: " + id);
		}

    	return match;
	}
    

	/**
     * PUT - UPDATE
     */
    @Override
	public boolean updateMatch(MatchReqDTO matchReq) {
		// Business Rules
    	boolean isSaved = false;
    	Match updated = null;
    	
		boolean exists = matchRepository.exists(matchReq.getMatchId()); 
		if(!exists) {
				log.info("Could not update invalid Match");
				throw new RecordNotFoundException("Partido inválido");
		}
		else{
			updated = matchRepository.findOne(matchReq.getMatchId());
			if(updated.isClosed()){
				log.info("Could not update already closed Match");
				throw new DuplicateKeyException("Partido duplicado. Ya fue cerrado y registrado.");
			}else{
				try {
		    		if(matchReq.getIsClosed()){
		    			// PARTIDO CERRADO CON RESULTADO FINAL EXACTO
		    			if(matchReq.getLocalScoreET()==null || matchReq.getVisitorScoreET()==null){
		    				// [Hack Error]
		    				// Because of Issue HHH-9007 on Hibernate 4x we need to force NULL values to be 0 to support SQL failures
		    				matchReq.setLocalScoreET(0);
		    				matchReq.setVisitorScoreET(0);
		    				matchReq.setLocalScorePEN(0);
		    				matchReq.setVisitorScorePEN(0);
		    			}
		    			
		    			matchReq.setResult(matchReq.getLocalScore()+"-"+matchReq.getVisitorScore());
		    			if(matchReq.getLocalScore() > matchReq.getVisitorScore()) matchReq.setWinner("LOCAL");
		    			if(matchReq.getLocalScore() < matchReq.getVisitorScore()) matchReq.setWinner("VISITANTE");
		    			if(matchReq.getLocalScore() == matchReq.getVisitorScore()) matchReq.setWinner("EMPATE");
		    			
		    			if(matchReq.getIsExtraTime()){
		    				matchReq.setResultExtra(matchReq.getLocalScore()+matchReq.getLocalScoreET()+"-"+matchReq.getVisitorScore()+matchReq.getVisitorScoreET());
		    				if(matchReq.getIsPenalties()){
			    				matchReq.setResultExtra(matchReq.getLocalScore()+matchReq.getLocalScoreET()+" ("+matchReq.getLocalScorePEN()+")"+" - "+"("+matchReq.getVisitorScorePEN()+") "+matchReq.getVisitorScore()+matchReq.getVisitorScoreET());
			    			}
		    			}
		    			else{
		    				matchReq.setResultExtra("-");
		    			}
		    			matchRepository.SP_Update_Match_Scores(matchReq.getMatchId(), 
										       matchReq.getLocalTeamId(), matchReq.getVisitorTeamId(), 
										       matchReq.getLocalScore(), matchReq.getVisitorScore(), 
										       matchReq.getIsExtraTime(), matchReq.getLocalScoreET(), matchReq.getVisitorScoreET(), 
										       matchReq.getIsPenalties(), matchReq.getLocalScorePEN(), matchReq.getVisitorScorePEN(), 
										       matchReq.getResult(), matchReq.getResultExtra(), matchReq.getWinner().toUpperCase());
		    			log.debug("Stored Procedure executed successfully!");
		    			
		    			// SEND PUSHBOTS NOTIFICATIONS TO MOBILE USERS !!!
		    			//PushBotsAPIService.sendNotificationsToAll(MatchMapper.setPushNotificationMatchCloseMessage(updated, matchReq.getResult(), matchReq.getResultExtra()));
		    		}
		    		else{
		    			// PARTIDO NO CERRADO CON RIVALES AHORA DEFINIDOS
		    		   updated = matchRepository.findOne(matchReq.getMatchId());
		    		   Team  local = teamRepository.findOne(matchReq.getLocalTeamId());
		    		   Team  visit = teamRepository.findOne(matchReq.getVisitorTeamId());
		    		    updated.setRelatedLocal(local);
		    		    updated.setRelatedVisitor(visit);
		    		}
		    		
		    		isSaved = true;
					
				} catch (ApplicationException ex) {
					log.error("Error - " + ex.getMessage());
					throw ex;
				}
			}
		}	
		
		return isSaved;
	}



	/**
     * GET ALL - READ
     */
    @Override
    //@Cacheable("fixture")
	public List<Match> listAllMatchs() {
		return matchRepository.findAll();
	}

    
    
	/**
     * GET ONE - FIND
     */
    @Override
	public List<Match> getMatchsByDate(Date d) {
		List<Match> matches = matchRepository.findByStartDate(d);
		
		if (matches.isEmpty()) {
			throw new RecordNotFoundException("Matches info not found for Date: " + d.toString());
		}
		
		return matches;
	}
	
	
}




