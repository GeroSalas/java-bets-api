package com.gen.desafio.api.dal;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gen.desafio.api.domain.model.Sector;


  // JPA REPOSITORY

@Repository
public interface SectorDAO extends JpaRepository<Sector, Long>{ 
	
	@Query(value="SELECT s.* FROM sectors s INNER JOIN d_users u ON s.id=u.sector_id WHERE u.client_id=?1 GROUP BY s.id", nativeQuery=true)
	List<Sector> getClientSectors(long clientId);
	
}
