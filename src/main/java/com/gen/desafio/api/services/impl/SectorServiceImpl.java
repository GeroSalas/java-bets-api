package com.gen.desafio.api.services.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gen.desafio.api.dal.SectorDAO;
import com.gen.desafio.api.domain.exception.DuplicateKeyException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.services.SectorService;



@Service
@Transactional
public class SectorServiceImpl implements SectorService {
	
	private static final Logger log = LoggerFactory.getLogger(SectorServiceImpl.class);
 
    @Autowired 
    private SectorDAO sectorRepository;

    
    
    @Override
	public Sector getSector(long id) {    	
    	Sector sector = sectorRepository.findOne(id);
		if (sector == null) {			
			throw new RecordNotFoundException("Sector no encontrado con ID " + id);
		}
		
		return sector;
	}
    
    
	@Override
	public boolean createSector(Sector sector) {
		// Business Rules
    	boolean isSaved = false;
    	
	    try {
	    	List<Sector> oldSectors = sectorRepository.findAll();
	    	for(Sector s : oldSectors){
	    		if(s.getName().equalsIgnoreCase(sector.getName())){
	    			throw new DuplicateKeyException("Ya existe un sector con ese nombre.");
	    		}
	    	}
	    	sectorRepository.saveAndFlush(sector);
	    	isSaved = true;
			log.info("Sector successfully saved");
	    }
	    catch(Exception ex) {
			log.error("Error - " + ex.getMessage());
			throw ex;
		}
		
		return isSaved;
	}
	
	
	@Override
	public boolean updateSector(Sector sector) {
		// Business Rules
    	boolean isUpdated = false;
    	
	    try {
	    	List<Sector> oldSectors = sectorRepository.findAll();
	    	for(Sector s : oldSectors){
	    		if(s.getName().equalsIgnoreCase(sector.getName())){
	    			throw new DuplicateKeyException("Ya existe un sector con ese nombre.");
	    		}
	    	}
	    	sectorRepository.saveAndFlush(sector);
	    	isUpdated = true;
			log.info("Sector successfully updated");
	    }
	    catch(Exception ex) {
			log.error("Error - " + ex.getMessage());
			throw ex;
		}
		
		return isUpdated;
	}

	
	@Override
	public List<Sector> listSectors() {
		return sectorRepository.findAll();
	}
	
	
	@Override
	public List<Sector> listSectors(long clientId) {
		return sectorRepository.getClientSectors(clientId);
	}
	
	
    @Override
	public void removeSector(long id) {
		Sector validOne = sectorRepository.findOne(id);
		if(validOne==null) {
			log.info("Could not found valid Sector with Id: " + id);
			throw new RecordNotFoundException("Sector incorrecto.");
		}
		else{
	    	try {
	    		sectorRepository.delete(validOne);
			} catch (Exception ex) { 
				log.error("Error - " + ex.getMessage());
				//throw new InvalidRequestException("No se puede eliminar un Sector que ya está siendo usado.");
				throw ex;
			}
		}		
	}
	
}




