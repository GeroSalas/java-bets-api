package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.List;

import com.gen.desafio.api.domain.dto.req.AddClientReqDTO;
import com.gen.desafio.api.domain.dto.res.ClientResponseDTO;
import com.gen.desafio.api.domain.model.AuthorityRole;
import com.gen.desafio.api.domain.model.Client;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;
import com.gen.desafio.api.utils.security.SecurityUtils;


public class ClientMapper extends ObjectMapper {
	
	
	public static Client matchClientRequest(AddClientReqDTO dto){
    	Client clientEntity = new Client(dto.getCompanyName().toUpperCase(), dto.getCompanyEmail());
    	AuthorityRole adminRole = new AuthorityRole(AuthoritiesConstants.ADMIN);
    	User customerAdmin = new User();  //  This will be the Admin user who manages the Customer Dashboard (not player)
    	 customerAdmin.setFirstname(dto.getAdminFirstname().toUpperCase());
    	 customerAdmin.setLastname(dto.getAdminLastname().toUpperCase());
    	 customerAdmin.setUsername(dto.getAdminUsername());  // admin email
    	 customerAdmin.setPassword(SecurityUtils.generateRandomPassword());
    	 customerAdmin.setEnabled((byte) 1);
    	 customerAdmin.setOnPlay(false);
    	 adminRole.setRelatedUser(customerAdmin);
    	 customerAdmin.getRoles().add(adminRole);
    	 clientEntity.getUsers().add(customerAdmin);
    	 clientEntity.setLogoImage(dto.getLogoImage());
    	 clientEntity.setBackImage(dto.getBackImage());
    	 customerAdmin.setRelatedClient(clientEntity);
    	 
    	 if(dto.getTutoImage1()!=null && dto.getTutoImage2()!=null && dto.getTutoImage3()!=null && dto.getTutoImage4()!=null){
    		 clientEntity.setTutorialImage1(dto.getTutoImage1());
    		 clientEntity.setTutorialImage2(dto.getTutoImage2());
    		 clientEntity.setTutorialImage3(dto.getTutoImage3());
    		 clientEntity.setTutorialImage4(dto.getTutoImage4());
    	 }
    	
    	return clientEntity;
    }
	
	
	public static ClientResponseDTO buildCustomClientDTOResponse(Client entity){
    	ClientResponseDTO dto = new ClientResponseDTO();
    	 dto.setClientId(entity.getId());
    	 dto.setName(entity.getName());
    	 dto.setEmail(entity.getEmail());
    	 dto.setLogoImage(entity.getLogoImage());
       	 dto.setBackImage(entity.getBackImage());
       	 int usersCount = 0;
    	 
       	 for(User u : entity.getUsers()){
    		 
       		 if(u.hasRole(AuthoritiesConstants.ADMIN)){
	       		dto.setAdminName(u.getFullName());
	       		dto.setAdminUsername(u.getUsername());
	       		dto.setRelatedSettings(UserSettingsMapper.buildCustomSettingsDTO(u.getRelatedSettings()));  //  client styles
	       	 }
    		
    		 usersCount++;
    	 }   
       	 
    	dto.setUsersCount(usersCount);
    	return dto;
    }
	
	
	public static List<ClientResponseDTO> buildCustomClientsDTOResponse(List<Client> entities){
    	List<ClientResponseDTO> dtos = new ArrayList<ClientResponseDTO>();
    	for(Client c : entities){
    		ClientResponseDTO dto = buildCustomClientDTOResponse(c);
	       	 dtos.add(dto);
    	}
    	  	 
    	return dtos;
    }
	
	
}
