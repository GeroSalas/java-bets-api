package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gen.desafio.api.domain.dto.req.CommentPostReqDTO;
import com.gen.desafio.api.domain.dto.req.PostReqDTO;
import com.gen.desafio.api.domain.dto.res.CommentResponseDTO;
import com.gen.desafio.api.domain.dto.res.PostNotificationResponseDTO;
import com.gen.desafio.api.domain.dto.res.PostResponseDTO;
import com.gen.desafio.api.domain.model.Like;
import com.gen.desafio.api.domain.model.Post;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


public class PostMapper extends ObjectMapper {
	
	
	public static Post convertPostDTOToEntity(PostReqDTO pReq, User currentUser){
		Post entity = new Post();
		 entity.setDate(new Date());
		 entity.setText(pReq.getTextContent());
		 entity.setRelatedUser(currentUser); 
		 if(pReq.getImages().size() > 0){
			for(int i=0; i<pReq.getImages().size(); i++){
				if(i==0) entity.setImageOne((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==1) entity.setImageTwo((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==2) entity.setImageThree((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==3) entity.setImageFour((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==4) entity.setImageFive((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==5) entity.setImageSix((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==6) entity.setImageSeven((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
				if(i==7) entity.setImageEight((pReq.getImages().get(i)!=null) ? pReq.getImages().get(i).toString() : null);
			}
		 }
		 
		 // Post_Sector mappings
		 if(pReq.getSectors().size() > 0 && currentUser.hasRole(AuthoritiesConstants.ADMIN)){
			 for(int j=0; j<pReq.getSectors().size(); j++){
				 long sectorId = pReq.getSectors().get(j);
				 entity.getRelatedSectors().add(new Sector(sectorId));
			 }
		 }
		 
		 entity.setIsComment(false);
		 entity.setNotified(false);
		 entity.setParentPost(null);  // Is not a simple comment
		 
		return entity;
	}
	
	
	public static Post convertPostCommentDTOToEntity(CommentPostReqDTO pcReq, User currentUser){
		Post entity = new Post();
		 entity.setDate(new Date());
		 entity.setText(pcReq.getTextContent());
		 entity.setRelatedUser(currentUser);
		 entity.setIsComment(true);
		 entity.setNotified(false);
		 entity.setParentPost(null);  // Will be set on Service Layer before saved
		 
		return entity;
	}
	
	
	public static PostResponseDTO buildCustomSinglePostDTO(Post post, User currentUser){
		PostResponseDTO dto = new PostResponseDTO();
			if(!post.getIsComment() && post.getParentPost()==null){
				dto.setId(post.getId());
				dto.setPostedDate(post.getDate().toInstant().toString());
				dto.setRelatedUser(UserMapper.buildMinifiedUserDTOResponse(post.getRelatedUser()));
				dto.setText(post.getText());
				dto.setLikes(post.getRelatedLikes().size());
				dto.setLiked(false);
				// Check if the user logged already liked this Post
				for(Like l : post.getRelatedLikes()){
					if(l.getRelatedUser().getId().longValue()==currentUser.getId().longValue()){
						dto.setLiked(true);
					}
				}
				
				// Add posted images urls from AWS-S3 bucket
				if(post.getImageOne()!=null) dto.getImages().add(post.getImageOne());
				if(post.getImageTwo()!=null) dto.getImages().add(post.getImageTwo());
				if(post.getImageThree()!=null) dto.getImages().add(post.getImageThree());
				if(post.getImageFour()!=null) dto.getImages().add(post.getImageFour());
				if(post.getImageFive()!=null) dto.getImages().add(post.getImageFive());
				if(post.getImageSix()!=null) dto.getImages().add(post.getImageSix());
				if(post.getImageSeven()!=null) dto.getImages().add(post.getImageSeven());
				if(post.getImageEight()!=null) dto.getImages().add(post.getImageEight());
				
				// Fetch / Add child comments to the related parent Post
				for(Post c : post.getComments()){
					CommentResponseDTO childComment = new CommentResponseDTO();
					 childComment.setId(c.getId());
					 childComment.setCommentedDate(c.getDate().toInstant().toString());
					 childComment.setText(c.getText());
					 childComment.setRelatedUser(UserMapper.buildMinifiedUserDTOResponse(c.getRelatedUser()));
					 if(currentUser.equals(c.getRelatedUser())){
						 childComment.setMine(true);	 
					 }
					 else{
						 childComment.setMine(false);
					 }
					 
					 dto.getComments().add(childComment);	
				}
				
			}
		
		return dto;
	}
	
	
	public static List<PostResponseDTO> buildCustomPostsDTO(List<Post> recentPosts, User currentUser){
		List<PostResponseDTO> dtos = new ArrayList<PostResponseDTO>();
		for(Post p : recentPosts){
			PostResponseDTO dto = buildCustomSinglePostDTO(p, currentUser);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	
	public static List<PostNotificationResponseDTO> buildCustomPostNotificationsDTO(List<Post> notiPosts){
		List<PostNotificationResponseDTO> dtos = new ArrayList<PostNotificationResponseDTO>();
		for(Post p : notiPosts){
			if(p.getComments().size() > 0){
				PostNotificationResponseDTO dtoC = new PostNotificationResponseDTO();
				 dtoC.setAction("Comment");
				 dtoC.setRelatedPost(p.getId());
				for(Post c : p.getComments()){
					if(!c.isNotified()){
					    dtoC.getReactUsers().add(c.getRelatedUser().getFullName());
					}
				}
				// Solo si hay reacciones no notificadas
				if(dtoC.getReactUsers().size()>0)  dtos.add(dtoC);
			}
			
			if(p.getRelatedLikes().size() > 0){
				PostNotificationResponseDTO dtoL = new PostNotificationResponseDTO();
				 dtoL.setAction("Like");
				 dtoL.setRelatedPost(p.getId());
				for(Like l : p.getRelatedLikes()){
					if(!l.isNotified()){
						dtoL.getReactUsers().add(l.getRelatedUser().getFullName());
					}
				}
				// Solo si hay reacciones no notificadas
				if(dtoL.getReactUsers().size()>0)  dtos.add(dtoL);
			}
		}
		
		return dtos;
	}
	
	
}
