package com.gen.desafio.api.domain.dto.res;


import com.gen.desafio.api.domain.dto.BaseDTO;
import com.gen.desafio.api.domain.model.Client;


public class UserBetNotificationResponseDTO extends BaseDTO {

	private long id;
	private String result;
	private int points;
	
	
	public UserBetNotificationResponseDTO() {}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserBetNotificationResponseDTO dto = (UserBetNotificationResponseDTO) o;

        if (!result.equals(dto.result)) {
            return false;
        }

        return true;
    }
	
	@Override
    public int hashCode() {
        return result.hashCode();
    }
	
}
