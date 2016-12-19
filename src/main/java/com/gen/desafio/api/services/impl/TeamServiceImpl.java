package com.gen.desafio.api.services.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gen.desafio.api.dal.TeamDAO;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.Team;
import com.gen.desafio.api.services.TeamService;



@Service
@Transactional
public class TeamServiceImpl implements TeamService {
 
	
    @Autowired 
    private TeamDAO teamRepository;


	@Override
	public Team find(long id) {
		Team team = teamRepository.findOne(id);
		if (team == null) {
			throw new RecordNotFoundException("Equipo no encontrado.");
		}
		
		return team;
	}


	@Override
	public List<Team> listAllTeams() {
		return teamRepository.findAll();
	}


	@Override
	public List<Team> getTeamsByGroup(int group) {
		return teamRepository.findByGroup(group);
	}


	@Override
	public List<Team> getClasifiedTeams() {
		return teamRepository.findClasifiedByGroups();  // Clasificados en Fase de Grupos
	}

    
}




