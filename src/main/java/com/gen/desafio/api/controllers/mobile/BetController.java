package com.gen.desafio.api.controllers.mobile;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.BetResultReqDTO;
import com.gen.desafio.api.domain.dto.res.UserBetResponseDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.domain.model.UserBet;
import com.gen.desafio.api.services.BetService;
import com.gen.desafio.api.utils.mappers.UserBetMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1m")
@Secured(AuthoritiesConstants.USER)
public class BetController {
	
	private final Logger log = LoggerFactory.getLogger(BetController.class);
	
	
	@Autowired
    BetService betService;
	
	
	
	//-------------------Retrieve Mobile User Bet -----------------------------------
	
	@RequestMapping(value = "/bets/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserBetResponseDTO> retrieveUserBet(@PathVariable("id") long betId, 
															  @AuthenticationPrincipal User authUser) {
		log.debug("Retrieving User Bet...");

		UserBet userBet = betService.find(authUser.getId(), betId);
		
		UserBetResponseDTO response =  UserBetMapper.buildCustomUserBetDTO(userBet);
		
        return new ResponseEntity<UserBetResponseDTO>(response, HttpStatus.OK);	
		
	}
	
	
	//-------------------Retrieve Mobile User Bets -----------------------------------
	
	@RequestMapping(value = "/bets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserBetResponseDTO>> retrieveUserBets(@AuthenticationPrincipal User authUser) {
		log.debug("Retrieving User Bets...");

		List<UserBet> userBets = betService.listUserBets(authUser.getId());
		
		if(userBets==null || userBets.isEmpty()){
            return new ResponseEntity<List<UserBetResponseDTO>>(HttpStatus.NO_CONTENT);
        }
        else{
        	List<UserBetResponseDTO> response =  UserBetMapper.buildCustomUserBetsDTO(userBets);
        	return new ResponseEntity<List<UserBetResponseDTO>>(response, HttpStatus.OK);	
        }
		
	}
	
	
	//------------------- Create Mobile User Bet -----------------------------------
	
	@RequestMapping(value = "/bets", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> createUserBet(@RequestParam(value="bt", required=true) int betType, 
    		                                              @RequestBody @Validated BetResultReqDTO betReq, BindingResult bindingResult, 
    		                                              @AuthenticationPrincipal User authUser) {
    	log.debug("Creating new User Bet...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        if(betType==1 || betType==2){
        	boolean valid = UserBetMapper.validateBetDTO(betReq, betType);
        	if(valid){
        		boolean saved = betService.createBet(betReq, authUser);
                
                BooleanResultDTO response  = new BooleanResultDTO(saved);
                
                if(saved)
                	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
                else
                	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);  
        	}
        	else{
        		throw new BadRequestException("Algunos campos tienen errores.");
        	}
        }
        else{
        	throw new BadRequestException("No podemos validar su apuesta ya que no coinciden los parametros correctos.");
        }
        
    }
	
	
	
	//------------------- Edit Mobile User Bet -----------------------------------
	
	@RequestMapping(value = "/bets", method = RequestMethod.PUT)
	public ResponseEntity<BooleanResultDTO> editUserBet(@RequestBody @Validated BetResultReqDTO betReq, BindingResult bindingResult, 
			                                            @AuthenticationPrincipal User authUser) {
	    log.debug("Editing existing Bet...");
	 
	    if (bindingResult.hasErrors()) {
	          throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
	    }
	        
	    boolean updated = betService.modifyBet(betReq, authUser);
	        
	    BooleanResultDTO response  = new BooleanResultDTO(updated);
        
        if(updated)
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
        else
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);  
	}
    
	
	//------------------- Confirm Bet notifications viewed -----------------------------------
	
	@RequestMapping(value = "/bets/{id}", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> viewBetNotification(@PathVariable("id") long betId, 
    		 													@RequestParam(value="notified", required=true) boolean isNotified, 
    		 													@AuthenticationPrincipal User authUser) {
    	log.debug("Set notifications viewed on Bet...");
 
    	if(isNotified){
    		betService.viewNotifications(betId, authUser);
    	}
          
        return new ResponseEntity<BooleanResultDTO>(new BooleanResultDTO(true), HttpStatus.OK);
    }		
 
	
}