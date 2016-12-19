package com.gen.desafio.api.dal;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gen.desafio.api.domain.model.Team;


  // JPA REPOSITORY

@Repository
public interface TeamDAO extends JpaRepository<Team, Long>{ 
	
	List<Team> findByGroup(int group_id);
	Team findFirstByGroupOrderByPointsDesc(int group_id);
	
	@Query(value= "(SELECT * FROM teams WHERE team_group=1 ORDER BY points_group DESC LIMIT 2) "
		   + " UNION ALL " + 
		   "(SELECT * FROM teams WHERE team_group=2 ORDER BY points_group DESC LIMIT 2) "
		   + " UNION ALL " +
           "(SELECT * FROM teams WHERE team_group=3 ORDER BY points_group DESC LIMIT 2) "
           + " UNION ALL " +
           "(SELECT * FROM teams WHERE team_group=4 ORDER BY points_group DESC LIMIT 2) ", nativeQuery=true)
	List<Team> findClasifiedByGroups();  // 2 primeros equipos de cada grupo
	
}
