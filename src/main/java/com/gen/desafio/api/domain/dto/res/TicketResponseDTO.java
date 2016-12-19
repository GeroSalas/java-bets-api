package com.gen.desafio.api.domain.dto.res;


import java.util.ArrayList;
import java.util.List;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class TicketResponseDTO extends BaseDTO {

	private long id;
	private String title;
	private String description;
	private String postedDate;
	private UserResponseDTO relatedUser;
	private List<CommentResponseDTO> comments = new ArrayList<CommentResponseDTO>();
	
	
	public TicketResponseDTO() {}

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public UserResponseDTO getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(UserResponseDTO relatedUser) {
		this.relatedUser = relatedUser;
	}

	public List<CommentResponseDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponseDTO> comments) {
		this.comments = comments;
	}

	
}
