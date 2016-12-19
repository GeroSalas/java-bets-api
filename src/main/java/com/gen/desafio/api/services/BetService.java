package com.gen.desafio.api.services;


import java.util.List;
import org.springframework.stereotype.Service;
import com.gen.desafio.api.domain.dto.req.BetResultReqDTO;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.domain.model.UserBet;


/**
 * 
 * THIS WILL BE USED AS UserBetService TOO
 * 
 * @author GeronimoEzequiel
 *
 */

@Service
public interface BetService extends BaseService {
	
	 UserBet find(long userId, long betId);
	 boolean createBet(BetResultReqDTO userbet, User user);
	 boolean modifyBet(BetResultReqDTO userbet, User user);
     List<UserBet> listUserBets(long userId);
     void viewNotifications(long betId, User user);

}
