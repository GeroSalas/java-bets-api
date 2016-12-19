package com.gen.desafio.api.services;


import org.springframework.stereotype.Service;

import com.gen.desafio.api.domain.dto.res.CustomerTimelineResponseDTO;
import com.gen.desafio.api.domain.dto.res.DashboardCInfoResponseDTO;
import com.gen.desafio.api.domain.dto.res.MobileTimelineResponseDTO;
import com.gen.desafio.api.domain.model.User;


@Service
public interface ProfileService extends BaseService {
	
	//DashboardAInfoResponseDTO retrieveAdminProfileInfo(User superAdmin);
	 DashboardCInfoResponseDTO retrieveClientProfileInfo(User adminClient);
	 CustomerTimelineResponseDTO retrieveCustomerProfileInfo(User adminUser, int page);
	 MobileTimelineResponseDTO retrieveMobileProfileInfo(User mobileUser, int page);

}
