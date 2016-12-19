package com.gen.desafio.api.controllers.admin;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.services.SectorService;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1a")
@Secured(AuthoritiesConstants.SUPER_ADMIN)
public class SectorAdminController {
	
	private final Logger log = LoggerFactory.getLogger(SectorAdminController.class);
	
	@Autowired
    SectorService sectorService; 
	
	
		
   //-------------------Retrieve All Sector available for Clients-------------------------------------------------------- 	
	
    @RequestMapping(value = "/sectors", method = RequestMethod.GET)
    public ResponseEntity<List<Sector>> listAllSectors() {
    	log.debug("Retrieving all Sectors");
    	
    	List<Sector> sectors = sectorService.listSectors();
        
    	if(sectors==null || sectors.isEmpty()){
            return new ResponseEntity<List<Sector>>(HttpStatus.NO_CONTENT); 
        }
        else{
        	return new ResponseEntity<List<Sector>>(sectors, HttpStatus.OK);	
        }
    }
    
   
    
  //-------------------Create a New Sector -------------------------------------------------
    
    @RequestMapping(value = "/sectors", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> createSector(@RequestBody @Validated Sector sectorReq) {
    	log.debug("Creating new Sector with Name: " + sectorReq.getName());
 
        if (sectorReq.getName()==null || sectorReq.getName().isEmpty()) {
            throw new BadRequestException("Ingrese un nombre válido para agregar un nuevo Sector.");
        }
        
        boolean saved = sectorService.createSector(sectorReq);
       
        BooleanResultDTO response  = new BooleanResultDTO(saved);
        
        if(saved)
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
        else
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);     
    }
    
    
   //------------------- Update a Sector -------------------------------------------------  
    
    @RequestMapping(value = "/sectors/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BooleanResultDTO> editSector(@PathVariable("id") long sectorId,
    		                                           @RequestBody @Validated Sector sectorReq) {
    	log.debug("Fetching & Updating Sector with Id " + sectorId);
        
    	Sector s = sectorService.getSector(sectorId);
    	s.setName(sectorReq.getName());
    	
    	boolean saved = sectorService.updateSector(s);
        
    	BooleanResultDTO response  = new BooleanResultDTO(saved);
        
        if(saved)
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
        else
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT); 
    } 
    
    
   //------------------- Delete a Sector-------------------------------------------------  
    
    @RequestMapping(value = "/sectors/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BooleanResultDTO> deleteClient(@PathVariable("id") long sectorId) {
    	log.debug("Fetching to attempt delete Sector with Id " + sectorId);
        
    	sectorService.removeSector(sectorId);  // if try to remove a used Sector will return an Error 500
        
        BooleanResultDTO responseSuccessful = new BooleanResultDTO(true);
        return new ResponseEntity<BooleanResultDTO>(responseSuccessful, HttpStatus.OK);
    }
  
 
}