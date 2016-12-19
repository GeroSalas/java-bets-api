package com.gen.desafio.api.utils.mappers;


import com.gen.desafio.api.domain.dto.res.SettingsResponseDTO;
import com.gen.desafio.api.domain.model.UserPreferences;


public class UserSettingsMapper extends ObjectMapper {
	
	public static SettingsResponseDTO buildCustomSettingsDTO(UserPreferences uPreferences){
		SettingsResponseDTO dto = new SettingsResponseDTO();
		 dto.getStyle1().setBackgroundColor(uPreferences.getStyle1Color());
		 dto.getStyle2().setBackgroundColor(uPreferences.getStyle2Color());
		 dto.getStyle3().setBackgroundColor(uPreferences.getStyle3Color());
		 dto.getStyle4().setBackgroundColor(uPreferences.getStyle4Color());
		 dto.getStyle5().setTextColor(uPreferences.getStyle5Color());
		 
		 return dto;
	}
	
}
