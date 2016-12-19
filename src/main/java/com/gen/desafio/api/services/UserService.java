package com.gen.desafio.api.services;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.gen.desafio.api.domain.dto.res.UserBatchResponseDTO;
import com.gen.desafio.api.domain.model.User;


@Service
public interface UserService extends BaseService, UserDetailsService {
	
	 User find(long userId, long clientId);                 // GET  FIND ONE
	 User addUser(User user);                               // POST INSERT (SINGLE)
	 List<UserBatchResponseDTO> addUsers(List<User> users); // POST INSERT (BATCH)
	 User modifyUser(User user);                            // PUT  PATCH  UPDATE
     void removeUser(long id);                              // DELETE ONE
     List<User> listUsers(long clientId);                   // GET  SELECT LIST ALL
     int rankUserLogged(long userId, long clientId);
     boolean checkLoginCredentials(String username, String password);
     boolean addPushbotsInfo(User user);

}
