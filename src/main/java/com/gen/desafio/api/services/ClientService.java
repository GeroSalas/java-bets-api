package com.gen.desafio.api.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.gen.desafio.api.domain.model.Client;
import com.gen.desafio.api.domain.model.UserPreferences;


@Service
public interface ClientService extends BaseService {
	
	 Client find(long id);
	 boolean addClient(Client client, UserPreferences settings);
	 boolean modifyClient(Client client, UserPreferences settings);
     void removeClient(long id);
     List<Client> listClients(); 	                         

}
