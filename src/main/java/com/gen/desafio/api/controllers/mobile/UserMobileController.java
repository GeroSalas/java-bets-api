package com.gen.desafio.api.controllers.mobile;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.AddMobileTokenReqDTO;
import com.gen.desafio.api.domain.dto.req.ChangePasswordReqDTO;
import com.gen.desafio.api.domain.dto.req.UpdateMobileUserReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.dto.res.UserResponseDTO;
import com.gen.desafio.api.domain.exception.AuthenticationFailureException;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.UserService;
import com.gen.desafio.api.utils.EmailSender;
import com.gen.desafio.api.utils.mappers.UserMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1m")
@Secured(AuthoritiesConstants.USER)
public class UserMobileController {
	
	private final Logger log = LoggerFactory.getLogger(UserMobileController.class);
	
	
	@Autowired
    UserService userService;
	
	
	
	//-------------------Retrieve Current User Info on Mobile App-----------------------------------
	
	@RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDTO> getCurrentUser(@AuthenticationPrincipal User authUser) {
		log.debug("Getting current user information...");

		UserResponseDTO mobileUserInfo = UserMapper.buildCustomUserDTOResponse(authUser);
		 mobileUserInfo.setRankingPosition(userService.rankUserLogged(authUser.getId(), authUser.getRelatedClient().getId()));
		
		return new ResponseEntity<UserResponseDTO>(mobileUserInfo, HttpStatus.OK);
	}
	
	
	//------------------- Send PushBots information -----------------------------------
	
	@RequestMapping(value = "/me", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> addPBInformation(@RequestBody @Validated AddMobileTokenReqDTO pbReq, BindingResult bindingResult, 
    														 @AuthenticationPrincipal User authUser) {
    	log.debug("Adding PushBot util information for existing User...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        authUser.setPbToken(pbReq.getPbToken());
        authUser.setPbPlatform(pbReq.getPlatform());
        
        boolean saved = userService.addPushbotsInfo(authUser);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);     
        
        if(saved)
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        else
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
    }
	
	
	//------------------- Update Profile information ---------------------------------
	
	@RequestMapping(value = "/me", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDTO> updateProfile(@RequestBody @Validated UpdateMobileUserReqDTO uReq, BindingResult bindingResult, 
    		                                             @AuthenticationPrincipal User authUser) {
	    log.debug("Updating mobile user profile...");
	 
	    if (bindingResult.hasErrors()) {
	         throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
	    }
	        
	    authUser.setImage(uReq.getProfileImage());
	    User updated = userService.modifyUser(authUser);
	    
	    UserResponseDTO response = UserMapper.buildCustomUserDTOResponse(updated);
	        
	    return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
	}
 
	
	//-------------------Retrieve All Users by Client Owner------------------------------------------ 	
	
    @RequestMapping(value = "/clients/{client_id}/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDTO>> listAllUsersByClient(@PathVariable("client_id") long clientId, 
    		                                                          @AuthenticationPrincipal User authUser) throws AccessDeniedException {
    	log.debug("Retrieving all users for Client Id " + clientId);   	
    	
    	if(authUser.getRelatedClient().getId().equals(clientId)){
    		List<User> users = userService.listUsers(clientId);
            if(users==null || users.isEmpty()){
                return new ResponseEntity<List<UserResponseDTO>>(HttpStatus.NO_CONTENT); 
            }
            else{
            	List<UserResponseDTO> response = UserMapper.buildCustomUsersDTOResponse(users);
            	return new ResponseEntity<List<UserResponseDTO>>(response, HttpStatus.OK);	
            }
    	}
    	else{
    		throw new AccessDeniedException("No tienes permisos para ver los usuarios de un Cliente ajeno.");
    	}
    	
    }
 
 
    
    //-------------------Retrieve Single User by Client owner--------------------------------------------
     
    @RequestMapping(value = "/clients/{client_id}/users/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("client_id") long clientId, 
										    	   @PathVariable("user_id") long userId, 
										    	   @AuthenticationPrincipal User authUser) throws AccessDeniedException {
    	log.debug("Fetching User with Id " + userId);
        
    	if(authUser.getRelatedClient().getId().equals(clientId)){
    		User u = userService.find(userId, clientId);
    		UserResponseDTO response = UserMapper.buildCustomUserDTOResponse(u);
    		
    		return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
    	}
    	else{
    		throw new AccessDeniedException("No tienes permisos para ver un usuario perteneciente a otro Cliente ajeno.");
    	}
    	
    }     

    
	//------------------- Change Password ---------------------------------
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> changePass(@RequestBody @Validated ChangePasswordReqDTO cpReq, BindingResult bindingResult, 
    		                                           @AuthenticationPrincipal User authUser) {
    	log.debug("Changing password for User...");
 
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
	

	
}