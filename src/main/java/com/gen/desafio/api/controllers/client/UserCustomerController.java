package com.gen.desafio.api.controllers.client;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.BatchUsersReqDTO;
import com.gen.desafio.api.domain.dto.req.ChangePasswordReqDTO;
import com.gen.desafio.api.domain.dto.req.EditUserReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.dto.res.ClientResponseDTO;
import com.gen.desafio.api.domain.dto.res.UserBatchResponseDTO;
import com.gen.desafio.api.domain.dto.res.UserResponseDTO;
import com.gen.desafio.api.domain.exception.AuthenticationFailureException;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.ClientService;
import com.gen.desafio.api.services.SectorService;
import com.gen.desafio.api.services.UserService;
import com.gen.desafio.api.utils.EmailSender;
import com.gen.desafio.api.utils.mappers.ClientMapper;
import com.gen.desafio.api.utils.mappers.UserMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;

 
@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class UserCustomerController {
	
	private final Logger log = LoggerFactory.getLogger(UserCustomerController.class);
	
	@Autowired
    ClientService clientService;
	
	@Autowired
    UserService userService;
	
	@Autowired
    SectorService sectorService;
	
	
	//-------------------Retrieve Current Client Info------------------------------------------------
	
	@RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientResponseDTO> getCurrentClient(@AuthenticationPrincipal User authUser) {
		log.debug("Getting current client information...");
		
		ClientResponseDTO adminClientInfo = ClientMapper.buildCustomClientDTOResponse(authUser.getRelatedClient());
		
		return new ResponseEntity<ClientResponseDTO>(adminClientInfo, HttpStatus.OK);
	}
	
	
//	//------------------- Update Client Profile information ---------------------------------
//	
//	@RequestMapping(value = "/me", method = RequestMethod.PUT)
//    public ResponseEntity<UserResponseDTO> updateClientProfile(@RequestBody @Validated UpdateClientProfileReqDTO uReq, BindingResult bindingResult, @AuthenticationPrincipal User authUser) {
//    	log.debug("Updating mobile user profile...");
// 
//        if (bindingResult.hasErrors()) {
//            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
//        }
//        
//        authUser.getRelatedClient().setLogoImage(uReq.getLogoImage());
//        authUser.getRelatedClient().setBackImage(uReq.getBackImage());
//        UserResponseDTO updated = userService.modifyUser(authUser);
//        
//        return new ResponseEntity<UserResponseDTO>(updated, HttpStatus.OK);
//    }
	
 
	
	//-------------------Retrieve All Users by Client owner-------------------------------------------------------- 	
	
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDTO>> listAllUsersByClient(@AuthenticationPrincipal User authUser) {
    	log.debug("Retrieving all users for Client...");
    	
    	List<UserResponseDTO> response = null;
    	
    	List<User> users = userService.listUsers(authUser.getRelatedClient().getId()); 
    	
        if(users==null){
            return new ResponseEntity<List<UserResponseDTO>>(HttpStatus.NO_CONTENT); 
        }
        else{
        	response = UserMapper.buildCustomUsersDTOResponse(users);
        	return new ResponseEntity<List<UserResponseDTO>>(response, HttpStatus.OK);	
        }
    }
 
 
    
    //-------------------Retrieve Single User on Current Client------------------------------------
     
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDTO> getMobileUser(@PathVariable("id") long userId, 
    		                                             @AuthenticationPrincipal User authUser) {
    	log.debug("Fetching User with Id " + userId);
    	
        User u = userService.find(userId, authUser.getRelatedClient().getId());
        UserResponseDTO response = UserMapper.buildCustomUserDTOResponse(u);
        
        return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
    }
 
     
     
    //-------------------Create (BATCH) Users on Current Client---------------------------------------------
     
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<List<UserBatchResponseDTO>> createMobileUser(@RequestBody @Validated BatchUsersReqDTO usersBatchReq, BindingResult bindingResult, 
    		                                                           @AuthenticationPrincipal User authUser) {
    	log.debug("Creating new Users on batch...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        List<UserBatchResponseDTO> response = userService.addUsers(UserMapper.matchUsersBatchRequest(authUser.getRelatedClient(), usersBatchReq));
        
        if(response.size() > 0){
            return new ResponseEntity<List<UserBatchResponseDTO>>(response, HttpStatus.OK); 
        }
        else{
        	return new ResponseEntity<List<UserBatchResponseDTO>>(HttpStatus.CONFLICT);	
        }

    }
    
    
	//------------------- Update Mobile User Profile information ---------------------------------
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") long userId, 
    												  @RequestBody @Validated EditUserReqDTO uReq, BindingResult bindingResult, 
    		                                          @AuthenticationPrincipal User authUser) {
    	log.debug("Updating mobile user profile...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        boolean needNotiEmail = false;
        
        User u = userService.find(userId, authUser.getRelatedClient().getId());
         if(uReq.getFirstname()!=null) u.setFirstname(uReq.getFirstname());
      	 if(uReq.getLastname()!=null) u.setLastname(uReq.getLastname());
      	 if(uReq.getUsername()!=null){
      		 needNotiEmail = true;
      		 u.setUsername(uReq.getUsername());
      	 }
      	 if(uReq.getAge()!=null) u.setAge(uReq.getAge());
      	 if(uReq.getGender()!=null) u.setGender(uReq.getGender());
      	 if(uReq.getCompanySector()!=null){
      		 Sector s = sectorService.getSector(uReq.getCompanySector());
      		 u.setRelatedSector(s); 
      	 }
    	
        User updated = userService.modifyUser(u);
        
        if(needNotiEmail) EmailSender.notifyMobileUser(authUser);  //  Notify via Email
        
        UserResponseDTO response = UserMapper.buildCustomUserDTOResponse(updated);
        
        return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
    }
 
    
    
    //------------------- Delete a User on Current Client-------------------------------------------------    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BooleanResultDTO> deleteMobileUser(@PathVariable("id") long userId) {
    	log.debug("Fetching & Deleting User with Id " + userId);
        
        userService.removeUser(userId);
        
        BooleanResultDTO responseSuccessful = new BooleanResultDTO(true);
        return new ResponseEntity<BooleanResultDTO>(responseSuccessful, HttpStatus.OK);
    }
    
    
    
    //------------------- Change Admin Password ---------------------------------
	
  	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
      public ResponseEntity<BooleanResultDTO> changePass(@RequestBody @Validated ChangePasswordReqDTO cpReq, BindingResult bindingResult, 
    		                                             @AuthenticationPrincipal User authUser) {
      	log.debug("Changing password for Admin User...");
   
          if (bindingResult.hasErrors()) {
              throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
          }
          
          BooleanResultDTO response  = new BooleanResultDTO();
          
          if(authUser.getUsername().equals(cpReq.getUsername()) && authUser.getPassword().equals(cpReq.getCurrentPassword())){
          	if(authUser.getPassword().equals(cpReq.getCurrentPassword()) && !authUser.getPassword().equalsIgnoreCase(cpReq.getNewPassword())){
          		authUser.setPassword(cpReq.getNewPassword());
              	userService.modifyUser(authUser);
              	
              	EmailSender.notifyMobileUser(authUser);  //  Notify via Email
              	
              	response.setResult(true);            	
              	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
              }
          	else{
          		response.setResult(false);
          		throw new BadRequestException("Su contraseña nueva debe ser diferente a la anterior.");
          	}
          }
          else{
          	response.setResult(false);
          	throw new AuthenticationFailureException("Credenciales incorrectas.");
          }
          
      }
  	
    
    
    
    
    /**
     *  SPRING HATEOAS DTO HREF LINKS
     * 
    private void addUserLinks(List<UserResponseDTO> users){
    	for(UserResponseDTO u : users){
    		u.add(linkTo(methodOn(UserCustomerController.class).getUser(u.getUserId())).withSelfRel());
    	}
    }
    */
 
}