package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.List;
import com.gen.desafio.api.domain.dto.res.MatchResponseDTO;
import com.gen.desafio.api.domain.dto.res.TeamResponseDTO;
import com.gen.desafio.api.domain.model.Match;


public class MatchMapper extends ObjectMapper {
	
	public static MatchResponseDTO buildCustomMatchDTOResponse(Match match){
    	MatchResponseDTO dto = new MatchResponseDTO();
    	 dto.setMatchId(match.getId());
    	 //dto.setScheduledDate(DateUtils.format(match.getStartDate()));
    	 dto.setScheduledDate(match.getStartDate().toInstant().toString());
    	 dto.setStageDescription(match.getDescription());
    	 if(match.getRelatedLocal()!=null && match.getRelatedVisitor()!=null){
    		 dto.setLocal(new TeamResponseDTO(match.getRelatedLocal().getId(), match.getRelatedLocal().getName(), match.getRelatedLocal().getFlag(), match.getRelatedLocal().getShield(), match.getRelatedLocal().getGroup(), match.getRelatedLocal().getPoints()));
        	 dto.setVisitor(new TeamResponseDTO(match.getRelatedVisitor().getId(), match.getRelatedVisitor().getName(), match.getRelatedVisitor().getFlag(), match.getRelatedVisitor().getShield(), match.getRelatedVisitor().getGroup(), match.getRelatedVisitor().getPoints()));
    	 }
    	 else{
    		 // UNDEFINED 
    		 dto.setLocal(new TeamResponseDTO("NO DEFINIDO"));
    	     dto.setVisitor(new TeamResponseDTO("NO DEFINIDO"));
    	 }
    	 dto.setPlayoff(match.isPlayOff());
    	 dto.setLocalScore(match.getLocalScore());
    	 dto.setVisitorScore(match.getVisitorScore());
    	 dto.setHasExtraTime(match.isExtraTime());
    	 dto.setLocalScoreET(match.getLocalScoreET());
    	 dto.setVisitorScoreET(match.getVisitorScoreET());
    	 dto.setHasPenalties(match.isPenalties());
    	 dto.setLocalScorePEN(match.getLocalScorePEN());
    	 dto.setVisitorScorePEN(match.getVisitorScorePEN());
    	 dto.setResult(match.getResult());
    	 dto.setIsClosed(match.isClosed());
    	 
    	return dto;
    }
    
    
    public static List<MatchResponseDTO> buildCustomMatchesDTOResponse(List<Match> matches){
    	List<MatchResponseDTO> dtos = new ArrayList<MatchResponseDTO>();
    	for(Match m : matches){
    	 MatchResponseDTO dto = buildCustomMatchDTOResponse(m);
       	 dtos.add(dto);
    	}
    	
    	return dtos;
    }
    
    
    public static List<MatchResponseDTO> buildNextMatchesDTOResponse(List<Match> matchs){
    	List<MatchResponseDTO> dtos = new ArrayList<MatchResponseDTO>();
    	for(Match m : matchs){
    	 MatchResponseDTO dto = new MatchResponseDTO();
    	 dto.setMatchId(m.getId());
    	 //dto.setScheduledDate(DateUtils.format(m.getStartDate()));
    	 dto.setScheduledDate(m.getStartDate().toInstant().toString());
    	 dto.setStageDescription(m.getDescription());
    	 if(m.getRelatedLocal()!=null && m.getRelatedVisitor()!=null){
    		 dto.setLocal(new TeamResponseDTO(m.getRelatedLocal().getId(), m.getRelatedLocal().getName(), m.getRelatedLocal().getFlag(), m.getRelatedLocal().getShield(), m.getRelatedLocal().getGroup(), m.getRelatedLocal().getPoints()));
        	 dto.setVisitor(new TeamResponseDTO(m.getRelatedVisitor().getId(), m.getRelatedVisitor().getName(), m.getRelatedVisitor().getFlag(), m.getRelatedVisitor().getShield(), m.getRelatedVisitor().getGroup(), m.getRelatedVisitor().getPoints()));
    	 }
    	 else{
    		 // RIVALES NO DEFINIDOS AUN 
    		 dto.setLocal(new TeamResponseDTO("NO DEFINIDO"));
    	     dto.setVisitor(new TeamResponseDTO("NO DEFINIDO"));
    	 }
    	 
       	 dtos.add(dto);
    	}
    	
    	return dtos;
    }
    
    
    public static String setPushNotificationMatchCloseMessage(Match m, String result90, String resultExtra){
    	StringBuilder message = null;
    	if(resultExtra.equals("-")){
    	    message = new StringBuilder("Ha finalizado ")
			            .append(m.getRelatedLocal().getName())
			            .append(" ")
			            .append(result90)
			            .append(" ")
			            .append(m.getRelatedVisitor().getName())
			            .append(". Revisa tus Puntos!.");
    	}
    	else{
    		message = new StringBuilder("Ha finalizado ")
            .append(m.getRelatedLocal().getName())
            .append(" ")
            .append(resultExtra)
            .append(" ")
            .append(m.getRelatedVisitor().getName())
            .append(". Revisa tus Puntos!.");
    	}
    	
    	return message.toString();
    }
    
   
}
