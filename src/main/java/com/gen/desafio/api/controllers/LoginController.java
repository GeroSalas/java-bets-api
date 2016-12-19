package com.gen.desafio.api.controllers;
 

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gen.desafio.api.domain.dto.req.ForgotPasswordReqDTO;
import com.gen.desafio.api.domain.dto.req.LoginReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.exception.AuthenticationFailureException;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.UserService;
import com.gen.desafio.api.utils.APIConstants;
import com.gen.desafio.api.utils.EmailSender;
import com.gen.desafio.api.utils.security.AuthenticationService;
import com.gen.desafio.api.utils.security.SecurityUtils;
import com.gen.desafio.api.utils.security.UserAuthentication;

 
@RestController
@RequestMapping("/api")
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
    UserService userService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	
	
	//-------------------Secure Stateless Login with Access Token--------------------------------------
	
	@RequestMapping(value = "/login/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BooleanResultDTO> doLogin(HttpServletRequest request, 
    		                                        @RequestBody @Validated LoginReqDTO loginReq, BindingResult bindingResult) throws Exception {
    	log.debug("Performing Login authentication for: " + loginReq.getUsername());
 
    	BooleanResultDTO response = new BooleanResultDTO();
    	String accessToken = null;
    	
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Usuario o Contraseña inválidos.", bindingResult);
        }
        
        boolean validAPIKey = authenticationService.getAPIKeyAuthentication(request);
        if(validAPIKey){
        	Authentication authenticated = authenticationService.getAuthentication(request);
            if(authenticated!=null && authenticated.getDetails().equals(loginReq.getUsername())){  
                //  if do login with already valid access token provided
            	response.setResult(true);
            	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
            }
            else{
            	// Let's authenticate the User and give a Token to the client
                boolean isValid = userService.checkLoginCredentials(loginReq.getUsername(), loginReq.getPassword());
                if(isValid){
                	// OK! - Successful Login
                	authenticated = new UserAuthentication((User) userService.loadUserByUsername(loginReq.getUsername()));
                	accessToken = authenticationService.createCustomJWT((User) authenticated.getPrincipal());
                	SecurityContextHolder.getContext().setAuthentication(authenticated);
                	
                	// build response
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(APIConstants.X_AUTH_TOKEN, accessToken);  // provide our custom JWT
                    response.setResult(true);
                    return new ResponseEntity<BooleanResultDTO>(response, headers, HttpStatus.OK);
                }
                else{
                	// FAILED! - Unsuccessful Login (handled by ExceptionProcessor anyway)
                	response.setResult(false);
                	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.UNAUTHORIZED);
                }
            }
        }
        else{
        	throw new AuthenticationFailureException("Invalid API Key.");
        	//return new ResponseEntity<BooleanResultDTO>(ERROR, HttpStatus.UNAUTHORIZED);
        }
        
    }
	
	//------------------- Forgot/Reset Password ---------------------------------
	
		@RequestMapping(value = "/forgotpassword/", method = RequestMethod.POST)
	    public ResponseEntity<BooleanResultDTO> resetPass(HttpServletRequest request, 
	    		                                          @RequestBody @Validated ForgotPasswordReqDTO fpReq, BindingResult bindingResult) {
	    	log.debug("Requesting to reset password for User...");
	 
	        if (bindingResult.hasErrors()) {
	            throw new InvalidRequestException("Email inválido.", bindingResult);
	        }
	        
	        BooleanResultDTO response  = new BooleanResultDTO();
	        
	        boolean validAPIKey = authenticationService.getAPIKeyAuthentication(request);
	        if(validAPIKey){
	        	User authUser = (User) userService.loadUserByUsername(fpReq.getUsername());
		         authUser.setPassword(SecurityUtils.generateRandomPassword());
		         userService.modifyUser(authUser);
		            	
		         EmailSender.notifyMobileUser(authUser);  //  Notify new temporal Password via Email
		         response.setResult(true);
		         return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
	        }
	        else{
	        	throw new AuthenticationFailureException("Invalid API Key.");
	        	//return new ResponseEntity<BooleanResultDTO>(ERROR, HttpStatus.UNAUTHORIZED);
	        }
	        
	    }
		
 
}