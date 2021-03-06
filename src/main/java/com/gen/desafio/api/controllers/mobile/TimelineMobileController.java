package com.gen.desafio.api.controllers.mobile;
 

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

import com.gen.desafio.api.domain.dto.res.MobileTimelineResponseDTO;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.ProfileService;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


@RestController
@RequestMapping("/api/v1m")
@Secured(AuthoritiesConstants.USER)
public class TimelineMobileController {
	
	private final Logger log = LoggerFactory.getLogger(TimelineMobileController.class);
	
	@Autowired
    ProfileService profileService; 
	
	
	
  //-------------------Retrieve Mobile Timeline main recent information------------------------------------ 
	
    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    public ResponseEntity<MobileTimelineResponseDTO> showMobileTimeline(@RequestParam(value="page", defaultValue="1", required=false) int page, @AuthenticationPrincipal User authUser) {
    	log.debug("Retrieving minified timeline information for mobile profile...");
    	
    	MobileTimelineResponseDTO timelineInfo = profileService.retrieveMobileProfileInfo(authUser, page);
    	
        return new ResponseEntity<MobileTimelineResponseDTO>(timelineInfo, HttpStatus.OK);	
    }
	
 
}