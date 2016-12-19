package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.List;

import com.gen.desafio.api.domain.dto.req.BetResultReqDTO;
import com.gen.desafio.api.domain.dto.res.UserBetNotificationResponseDTO;
import com.gen.desafio.api.domain.dto.res.UserBetResponseDTO;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.model.UserBet;


public class UserBetMapper extends ObjectMapper {
	
	
	public static List<UserBetNotificationResponseDTO> buildCustomUserBetNotificationDTO(List<UserBet> userBets){
		List<UserBetNotificationResponseDTO> dtos = new ArrayList<UserBetNotificationResponseDTO>();
    	for(UserBet bu : userBets){
    		boolean flagToNotify = false;
    		UserBetNotificationResponseDTO dto = new UserBetNotificationResponseDTO();
    		 dto.setId(bu.getRelatedBet().getId());
    		 dto.setPoints(bu.getPoints());
    		 if(bu.getRelatedBet().getRelatedMatch()!=null){
    			 if(bu.getRelatedBet().getRelatedMatch().isClosed()){
    				 dto.setResult(bu.getRelatedBet().getRelatedMatch().getRelatedLocal().getName() +" "+ bu.getRelatedBet().getRelatedMatch().getResult() +" "+bu.getRelatedBet().getRelatedMatch().getRelatedVisitor().getName());
    				 // Ejemplo:  'Argentina 2 - 0 Panamá'
    				 flagToNotify = true;
    			 }
    		 }
    		 else{
    			if(bu.getRelatedBet().getGroupWinnerResult()!=null){
    				dto.setResult(bu.getRelatedBet().getGroupWinnerResult());
	       			 // Ejemplo: 'ALEMANIA' (Ganador Grupo 3)
	       			flagToNotify = true;
    			}
    	     }   		
    		 
    		 if(flagToNotify) dtos.add(dto);  // Sólo notificar ante partidos cerrados y resultados ya definidos!
    	}  	
    	
    	return dtos;
	}
	
	
	public static UserBetResponseDTO buildCustomUserBetDTO(UserBet userBet){
    		UserBetResponseDTO dto = new UserBetResponseDTO();
    		 dto.setBetType(userBet.getRelatedBet().getBetType());
    		 if(userBet.getRelatedBet().getBetType()==1){
    			 dto.setBetId(userBet.getRelatedBet().getId());
    			 dto.setRelatedId(userBet.getRelatedBet().getRelatedMatch().getId());
    			 dto.setRelatedObject("Resultado de Partido");
    			 dto.setScoreResult(userBet.getScoreResult());
    			 dto.setPoints((userBet.getPoints()!=null) ? userBet.getPoints() : 0);
    		 }
    		 if(userBet.getRelatedBet().getBetType()==2){
    			 dto.setBetId(userBet.getRelatedBet().getId());
    			 dto.setRelatedId(userBet.getRelatedBet().getRelatedGroup().longValue());
    			 dto.setRelatedObject("Ganador de Grupo");
    			 dto.setGroupWinnerResult(userBet.getGroupResult());
    			 dto.setPoints((userBet.getPoints()!=null) ? userBet.getPoints() : 0);
    		 } 		
    	
    	return dto;
	}
	
	
	public static List<UserBetResponseDTO> buildCustomUserBetsDTO(List<UserBet> userBets){
		List<UserBetResponseDTO> dtos = new ArrayList<UserBetResponseDTO>();
    	for(UserBet bu : userBets){
    		UserBetResponseDTO dto = buildCustomUserBetDTO(bu);
    		dtos.add(dto);		
    	}
    	
    	return dtos;
	}
	
	
	
	public static String setTeamWinnerByResult(BetResultReqDTO bu){
		String winner = null;
		if(bu.getLocalScore() > bu.getVisitorScore()){
			winner = "LOCAL";
		}
		if(bu.getLocalScore() < bu.getVisitorScore()){
			winner = "VISITANTE";
		}
		if(bu.getLocalScore() == bu.getVisitorScore()){
			winner = "EMPATE";
		}
		
		return winner;
	}
	
	
	public static boolean validateBetDTO(BetResultReqDTO bu, int bt){
		boolean isValid = false;
		
		if(bt==1){
			if(!bu.getBetType().equals(1)){
				throw new BadRequestException("Tipo de Apuesta incorrecta.");	
			}
			else if(bu.getMatchId()==null){
				throw new BadRequestException("Partido inválido para apostar.");
			}
			else if(bu.getLocalScore()==null){
				throw new BadRequestException("Debe especificar un resultado a equipo local.");
			}
			else if(bu.getVisitorScore()==null){
				throw new BadRequestException("Debe especificar un resultado a equipo visitante.");
			}
			else{
				isValid = true;
			}
		}
		else{
			if(!bu.getBetType().equals(2)){
				throw new BadRequestException("Tipo de Apuesta incorrecta.");	
			}
			else if(bu.getGroupWinners().size()!=4){
				// agregar validacion extra para corrobar nombres de equipos validos
				throw new BadRequestException("Debe especificar los 4 Equipos ganadores de cada grupo obligatoriamente.");
			}
			else{
				isValid = true;
			}
		}
		
		return isValid;
	}
	
	
}
