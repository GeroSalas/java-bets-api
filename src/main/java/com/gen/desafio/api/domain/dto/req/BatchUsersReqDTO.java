package com.gen.desafio.api.domain.dto.req;


import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class BatchUsersReqDTO extends BaseDTO {

	@Valid // If huge CSV file contains a little error will prevent all the transaction
	private List<AddUserReqDTO> users = new ArrayList<AddUserReqDTO>();
	
	
	public BatchUsersReqDTO() {}


	public List<AddUserReqDTO> getUsers() {
		return users;
	}
	public void setUsers(List<AddUserReqDTO> users) {
		this.users = users;
	}
	
	
}
