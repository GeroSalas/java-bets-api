package com.gen.desafio.api.dal;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gen.desafio.api.domain.model.UserBet;


  // JPA REPOSITORY

@Repository
public interface UserBetDAO extends JpaRepository<UserBet, Long>{ 
	
	@Query("SELECT bu FROM UserBet bu WHERE bu.relatedUser.id=?1 AND bu.relatedBet.id=?2")
	UserBet findUserBetById(long userId, long betId);
	
	@Query(value="SELECT * FROM bet_user_points bu, bets b WHERE bu.user_id=?1 AND bu.bet_id=b.id AND b.related_match_id=?2 AND b.bet_type=1", nativeQuery=true)
	UserBet findBetMatchByUser(long userId, long matchId);  
	
	@Query(value="SELECT * FROM bet_user_points bu, bets b WHERE bu.user_id=?1 AND bu.bet_id=b.id AND b.bet_type=2 LIMIT 1", nativeQuery=true)
	UserBet findBetGroupsByUser(long userId);  
	
	@Query(value="SELECT COUNT(DISTINCT(u.id)) FROM d_users u INNER JOIN (SELECT bu.user_id AS players_id FROM bet_user_points bu INNER JOIN (SELECT b.id AS related_bets FROM bets b, matches m WHERE m.start_date < DATE_FORMAT(NOW(),'%Y-%m-%d') AND m.is_closed=TRUE AND b.related_match_id=m.id AND b.bet_type=1 ORDER BY m.start_date DESC LIMIT 1) a ON bu.bet_id=a.related_bets) actives WHERE u.id=actives.players_id AND u.client_id=?1", nativeQuery=true)
	Integer countActiveUsersOnPrevMatchByClient(Long clientId);  
	
	@Query(value="SELECT COUNT(DISTINCT(u.id)) FROM d_users u INNER JOIN (SELECT bu.user_id AS players_id FROM bet_user_points bu INNER JOIN (SELECT b.id AS related_bets FROM bets b, matches m WHERE m.start_date > DATE_FORMAT(NOW(),'%Y-%m-%d') AND m.is_closed=FALSE AND b.related_match_id=m.id AND b.bet_type=1 ORDER BY m.start_date ASC LIMIT 1) a ON bu.bet_id=a.related_bets) actives WHERE u.id=actives.players_id AND u.client_id=?1", nativeQuery=true)
	Integer countActiveUsersOnNextMatchByClient(Long clientId);  
	
	@Query(value="SELECT COUNT(DISTINCT(u.id)) FROM d_users u INNER JOIN (SELECT bu.user_id AS players_id FROM bet_user_points bu INNER JOIN (SELECT b.id AS related_bets FROM bets b, matches m WHERE m.is_closed=TRUE IS NOT NULL AND m.start_date < DATE_FORMAT(NOW(),'%Y-%m-%d') AND b.related_match_id=m.id AND b.bet_type=1) a ON bu.bet_id=a.related_bets) actives WHERE u.id=actives.players_id AND u.client_id=?1", nativeQuery=true)
	Integer countActiveUsersOnAllMatchesByClient(Long clientId);  
	
	@Query(value="SELECT * FROM bet_user_points WHERE points IS NOT NULL AND notified_on_timeline=false AND user_id=?1", nativeQuery=true)
	List<UserBet> findBetPointsToNotifyByUser(Long userId);  

	@Query(value="SELECT * FROM bet_user_points WHERE user_id=?1", nativeQuery=true)
	List<UserBet> findByUser(Long userId);  
	
}
