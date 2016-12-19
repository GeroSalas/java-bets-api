package com.gen.desafio.api.controllers.admin;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.MatchReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.dto.res.MatchResponseDTO;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.Match;
import com.gen.desafio.api.services.MatchService;
import com.gen.desafio.api.utils.mappers.MatchMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1a")
@Secured(AuthoritiesConstants.SUPER_ADMIN)
public class MatchAdminController {
	
	private final Logger log = LoggerFactory.getLogger(MatchAdminController.class);
	
	@Autowired
    MatchService matchService; 
	
	
  //-------------------Retrieve All Matches (Fixture Complete)-------------------------------------------------------- 
	
    @RequestMapping(value = "/matches", method = RequestMethod.GET)
    public ResponseEntity<List<MatchResponseDTO>> listAllFixture() {
    	log.debug("Retrieving all matches of complete fixture...");
    	
        List<Match> matches = matchService.listAllMatchs();
        
        if(matches==null || matches.isEmpty()){
            return new ResponseEntity<List<MatchResponseDTO>>(HttpStatus.NO_CONTENT);
        }
        else{
        	List<MatchResponseDTO> response = MatchMapper.buildCustomMatchesDTOResponse(matches);
        	return new ResponseEntity<List<MatchResponseDTO>>(response, HttpStatus.OK);	
        }
    }
    
    
  //-------------------Update a Match with Results or Defined Rivals-------------------------------------------------
  //        (internally calls a SP to update all related cascades 'Teams and related Bets and points')
    
    @RequestMapping(value = "/matches", method = RequestMethod.PUT)
    public ResponseEntity<BooleanResultDTO> updateMatch(@RequestBody @Validated MatchReqDTO matchReq, BindingResult bindingResult) {
    	log.debug("Updating match information...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        boolean updated = matchService.updateMatch(matchReq);
        
        BooleanResultDTO response  = new BooleanResultDTO(updated);
        
        if(updated)
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
        else
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT); 
    }
	

 
}