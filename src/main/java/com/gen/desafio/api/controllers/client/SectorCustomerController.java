package com.gen.desafio.api.controllers.client;
 

import java.util.List;

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

import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.SectorService;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class SectorCustomerController {
	
	private final Logger log = LoggerFactory.getLogger(SectorCustomerController.class);
	
	@Autowired
    SectorService sectorService; 
	
	
		
   //-------------------Retrieve Sectors for Clients-------------------------------------------------------- 	
	
    @RequestMapping(value = "/sectors", method = RequestMethod.GET)
    public ResponseEntity<List<Sector>> listAllSectors(@RequestParam(value="filter", required=true) String filter,
    												   @AuthenticationPrincipal User authUser) {
    	log.debug("Retrieving all Sectors");
    	
    	List<Sector> sectors = null;
    	
    	if(filter.equalsIgnoreCase("all"))
    		sectors = sectorService.listSectors();
    	else if(filter.equalsIgnoreCase("used"))
    		sectors = sectorService.listSectors(authUser.getRelatedClient().getId());
    	else
    		throw new BadRequestException("Debe indicar el filtro de Sectores a consultar (all / used).");
        
    	
    	if(sectors==null || sectors.isEmpty()){
            return new ResponseEntity<List<Sector>>(HttpStatus.NO_CONTENT); 
        }
        else{
        	return new ResponseEntity<List<Sector>>(sectors, HttpStatus.OK);	
        }
    }
  
 
}