package com.gen.desafio.api.controllers.admin;
 

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gen.desafio.api.domain.dto.req.AddClientReqDTO;
import com.gen.desafio.api.domain.dto.req.EditClientReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.dto.res.ClientResponseDTO;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.Client;
import com.gen.desafio.api.domain.model.UserPreferences;
import com.gen.desafio.api.services.ClientService;
import com.gen.desafio.api.utils.EmailSender;
import com.gen.desafio.api.utils.mappers.ClientMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1a")
@Secured(AuthoritiesConstants.SUPER_ADMIN)
public class ClientAdminController {
	
	private final Logger log = LoggerFactory.getLogger(ClientAdminController.class);
	
	@Autowired
    ClientService clientService; 
	
	
		
   //-------------------Retrieve All Clients on DesafioApp -------------------------------------------------------- 	
	
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ResponseEntity<List<ClientResponseDTO>> listAllClients() {
    	log.debug("Retrieving all Clients");
    	
    	List<Client> clients = clientService.listClients();
    	
        if(clients==null || clients.isEmpty()){
            return new ResponseEntity<List<ClientResponseDTO>>(HttpStatus.NO_CONTENT); 
        }
        else{
        	List<ClientResponseDTO> response = ClientMapper.buildCustomClientsDTOResponse(clients); 
        	return new ResponseEntity<List<ClientResponseDTO>>(response, HttpStatus.OK);	
        }
    }
    
    
  //-------------------Retrieve Single Client ---------------------------------------------
    
    @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable("id") long clientId) {
    	log.debug("Fetching Client with Id " + clientId);
    	
    	Client client = clientService.find(clientId);
    	
    	ClientResponseDTO response = ClientMapper.buildCustomClientDTOResponse(client);
        
        return new ResponseEntity<ClientResponseDTO>(response, HttpStatus.OK);
    }
    
    
  //-------------------Modify existing Client ---------------------------------------------
    
    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BooleanResultDTO> editClient(@PathVariable("id") long clientId,
    		                                           @RequestBody @Validated EditClientReqDTO uReq, BindingResult bindingResult) {
    	
    	log.debug("Fetching to update Client with Id " + clientId);
    	
    	if(bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Client client = clientService.find(clientId);
         client.setName(uReq.getCompanyName());
         client.setEmail(uReq.getCompanyEmail());
        
         if(uReq.getLogoImage() != null){
        	 client.setLogoImage(uReq.getLogoImage());
         }
         
         if(uReq.getBackImage() != null){
        	 client.setBackImage(uReq.getBackImage()); 
         }
        
         UserPreferences styles = null;
         if(uReq.getStyle1()!=null && uReq.getStyle2()!=null && uReq.getStyle3()!=null && uReq.getStyle4()!=null && uReq.getStyle5()!=null){
     		 styles = new UserPreferences();
 	   	  	 styles.setStyle1Color(uReq.getStyle1());
 	   	  	 styles.setStyle2Color(uReq.getStyle2());
 	   	  	 styles.setStyle3Color(uReq.getStyle3());
 	   	  	 styles.setStyle4Color(uReq.getStyle4());
 	   	  	 styles.setStyle5Color(uReq.getStyle5());
 	   	     //client.getAdminUser().setRelatedSettings(styles);
     	 }
    	
        boolean updated = clientService.modifyClient(client,styles);
    	
        BooleanResultDTO response  = new BooleanResultDTO(updated);
        
        if(updated)
            return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        else
            return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
    }  
    
    
   //-------------------Create a New Client -------------------------------------------------
    
    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> createClient(@RequestBody @Validated AddClientReqDTO clientReq, BindingResult bindingResult) {
    	log.debug("Creating new Client with Name: " + clientReq.getCompanyName());
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Client c = ClientMapper.matchClientRequest(clientReq);
        UserPreferences styles = new UserPreferences();  // create new user colors for this Client
	  	  styles.setStyle1Color(clientReq.getStyle1());
	  	  styles.setStyle2Color(clientReq.getStyle2());
	  	  styles.setStyle3Color(clientReq.getStyle3());
	  	  styles.setStyle4Color(clientReq.getStyle4());
	  	  styles.setStyle5Color(clientReq.getStyle5());
	  	  
        boolean saved = clientService.addClient(c, styles);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);     
        
        if(saved){
           EmailSender.welcomeClient(c.getAdminUser()); //  Notify via Email
           return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
        }
        else{
           return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
        }
    }
     
    
  //------------------- Delete a Client-------------------------------------------------  
    
    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BooleanResultDTO> deleteClient(@PathVariable("id") long clientId) {
    	log.debug("Fetching & Deleting Client with Id " + clientId);
        
    	clientService.removeClient(clientId);
        
        BooleanResultDTO responseSuccessful = new BooleanResultDTO(true);
        return new ResponseEntity<BooleanResultDTO>(responseSuccessful, HttpStatus.OK);
    }
    
 
}