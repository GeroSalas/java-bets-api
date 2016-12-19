package com.gen.desafio.api.controllers.admin;
 

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.ChangePasswordReqDTO;
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
@RequestMapping("/api/v1a")
@Secured(AuthoritiesConstants.SUPER_ADMIN)
public class UserAdminController {
	
	private final Logger log = LoggerFactory.getLogger(UserAdminController.class);
	
	@Autowired
    UserService userService;
	
	
	
	//-------------------Retrieve Current SuperAdmin info -----------------------------------
	
	@RequestMapping(value = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDTO> getCurrentUser(@AuthenticationPrincipal User authUser) {
		log.debug("Getting super admin user information...");

		UserResponseDTO superAdminInfo = UserMapper.buildCustomUserDTOResponse(authUser);
		
		return new ResponseEntity<UserResponseDTO>(superAdminInfo, HttpStatus.OK);
	}
	
	
    //------------------- Change SuperAdmin Password ---------------------------------
	
  	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
      public ResponseEntity<BooleanResultDTO> changePass(@RequestBody @Validated ChangePasswordReqDTO cpReq, BindingResult bindingResult, 
    		                                             @AuthenticationPrincipal User authUser) {
      	log.debug("Changing password for Super Admin...");
   
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