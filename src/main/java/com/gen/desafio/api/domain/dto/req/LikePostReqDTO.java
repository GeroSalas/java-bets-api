package com.gen.desafio.api.domain.dto.req;


import org.hibernate.validator.constraints.NotEmpty;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class LikePostReqDTO extends BaseDTO {
	
	@NotEmpty(message = "Por favor ingrese ID de publicación donde desea dar Like.")
	private Long parentPostId;
	
	// isLike ==> TRUE
	
	public LikePostReqDTO() {}


	public Long getParentPostId() {
		return parentPostId;
	}

	public void setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
	}

	
}
