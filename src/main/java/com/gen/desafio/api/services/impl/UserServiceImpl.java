package com.gen.desafio.api.services.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gen.desafio.api.dal.UserDAO;
import com.gen.desafio.api.domain.dto.res.UserBatchResponseDTO;
import com.gen.desafio.api.domain.exception.ApplicationException;
import com.gen.desafio.api.domain.exception.AuthenticationFailureException;
import com.gen.desafio.api.domain.exception.DuplicateKeyException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.UserService;
import com.gen.desafio.api.utils.EmailSender;



@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
 
    @Autowired 
    private UserDAO userRepository;
    

    
    /**
     * GET ONE - FIND
     */
    @Override
	public User find(long userId, long clientId) {    	
		User user = userRepository.findOneByIdAndClient(userId, clientId);
		if (user == null) {			
			throw new RecordNotFoundException("Usuario no encontrado con ID " + userId + " dentro de esta cuenta Cliente.");
		}
		
		return user;
	}
    
    
    /**
     * POST - CREATE  (not used yet)
     */
    @Override
	public User addUser(User user) {
    	if(userRepository.findFirstByUsername(user.getUsername()) != null){
	    	throw new DuplicateKeyException("Email duplicado. Nombre de usuario ya existente."); 
	    }
	    else{
	    	try {
		    	user = userRepository.saveAndFlush(user);
		    	log.info("Successfully saved with Id: " + user.getId());
	    		
		    	return user; 
	    	}
	    	catch(Exception ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
	    }
	}
    
    
    /**
     * POST - CREATE IN BATCH
     */
    @Override
	public List<UserBatchResponseDTO> addUsers(List<User> users) {
    	List<UserBatchResponseDTO> response = new ArrayList<UserBatchResponseDTO>();
    	
    	for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User u = iterator.next();
		    UserBatchResponseDTO bo = new UserBatchResponseDTO();
		     Long createdId = null;
		     String result = null;
		     String errorMessage = null;
		    
		    if(userRepository.findFirstByUsername(u.getUsername()) != null){
		    	result = "Failed";
		    	errorMessage = "Email duplicado. Nombre de usuario ya existente."; 
		    }
		    else{
		    	try {
			    	u = userRepository.save(u);
			    	createdId = u.getId();
			    	log.info("Successfully saved user in batch with Id: " + createdId);
			    	result = "Successfully";
			    	errorMessage = null;
		    	}
		    	catch(Exception ex) {
					log.error("Error - " + ex.getMessage());
					result = "Failed";
					errorMessage = ex.getMessage();
				}
		    }
		    
		    EmailSender.notifyMobileUser(u);
		    
		    bo.setUserId(createdId);
		    bo.setUsername(u.getUsername());
		    bo.setResult(result);
		    bo.setErrorMessage(errorMessage);
		    
		    response.add(bo);
		}
		
		return response;
	}

    
	/**
     * PUT - UPDATE
     */
    @Override
	public User modifyUser(User user) {	
		User u = userRepository.findOne(user.getId()); 
		if(u==null) {
			log.info("Could not found valid User info for: " + user.getId());
			throw new RecordNotFoundException("Usuario inválido para procesar su petición");
		}
		else{
	    	try {
		       	 u = userRepository.saveAndFlush(user);
	    		 log.info("Successfully updated user " + u.getId());
	    		
	    		return u; 
	    		
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}	
	}

    
	/**
     * DELETE - REMOVE
     */
    @Override
	public void removeUser(long id) {
		User validOne = userRepository.findOne(id);
		if(validOne==null) {
			log.info("Could not found valid User info for: " + id);
			throw new RecordNotFoundException("Usuario no encontrado para ID: " + id);
		}
		else{
	    	try {
	    		userRepository.delete(validOne);
				log.info("Deleted successfully");
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}		
	}

    
	/**
     * GET ALL - READ
     */
    @Override
	public List<User> listUsers(long clientId) {
		return userRepository.findAllByClient(clientId);
	}

    
    @Override
	public UserDetails loadUserByUsername(String username) {		
	    return userRepository.findFirstByUsername(username);
	}


	@Override
	public boolean checkLoginCredentials(String username, String password) {
		boolean logged = false;
		User u = userRepository.loginByUsernameAndPassword(username, password);
		
		if(u==null){
			log.info("Login failed for : " + username);
			throw new AuthenticationFailureException("Usuario o Contraseña incorrectos.");  // Credenciales inválidas
		}
		else{
			log.info("User: " +username+" logged successfully");
			logged = true;
		}
		
		return logged;
	}

	@Override
	public int rankUserLogged(long userId, long clientId) {
		int rankingPosition = 0;
		
		List<User> rankingList = userRepository.retrieveRankingListByClient(clientId);
		 for(int i=0; i<rankingList.size(); i++){
			 if(rankingList.get(i).getId() == userId){
				 rankingPosition = i+1;  // X°
				 break;
			 }
		 }
		
		return rankingPosition;
	}
	

	@Override
	public boolean addPushbotsInfo(User user) {
		boolean updated = false;
		try {
    		user = userRepository.saveAndFlush(user);
    		updated = true;
			log.info("Successfully updated User " + user.getId());
		} catch (ApplicationException ex) {
			log.error("Error - " + ex.getMessage());
			throw ex;
		}
		
		return updated;
	}
	
    
}



