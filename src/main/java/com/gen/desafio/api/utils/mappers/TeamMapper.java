package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.List;
import com.gen.desafio.api.domain.dto.res.TeamResponseDTO;
import com.gen.desafio.api.domain.model.Team;


public class TeamMapper extends ObjectMapper {
	
	
	public static TeamResponseDTO buildSingleTeamDTOResponse(Team team){
    	TeamResponseDTO bo = new TeamResponseDTO(team.getId(), team.getName(), team.getFlag(), team.getShield(), team.getGroup(), team.getPoints());   	 
    	return bo;
    }
    
    
    public static List<TeamResponseDTO> buildCustomTeamsDTOResponse(List<Team> teams){
    	List<TeamResponseDTO> dtos = new ArrayList<TeamResponseDTO>();
    	for(Team t : teams){
    		TeamResponseDTO dto = buildSingleTeamDTOResponse(t);
    		dtos.add(dto); 	 
    	}
    	
    	return dtos;
    }   
   
}
