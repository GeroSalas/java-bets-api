package com.gen.desafio.api.dal;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gen.desafio.api.domain.model.User;


  // JPA REPOSITORY

@Repository
public interface UserDAO extends JpaRepository<User, Long>{ 
    
	// Custom DAO extra query methods added here...
	
	User findFirstByUsername(String username);
	
	@Query(value="SELECT * FROM d_users u WHERE u.username=?1 AND BINARY u.password=?2", nativeQuery=true)
	User loginByUsernameAndPassword(String username, String credentials); 
	
	@Query("SELECT u FROM User u WHERE u.id=?1 AND u.relatedClient.id=?2")
	User findOneByIdAndClient(Long userId, Long clientId);
	
	@Query("SELECT u FROM User u WHERE u.relatedClient.id=?1")
	List<User> findAllByClient(Long clientId);
	
	@Query("SELECT u FROM User u WHERE u.relatedClient.id=?1 AND u.relatedSector=?2")
	List<User> findAllByCompanySector(Long clientId, Long sectorId);
	
	@Query(value="SELECT * FROM d_users u , bet_user_points bu WHERE u.client_id=?1 AND u.on_play=true AND u.id=bu.user_id GROUP BY u.id", nativeQuery=true)
	List<User> findAllActivesByClient(Long clientId);
	
	@Query(value="SELECT * FROM d_users WHERE client_id=?1 AND on_play=true ORDER BY ranking_points DESC LIMIT 4", nativeQuery=true)
	List<User> findTopRankingOrderByPointsDesc(Long clientId); //  Ranking Top 4
	
	@Query(value="SELECT * FROM d_users WHERE client_id=?1 AND on_play=true ORDER BY ranking_points DESC", nativeQuery=true)
	List<User> retrieveRankingListByClient(long clientId);
	
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE d_users SET agree_tutorial=TRUE WHERE id=?1 AND client_id=?2", nativeQuery=true)
	void setAgreeAppTutorial(Long userId, Long clientId);
	
}
