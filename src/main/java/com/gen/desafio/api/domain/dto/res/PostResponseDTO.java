package com.gen.desafio.api.domain.dto.res;


import java.util.ArrayList;
import java.util.List;
import com.gen.desafio.api.domain.dto.BaseDTO;


public class PostResponseDTO extends BaseDTO {

	private long id;
	private String text;
	private String postedDate;
	private Integer likes;
	private boolean isLiked;
	private List<String> images = new ArrayList<String>();
	private UserResponseDTO relatedUser;
	private List<CommentResponseDTO> comments = new ArrayList<CommentResponseDTO>();
	
	
	public PostResponseDTO() { }


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<CommentResponseDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponseDTO> comments) {
		this.comments = comments;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	
	
}
