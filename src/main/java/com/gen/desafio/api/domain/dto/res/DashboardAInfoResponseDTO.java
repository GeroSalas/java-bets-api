package com.gen.desafio.api.domain.dto.res;

import com.gen.desafio.api.domain.dto.BaseDTO;

public class DashboardAInfoResponseDTO extends BaseDTO {

	private int clientsCount;
	
	
	public DashboardAInfoResponseDTO() {}


	public int getClientsCount() {
		return clientsCount;
	}
	public void setClientsCount(int clientsCount) {
		this.clientsCount = clientsCount;
	}
	
	
}
