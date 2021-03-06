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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gen.desafio.api.domain.dto.res.CustomerTimelineResponseDTO;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.ProfileService;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class TimelineCustomerController {
	
	private final Logger log = LoggerFactory.getLogger(TimelineCustomerController.class);
	
	@Autowired
    ProfileService profileService; 
	
	
	
  //-------------------Retrieve Customer Timeline main recent information------------------------------------ 
	
    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    public ResponseEntity<CustomerTimelineResponseDTO> showCustomerTimeline(@RequestParam(value="page", defaultValue="1", required=false) int page, @AuthenticationPrincipal User authUser) {
    	log.debug("Retrieving minified timeline information for Client Admin profile...");
    	
    	CustomerTimelineResponseDTO timelineInfo = profileService.retrieveCustomerProfileInfo(authUser, page);
    	
        return new ResponseEntity<CustomerTimelineResponseDTO>(timelineInfo, HttpStatus.OK);	
    }
	
 
}