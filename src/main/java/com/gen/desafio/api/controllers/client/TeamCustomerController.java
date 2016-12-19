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

import com.gen.desafio.api.domain.dto.res.TeamResponseDTO;
import com.gen.desafio.api.domain.model.Team;
import com.gen.desafio.api.services.TeamService;
import com.gen.desafio.api.utils.mappers.TeamMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class TeamCustomerController {
	
	private final Logger log = LoggerFactory.getLogger(TeamCustomerController.class);
	
	@Autowired
    TeamService teamService; 
	
	
   //-------------------Retrieve All Teams -------------------------------------------------------- 
	
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ResponseEntity<List<TeamResponseDTO>> listAllTeams() {
    	log.debug("Retrieving all teams of the competition...");
    	
        List<Team> teams = teamService.listAllTeams();
        
        if(teams==null || teams.isEmpty()){
            return new ResponseEntity<List<TeamResponseDTO>>(HttpStatus.NO_CONTENT);
        }
        else{
        	List<TeamResponseDTO> response = TeamMapper.buildCustomTeamsDTOResponse(teams);
        	return new ResponseEntity<List<TeamResponseDTO>>(response, HttpStatus.OK);	
        }
    }
	
 
}