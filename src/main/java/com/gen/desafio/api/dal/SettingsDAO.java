package com.gen.desafio.api.dal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gen.desafio.api.domain.model.UserPreferences;


  // JPA REPOSITORY

@Repository
public interface SettingsDAO extends JpaRepository<UserPreferences, Long>{ 
    
	
}
