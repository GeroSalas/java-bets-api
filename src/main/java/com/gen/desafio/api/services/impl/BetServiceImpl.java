package com.gen.desafio.api.services.impl;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gen.desafio.api.dal.BetDAO;
import com.gen.desafio.api.dal.MatchDAO;
import com.gen.desafio.api.dal.UserBetDAO;
import com.gen.desafio.api.domain.dto.req.BetResultReqDTO;
import com.gen.desafio.api.domain.exception.ApplicationException;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.exception.DuplicateKeyException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.Bet;
import com.gen.desafio.api.domain.model.Match;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.domain.model.UserBet;
import com.gen.desafio.api.services.BetService;
import com.gen.desafio.api.utils.DateUtils;
import com.gen.desafio.api.utils.mappers.UserBetMapper;


@Service
@Transactional
public class BetServiceImpl implements BetService {
	
	private static final Logger log = LoggerFactory.getLogger(BetServiceImpl.class);
 
	
    @Autowired 
    private BetDAO betRepository;
    
    @Autowired 
    private UserBetDAO userBetRepository;
    
    @Autowired 
    private MatchDAO matchRepository;

    
    
	@Override
	public UserBet find(long userId, long betId) {
		UserBet bu = userBetRepository.findUserBetById(userId, betId);
		
		if(bu == null) {
			throw new RecordNotFoundException("Apuesta de Usuario no encontrada.");  
		}
		
		return bu; 
	}
	

	@Override
	public boolean createBet(BetResultReqDTO userbet, User user) {
		// Business Rules
		boolean isSaved = false;
		
		UserBet bu = null;
		Bet parentBet = null;
		
		// Apuesta a Resultado de Partido
		if(userbet.getBetType()==1){
			bu = userBetRepository.findBetMatchByUser(user.getId(), userbet.getMatchId());
			parentBet = betRepository.findByRelatedMatch(userbet.getMatchId());
			Match m = matchRepository.findOne(userbet.getMatchId());
			if(!DateUtils.ableToBetMatch(m.getStartDate())){
				throw new BadRequestException("Horario excedido. Ya se cerraron las apuestas para este Partido.");
	    	}
			if(m.getRelatedLocal()==null || m.getRelatedVisitor()==null){
				throw new BadRequestException("No puede apostar en un Partido que no tiene rivales definidos todavía.");
	    	}
		}
		
		// Apuesta a Ganador de Grupos
		if(userbet.getBetType()==2){
			bu = userBetRepository.findBetGroupsByUser(user.getId());
			if(!DateUtils.ableToBetGroupWinner()){
				throw new BadRequestException("Horario excedido. Ya se cerraron las apuestas para ganadores de Grupo.");
	    	}
		}
	    	
		// Validar que Apuesta no sea repetida o duplicada
		if(bu != null){
			 throw new DuplicateKeyException("La apuesta de grupos ya fue realizada. Sólo debe modificarla.");  
		}
		else{
			try {
					 if(userbet.getBetType()==1){
						 UserBet buNew = new UserBet();
						 buNew.setRelatedUser(user);
						 buNew.setTeamResult(UserBetMapper.setTeamWinnerByResult(userbet)); 
						 buNew.setScoreResult(userbet.getLocalScore()+"-"+userbet.getVisitorScore());
						 parentBet.getRelatedUserBets().add(buNew);
						 buNew.setRelatedBet(parentBet);
						 
						 betRepository.save(parentBet);
						 isSaved = true;
						 log.info("Successfully saved UserBet!");
					 }
					 
					 if(userbet.getBetType()==2){
						 // is batch of 4 bets (group winners)
						 for(int i=0; i<userbet.getGroupWinners().size(); i++){
							 UserBet buNew = new UserBet();
							 buNew.setRelatedUser(user);
							 buNew.setGroupResult(userbet.getGroupWinners().get(i));  // agregar validacion extra para corrobar nombres de equipos validos
							 long groupId = i+1;
							 parentBet = betRepository.findByRelatedGroup(groupId);
							 parentBet.getRelatedUserBets().add(buNew);
							 buNew.setRelatedBet(parentBet);
							 
							 betRepository.save(parentBet);  // save bet for each group ID
							 isSaved = true;
							 log.info("Successfully saved UserBet!");
						 }						 
					 }					 
			 }
			 catch(Exception ex) {
				 log.error("Error - " + ex.getMessage());
				 throw ex;
			}
	    }
				
		return isSaved;
	}

	
	@Override
	public boolean modifyBet(BetResultReqDTO buReq, User user) {
		// Business Rules
		boolean isUpdated = false;
		UserBet bu = null;    
		Bet parentBet = null;
		
		if(buReq.getBetType()==1){
			bu = userBetRepository.findBetMatchByUser(user.getId(), buReq.getMatchId());
			parentBet = betRepository.findByRelatedMatch(buReq.getMatchId());
			Match m = matchRepository.findOne(buReq.getMatchId());
			if(!parentBet.getId().equals(buReq.getId()) || buReq.getId()==null){
				throw new BadRequestException("Apuesta incompatible o inexistente.");
			}
			if(!DateUtils.ableToBetMatch(m.getStartDate())){
				throw new BadRequestException("Horario excedido. Ya se cerraron las apuestas para este Partido.");
	    	}
		}
		if(buReq.getBetType()==2){
			bu = userBetRepository.findBetGroupsByUser(user.getId());
			if(!DateUtils.ableToBetGroupWinner()){
				throw new BadRequestException("Horario excedido. Ya se cerraron las apuestas para ganadores de Grupo.");
	    	}
		}
		
		if(bu != null){
			try {
				if(buReq.getBetType()==1){
					 UserBet buNew = new UserBet();
					 buNew.setRelatedUser(user);
					 buNew.setTeamResult(UserBetMapper.setTeamWinnerByResult(buReq)); 
					 buNew.setScoreResult(buReq.getLocalScore()+"-"+buReq.getVisitorScore());
					 parentBet.getRelatedUserBets().add(buNew);
					 buNew.setRelatedBet(parentBet);
					 
					 betRepository.save(parentBet);
					 isUpdated = true;
					 log.info("Successfully updated UserBet!");
				 }
				 
				if(buReq.getBetType()==2){
					 // is batch of 4 bets (group winners)
					 for(int i=0; i<buReq.getGroupWinners().size(); i++){
						 UserBet buNew = new UserBet();
						 buNew.setRelatedUser(user);
						 buNew.setGroupResult(buReq.getGroupWinners().get(i));   // agregar validacion extra para corrobar nombres de equipos validos
						 long groupId = i+1;
						 parentBet = betRepository.findByRelatedGroup(groupId);
						 parentBet.getRelatedUserBets().add(buNew);
						 buNew.setRelatedBet(parentBet);
						 
						 betRepository.save(parentBet);
						 isUpdated = true;
						 log.info("Successfully updated UserBet!");
					 } 
				 }					
			 }
			 catch(Exception ex) {
				 log.error("Error - " + ex.getMessage());
				 throw ex;
			}
	    }
		else{
			throw new RecordNotFoundException("No existe una apuesta previa de este tipo para modificar.");
		}
				
		return isUpdated;
	}


	@Override
	public List<UserBet> listUserBets(long userId) {
		return userBetRepository.findByUser(userId);
	}
	
	
	@Override
	public void viewNotifications(long betId, User user) {
		
		UserBet bu = userBetRepository.findUserBetById(user.getId(), betId); 
		
		if(bu != null) {
			try {
				bu.setNotified(true);
				
				Bet parentBet = betRepository.findOne(bu.getRelatedBet().getId());
				
				UserBet buNotified = new UserBet();
				 buNotified.setRelatedUser(bu.getRelatedUser());
				 buNotified.setTeamResult(bu.getTeamResult());
				 buNotified.setScoreResult(bu.getScoreResult());
				 buNotified.setGroupResult(bu.getGroupResult());
				 buNotified.setPoints(bu.getPoints());
				 buNotified.setNotified(true);
				 parentBet.getRelatedUserBets().add(buNotified);
				 buNotified.setRelatedBet(parentBet);
				 
				betRepository.save(parentBet);
				
    			log.info("Bets successfully fully viewed and saved");
	    		
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}
		else{
			throw new RecordNotFoundException("Apuesta no encontrada.");
		} 

	}
	
    
}




