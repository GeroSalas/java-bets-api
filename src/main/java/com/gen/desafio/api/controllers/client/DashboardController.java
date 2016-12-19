package com.gen.desafio.api.controllers.client;
 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.res.DashboardCInfoResponseDTO;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.ProfileService;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class DashboardController {
	
	private final Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	ProfileService profileService;
	
	
 //-------------------Retrieve All Utils Info to bind Dashboard main page--------------------------- 
	
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ResponseEntity<DashboardCInfoResponseDTO> retrieveDasboardUtils(@AuthenticationPrincipal User authUser) {
    	log.debug("Retrieving all information necessary for Customer Dashboard main page...");
    	
        DashboardCInfoResponseDTO dashInfo = profileService.retrieveClientProfileInfo(authUser);

        return new ResponseEntity<DashboardCInfoResponseDTO>(dashInfo, HttpStatus.OK);	
    }
	
 
}