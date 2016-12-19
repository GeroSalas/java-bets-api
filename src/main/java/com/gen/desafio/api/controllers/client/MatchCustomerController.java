package com.gen.desafio.api.controllers.client;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.res.MatchResponseDTO;
import com.gen.desafio.api.domain.model.Match;
import com.gen.desafio.api.services.MatchService;
import com.gen.desafio.api.utils.mappers.MatchMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class MatchCustomerController {
	
	private final Logger log = LoggerFactory.getLogger(MatchCustomerController.class);
	
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
        	List<MatchResponseDTO> fixture = MatchMapper.buildCustomMatchesDTOResponse(matches);
        	return new ResponseEntity<List<MatchResponseDTO>>(fixture, HttpStatus.OK);	
        }
    }
	
 
}