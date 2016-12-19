package com.gen.desafio.api.services;


import java.util.List;
import org.springframework.stereotype.Service;
import com.gen.desafio.api.domain.model.Team;


@Service
public interface TeamService extends BaseService {
	
	 Team find(long id);
     List<Team> listAllTeams();
     List<Team> getTeamsByGroup(int group);
     List<Team> getClasifiedTeams();

}
