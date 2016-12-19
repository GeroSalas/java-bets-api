package com.gen.desafio.api.domain.dto.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gen.desafio.api.domain.dto.BaseDTO;


@JsonInclude(Include.NON_NULL)
public class CommentResponseDTO extends BaseDTO {

	private Long id;
	private String text;
	private String commentedDate;
	private UserResponseDTO relatedUser;
	private Boolean isMine;
	
	
	public CommentResponseDTO() {}


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(String commentedDate) {
		this.commentedDate = commentedDate;
	}

	public UserResponseDTO getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(UserResponseDTO relatedUser) {
		this.relatedUser = relatedUser;
	}

	public Boolean isMine() {
		return isMine;
	}

	public void setMine(Boolean isMine) {
		this.isMine = isMine;
	}

	
}
