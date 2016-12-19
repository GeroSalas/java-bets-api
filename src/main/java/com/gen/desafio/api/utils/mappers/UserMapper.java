package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.List;

import com.gen.desafio.api.domain.dto.req.AddUserReqDTO;
import com.gen.desafio.api.domain.dto.req.BatchUsersReqDTO;
import com.gen.desafio.api.domain.dto.res.ClientResponseDTO;
import com.gen.desafio.api.domain.dto.res.UserResponseDTO;
import com.gen.desafio.api.domain.model.AuthorityRole;
import com.gen.desafio.api.domain.model.Client;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;
import com.gen.desafio.api.utils.security.SecurityUtils;


public class UserMapper extends ObjectMapper {
	
	
	public static UserResponseDTO buildCustomUserDTOResponse(User user){
    	// Set UserFlyweight properties
		UserResponseDTO dto = new UserResponseDTO();
    	 dto.setUserId(user.getId());
    	 dto.setFirstname(user.getFirstname());
    	 dto.setLastname(user.getLastname());
    	 dto.setUsername(user.getUsername());
    	 dto.setProfileImage(user.getImage());
    	 for(AuthorityRole r : user.getRoles()){
    		dto.getRoles().add(r.getRolename());
    	 }
    	 
     	 if(user.hasRole(AuthoritiesConstants.ADMIN)){
	       	 ClientResponseDTO cli = new ClientResponseDTO(user.getRelatedClient().getId(), user.getRelatedClient().getName(), user.getRelatedClient().getEmail());
	       	  cli.setLogoImage(user.getRelatedClient().getLogoImage());
	       	  cli.setBackImage(user.getRelatedClient().getBackImage());
	       	 dto.setClientOwner(cli);
	       	 dto.setRelatedSettings(UserSettingsMapper.buildCustomSettingsDTO(user.getRelatedSettings()));
     	 }
     	 
     	 if(user.hasRole(AuthoritiesConstants.USER)){
     		 dto.setAge(user.getAge());
	       	 dto.setGender(user.getGender());
	       	 ClientResponseDTO cli = new ClientResponseDTO(user.getRelatedClient().getId(), user.getRelatedClient().getName(), user.getRelatedClient().getEmail());
	       	  cli.setLogoImage(user.getRelatedClient().getLogoImage());
	       	  cli.setBackImage(user.getRelatedClient().getBackImage());
	       	 dto.setClientOwner(cli);
	       	 dto.setCompanySector(user.getRelatedSector().getName());
	       	 dto.setRelatedSettings(UserSettingsMapper.buildCustomSettingsDTO(user.getRelatedSettings()));
	       	 dto.setRankingPoints(user.getPoints()); 
	       	 dto.setAgreeTutorial(user.isAgreeTutorial());
	       	 if(!user.isAgreeTutorial()){
	       		// App intro carrousel mobile tutorial images
	       		dto.getTutorialImages().add(user.getRelatedClient().getTutorialImage1());
	       		dto.getTutorialImages().add(user.getRelatedClient().getTutorialImage2());
	       		dto.getTutorialImages().add(user.getRelatedClient().getTutorialImage3());
	       		dto.getTutorialImages().add(user.getRelatedClient().getTutorialImage4());
	       	 }
    	 }
    	
    	return dto;
    }
    
	
    public static List<UserResponseDTO> buildCustomUsersDTOResponse(List<User> users){
    	List<UserResponseDTO> dtos = new ArrayList<UserResponseDTO>();
    	for(User u : users){
    	 // Set UserFlyweight properties
         UserResponseDTO dto = new UserResponseDTO();
         dto.setUserId(u.getId());
         dto.setFirstname(u.getFirstname());
    	 dto.setLastname(u.getLastname());
       	 dto.setUsername(u.getUsername());
       	 dto.setProfileImage(u.getImage());
       	 for(AuthorityRole r : u.getRoles()){
    		dto.getRoles().add(r.getRolename());
    	 }
       	 	 
       	 if(u.hasRole(AuthoritiesConstants.USER)){
    		 dto.setAge(u.getAge());
	       	 dto.setGender(u.getGender());
	       	 dto.setCompanySector(u.getRelatedSector().getName());
	       	 dto.setRankingPoints(u.getPoints()); 
   	     }
   	
       	 dtos.add(dto);
    	}
    	
    	return dtos;
    }
    
    
    
    public static List<User> matchUsersBatchRequest(Client clientOwner, BatchUsersReqDTO usersBatchReq){
        List<User> entities = new ArrayList<User>();
    	
        for(AddUserReqDTO u : usersBatchReq.getUsers()){
	       AuthorityRole role = new AuthorityRole(AuthoritiesConstants.USER);  // ADMIN always creates only USER new child roles
	       User userEntity = new User();
	       	 userEntity.setUsername(u.getUsername());
	         userEntity.setFirstname(u.getFirstname());
	       	 userEntity.setLastname(u.getLastname());
	       	 userEntity.setPassword(SecurityUtils.generateRandomPassword());
	       	 userEntity.setGender(u.getGender()); 
	       	 userEntity.setAge(u.getAge());
	       	 userEntity.setOnPlay(true);
	       	 userEntity.setPoints(0);
	       	 userEntity.setRelatedSector(new Sector(u.getCompanySector()));  // example: 'Compras' => 2
	       	 userEntity.setRelatedSettings(clientOwner.getAdminUser().getRelatedSettings());
	       	 role.setRelatedUser(userEntity);
	       	 userEntity.getRoles().add(role);
	       	 clientOwner.getUsers().add(userEntity);
	       	 userEntity.setRelatedClient(clientOwner);  // bidirectional entities mapping requires this way
	       	 
	       	entities.add(userEntity);
       }
        
       return entities;
    }
    
    
    public static List<UserResponseDTO> buildRankingUsersDTOResponse(List<User> users){
    	List<UserResponseDTO> dtos = new ArrayList<UserResponseDTO>();
    	for(User u : users){
         if(u.isOnPlay() && u.hasRole(AuthoritiesConstants.USER)){
        	 UserResponseDTO dto = new UserResponseDTO();
        	 dto.setUserId(u.getId());
             dto.setFirstname(u.getFirstname());
        	 dto.setLastname(u.getLastname());
        	 dto.setProfileImage(u.getImage());
        	 dto.setCompanySector(u.getRelatedSector().getName());
        	 dto.setRankingPoints(u.getPoints());
        	 
        	 dtos.add(dto);
         }
   	
    	}
    	
    	return dtos;
    }
    
    
    public static UserResponseDTO buildMinifiedUserDTOResponse(User user){
    	UserResponseDTO dto = new UserResponseDTO();
    	 dto.setUserId(user.getId());
    	 dto.setUsername(user.getUsername());
    	 dto.setFirstname(user.getFirstname());
    	 dto.setLastname(user.getLastname());
    	 dto.setProfileImage(user.getImage());
    	 if(!user.hasRole(AuthoritiesConstants.SUPER_ADMIN)){
    		 dto.setClientName(user.getRelatedClient().getName()); 
    	 }
    	 
    	 if(user.hasRole(AuthoritiesConstants.USER)) dto.setCompanySector(user.getRelatedSector().getName());
    	
    	return dto;
    }
    
}
