package com.gen.desafio.api.dal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gen.desafio.api.domain.model.Client;


  // JPA REPOSITORY

@Repository
public interface ClientDAO extends JpaRepository<Client, Long>{
    
	// Custom DAO extra query methods added here...
	
	Client findFirstByName(String name);
	
}
