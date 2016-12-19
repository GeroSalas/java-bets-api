package com.gen.desafio.api.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.gen.desafio.api.domain.model.Sector;


@Service
public interface SectorService extends BaseService {
	
	 Sector getSector(long sectorId);
	 boolean updateSector(Sector sector);
	 boolean createSector(Sector sector);
	 List<Sector> listSectors();
	 List<Sector> listSectors(long clientId);
	 void removeSector(long sectorId);

}
