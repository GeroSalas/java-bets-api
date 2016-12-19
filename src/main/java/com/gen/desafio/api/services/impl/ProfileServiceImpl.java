package com.gen.desafio.api.services.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.desafio.api.dal.BetDAO;
import com.gen.desafio.api.dal.ClientDAO;
import com.gen.desafio.api.dal.MatchDAO;
import com.gen.desafio.api.dal.PostDAO;
import com.gen.desafio.api.dal.UserBetDAO;
import com.gen.desafio.api.dal.UserDAO;
import com.gen.desafio.api.domain.dto.res.CustomerTimelineResponseDTO;
import com.gen.desafio.api.domain.dto.res.DashboardCInfoResponseDTO;
import com.gen.desafio.api.domain.dto.res.MobileTimelineResponseDTO;
import com.gen.desafio.api.domain.model.Match;
import com.gen.desafio.api.domain.model.Post;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.domain.model.UserBet;
import com.gen.desafio.api.services.ProfileService;
import com.gen.desafio.api.utils.mappers.MatchMapper;
import com.gen.desafio.api.utils.mappers.PostMapper;
import com.gen.desafio.api.utils.mappers.UserBetMapper;
import com.gen.desafio.api.utils.mappers.UserMapper;


@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
	
	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);
 
    @Autowired 
    private ClientDAO clientRepository;
    
    @Autowired 
    private UserDAO userRepository;
    
    @Autowired 
    private BetDAO betRepository;
    
    @Autowired 
    private UserBetDAO userBetRepository;
    
    @Autowired 
    private MatchDAO matchRepository;
    
    @Autowired 
    private PostDAO postRepository;

    
    /**
    @Override
	public DashboardCInfoResponseDTO retrieveAdmintProfileInfo() {
    	//  Not implemented yet !
    	//  SuperAdmin Dashboard main information 
    }
    */
    
    
	@Override
	public DashboardCInfoResponseDTO retrieveClientProfileInfo(User adminClient) {
		DashboardCInfoResponseDTO dto = new DashboardCInfoResponseDTO();
		long currentClient = adminClient.getRelatedClient().getId();
		List<User> users = (List<User>) userRepository.findAllByClient(currentClient);
		List<User> activeUsers = (List<User>) userRepository.findAllActivesByClient(currentClient);
		List<User> top4Users = (List<User>) userRepository.findTopRankingOrderByPointsDesc(currentClient);
		Integer postsCount = postRepository.countAllPostsByClient(currentClient);
		List<Post> recent4Posts = (List<Post>) postRepository.findLast4PostsByClient(currentClient);
		Integer activesPrevMatch = userBetRepository.countActiveUsersOnPrevMatchByClient(currentClient);
		Integer activesNextMatch = userBetRepository.countActiveUsersOnNextMatchByClient(currentClient);
		Integer activesAllMatches = userBetRepository.countActiveUsersOnAllMatchesByClient(currentClient);
		List<Match> next5Matches = matchRepository.findTop5ByIsClosedFalseAndStartDateAfterOrderByStartDateAsc(new Date());
		int porcentajeActivesPrevMatch = (int) (Math.floor(activesPrevMatch * 100)/activeUsers.size());
		int porcentajeActivesNextMatch = (int) (Math.floor(activesNextMatch * 100)/activeUsers.size());
		int porcentajeActivesAllMatches = (int) (Math.floor(activesAllMatches * 100)/activeUsers.size());
	
		log.info("Successfully dashboard information retrieved. Building util DTO for Client...");
		
		dto.setUsersCount(users.size());
		dto.setActiveUsersCount(activeUsers.size());
		dto.setRankingTopUsers(UserMapper.buildRankingUsersDTOResponse(top4Users));
		dto.setPostsCount(postsCount);
		dto.setLastPosts(PostMapper.buildCustomPostsDTO(recent4Posts, adminClient));
		dto.setActivesPreviousMatch(activesPrevMatch);
		dto.setActivesNextMatch(activesNextMatch);
		dto.setActivesAllMatchs(activesAllMatches);
		dto.setPorcentageActivesPreviousMatch(porcentajeActivesPrevMatch);
		dto.setPorcentageActivesNextMatch(porcentajeActivesNextMatch);
		dto.setPorcentageActivesAllMatches(porcentajeActivesAllMatches);
		dto.setNextMatches(MatchMapper.buildNextMatchesDTOResponse(next5Matches));
		
		return dto;
	}
	
	
	@Override
	public MobileTimelineResponseDTO retrieveMobileProfileInfo(User mobileUser, int page) {
		MobileTimelineResponseDTO dto = new MobileTimelineResponseDTO();
		
		int pageOffsetter = (page-1)*30;  
		
		if(!mobileUser.isAgreeTutorial()){
			log.info("Updating mobile user tutorial agreement...");
			userRepository.setAgreeAppTutorial(mobileUser.getId(), mobileUser.getRelatedClient().getId());
		}
		
		List<Post> notiPosts = postRepository.findPostsByUser(mobileUser.getId());
		List<UserBet> userBets = userBetRepository.findBetPointsToNotifyByUser(mobileUser.getId());
		List<Post> posts = postRepository.findRecentsByCompanySector(mobileUser.getRelatedClient().getId(), mobileUser.getRelatedSector().getId(), pageOffsetter);
		
		log.info("Successfully timeline information retrieved. Building util DTO for Mobile user...");
		
		dto.setPostNotifications(PostMapper.buildCustomPostNotificationsDTO(notiPosts));
		dto.setBetNotifications(UserBetMapper.buildCustomUserBetNotificationDTO(userBets));
		dto.setRecentPosts(PostMapper.buildCustomPostsDTO(posts, mobileUser));
		
		return dto;
	}
	
	
	@Override
	public CustomerTimelineResponseDTO retrieveCustomerProfileInfo(User customerUser, int page) {
		CustomerTimelineResponseDTO dto = new CustomerTimelineResponseDTO();
		
		int pageOffsetter = (page-1)*30;  
		
		List<Post> notiPosts = postRepository.findPostsByUser(customerUser.getId());
		List<Post> posts = postRepository.findAllByClient(customerUser.getRelatedClient().getId(), pageOffsetter);
		
		log.info("Successfully timeline information retrieved. Building util DTO for Admin Client...");
		
		//dto.setPostNotifications(PostMapper.buildCustomPostNotificationsDTO(notiPosts));  NOT NECESSARY!
		dto.setRecentPosts(PostMapper.buildCustomPostsDTO(posts, customerUser));
		
		return dto;
	}
	

}



