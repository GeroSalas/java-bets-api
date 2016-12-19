package com.gen.desafio.api.services.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.desafio.api.dal.ClientDAO;
import com.gen.desafio.api.dal.SettingsDAO;
import com.gen.desafio.api.dal.UserDAO;
import com.gen.desafio.api.domain.exception.ApplicationException;
import com.gen.desafio.api.domain.exception.DuplicateKeyException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.Client;
import com.gen.desafio.api.domain.model.UserPreferences;
import com.gen.desafio.api.services.ClientService;



@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
 
    @Autowired 
    private ClientDAO clientRepository;
    
    @Autowired 
    private UserDAO userRepository;
    
    @Autowired 
    private SettingsDAO settingsRepository;

    
	@Override
	public Client find(long id) {
		Client client = clientRepository.findOne(id);
		if (client == null) {
			throw new RecordNotFoundException("Cliente no encontrado!");  
		}
		
		return client; 
	}

	
	@Override
	public boolean addClient(Client client, UserPreferences styles) {
		boolean isSaved = false;
    	
    	if(clientRepository.findFirstByName(client.getName()) != null){
	    	throw new DuplicateKeyException("Cliente ya existente."); 
	    }
	    else{
	    	try {
	    		UserPreferences s = settingsRepository.save(styles);
	    		client.getAdminUser().setRelatedSettings(s);
	    		client = clientRepository.save(client);
	    		userRepository.saveAndFlush(client.getAdminUser()); // save CustomerAdmin user
				
	    		log.info("Successfully saved with Id: " + client.getId());
				isSaved = true;
	    	}
	    	catch(Exception ex) {
	    		clientRepository.delete(client);
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
	    }
		
		return isSaved;
	}

	
	@Override
	public boolean modifyClient(Client client, UserPreferences styles) {
		boolean isSaved = false;
    	
		Client c = clientRepository.findOne(client.getId()); 
		if(c==null) {
			log.info("Could not found valid Client info for: " + client.getId());
			throw new RecordNotFoundException("Cliente inválido para procesar su petición");
		}
		else{
	    	try {
	    		if(styles != null){
	    			UserPreferences s = settingsRepository.save(styles);
		    		client.getAdminUser().setRelatedSettings(s);
		    		client = clientRepository.save(client);
		    		userRepository.saveAndFlush(client.getAdminUser()); // save CustomerAdmin user
	    		}
	    		else{
	    			client = clientRepository.saveAndFlush(client);
	    		}
	    		
				log.info("Successfully updated Client " + client.getId());				
				isSaved = true;
				
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}	
		
		return isSaved;
	}
	

	
	@Override
	public void removeClient(long id) {
		// Business Rules
		Client validOne = clientRepository.findOne(id);
		if(validOne==null) {
				log.info("Could not found valid Client info for: " + id);
				throw new RecordNotFoundException("Client info not found for: " + id);
		}
		else{
			try {
				clientRepository.delete(validOne);
			   log.info("Deleted successfully");
		    } catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
	    }		
	}

	
	@Override
	public List<Client> listClients() {
		return clientRepository.findAll();
	}
    
    
}



