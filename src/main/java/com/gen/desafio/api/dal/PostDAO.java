package com.gen.desafio.api.dal;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gen.desafio.api.domain.model.Post;


  // JPA REPOSITORY

@Repository
public interface PostDAO extends JpaRepository<Post, Long>{ 
	
	@Query(value="SELECT COUNT(p.id) AS total_posts FROM posts p, d_users u WHERE p.user_id=u.id AND u.client_id=?1", nativeQuery=true)
	Integer countAllPostsByClient(Long clientId);  
	
	//@Query(value="SELECT p.* FROM posts p, d_users u WHERE p.user_id=u.id AND u.client_id=?1 AND p.is_comment=false GROUP BY p.id ORDER BY p.posted_date DESC LIMIT 30 OFFSET ?2", nativeQuery=true)
	@Query(value="SELECT pos.* FROM d_users u , (SELECT * FROM posts p INNER JOIN posts_has_sectors ps ON p.id=ps.post_id) pos WHERE pos.user_id=u.id AND u.client_id=?1 AND pos.sector_id=?2 AND pos.is_comment=false GROUP BY pos.id ORDER BY pos.posted_date DESC LIMIT 30 OFFSET ?3", nativeQuery=true)
	List<Post> findRecentsByCompanySector(Long clientId, Long sectorId, int page);
	
	@Query(value="SELECT p.* FROM posts p, d_users u WHERE p.user_id=u.id AND u.client_id=?1 AND p.is_comment=false GROUP BY p.id ORDER BY p.posted_date DESC LIMIT 30 OFFSET ?2", nativeQuery=true)
	List<Post> findAllByClient(Long clientId, int page);
	
	@Query(value="SELECT p.* FROM posts p WHERE p.user_id=?1 AND p.is_comment=false GROUP BY p.id ORDER BY p.posted_date DESC", nativeQuery=true)
	List<Post> findPostsByUser(long userId);
	
	@Query(value="SELECT p.* FROM posts p, d_users u WHERE p.user_id=u.id AND u.client_id=?1 AND p.is_comment=false ORDER BY p.posted_date DESC LIMIT 4", nativeQuery=true)
	List<Post> findLast4PostsByClient(Long clientId); 
	
}
