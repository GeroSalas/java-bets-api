package com.gen.desafio.api.dal;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gen.desafio.api.domain.model.Bet;


  // JPA REPOSITORY

@Repository
public interface BetDAO extends JpaRepository<Bet, Long>{ 
	
	
	@Query("SELECT b FROM Bet b WHERE b.betType=1 AND b.relatedMatch.id=?1")
	Bet findByRelatedMatch(Long matchId);
	
	@Query(value="SELECT * FROM bets WHERE bet_type=2 AND related_group_name=?1", nativeQuery=true)
	Bet findByRelatedGroup(Long groupId);
	
	List<Bet> findByBetTypeOrderByIdAsc(int betType);
	
	@Query("SELECT b FROM Bet b WHERE b.relatedMatch.startDate>DATE_FORMAT(NOW(),'%Y-%m-%d') AND b.relatedMatch.isClosed=false")
	List<Bet> findPendingMatchesToBetByUser(long userId);
	
}
