package com.gen.desafio.api.services.impl;


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.desafio.api.dal.PostDAO;
import com.gen.desafio.api.dal.SectorDAO;
import com.gen.desafio.api.domain.exception.ApplicationException;
import com.gen.desafio.api.domain.exception.DuplicateKeyException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.exception.UnauthorizedException;
import com.gen.desafio.api.domain.model.Like;
import com.gen.desafio.api.domain.model.Post;
import com.gen.desafio.api.domain.model.Sector;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.PostService;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
 
	
    @Autowired 
    private PostDAO postRepository;
    
    @Autowired 
    private SectorDAO sectorRepository;


	@Override
	public boolean createPost(Post post) {
		// Business Rules
    	boolean isSaved = false;
    	
	    try {
	    	if(post.getRelatedUser().hasRole(AuthoritiesConstants.USER)){
	    		List<Sector> clientSectors = sectorRepository.getClientSectors(post.getRelatedUser().getRelatedClient().getId());
	    		for(Sector s : clientSectors){
					 post.getRelatedSectors().add(new Sector(s.getId()));
				 }
	    	}
	    	post = postRepository.saveAndFlush(post);
	    	isSaved = true;
			log.info("Post successfully saved");
	    }
	    catch(Exception ex) {
			log.error("Error - " + ex.getMessage());
			throw ex;
		}
		
		return isSaved;
	}
	
	@Override
	public boolean modifyPost(Post post, User owner) {
		// Business Rules
    	boolean isSaved = false;

		if(post!=null && !post.getIsComment()) {
			try {
				
				if(post.getRelatedUser().equals(owner) || owner.hasRole(AuthoritiesConstants.ADMIN)){
					post = postRepository.saveAndFlush(post);
					isSaved = true;
					log.info("Post successfully updated");
	    		}
	    		else{
	    			throw new UnauthorizedException("No tiene permisos para modificar esta publicación.");
	    		}
				
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}
		else{
			throw new RecordNotFoundException("No se puede modificar esta publicación.");
		} 
		
		return isSaved;		
	}


	@Override
	public boolean addComment(Post comment, long postId) {
		// Business Rules
    	boolean isSaved = false;
    	Post parentPost = postRepository.findOne(postId);
    	if(parentPost!=null && !parentPost.getIsComment()) {
    		if(parentPost.getRelatedUser().equals(comment.getRelatedUser())){
    			comment.setNotified(true);
    		}
    		parentPost.getComments().add(comment);
			comment.setParentPost(parentPost);
			postRepository.saveAndFlush(parentPost);
			isSaved = true;
			log.info("Post comment successfully saved");
		}
		else{
			throw new RecordNotFoundException("Publicación no encontrada para comentar.");
		} 
		
		return isSaved;
	}
	
	
	@Override
	public boolean addLike(Like like, long postId) {
		// Business Rules
    	boolean isSaved = false;
    	Post parentPost = postRepository.findOne(postId);
    	if(parentPost!=null && !parentPost.getIsComment()) {
    		for(Like l : parentPost.getRelatedLikes()){
    			if(l.getRelatedUser().getId()==like.getRelatedUser().getId()){
    				throw new DuplicateKeyException("Usted ya dio like en esta publicación.");
    			}
    		}
    		if(parentPost.getRelatedUser().getId().equals(like.getRelatedUser().getId())){
    			like.setNotified(true);
    		}
    		parentPost.getRelatedLikes().add(like);
    		like.setRelatedPost(parentPost);
			postRepository.saveAndFlush(parentPost);
			isSaved = true;
			log.info("Post liked successfully saved");
		}
		else{
			throw new RecordNotFoundException("Publicación no encontrada para dar Like.");
		} 
		
		return isSaved;
	}

	
	@Override
	public boolean disLike(long postId, User currentUser) {
		// Business Rules
    	boolean isDeleted = false;
    	Post parentPost = postRepository.findOne(postId);
    	if(parentPost!=null  && !parentPost.getIsComment()) {
    		for(Iterator<Like> iterator = parentPost.getRelatedLikes().iterator(); iterator.hasNext();) {
    			Like l = iterator.next();
    			if(l.getRelatedUser().getId() == currentUser.getId()){
    				l.setRelatedPost(null);
    				iterator.remove();  // remove Like here
    				break;
    			}
			}
			postRepository.saveAndFlush(parentPost);
			isDeleted = true;
			log.info("Post disliked successfully deleted");
		}
		else{
			throw new RecordNotFoundException("Publicación no encontrada para dar dislike.");
		} 
		
		return isDeleted;
	}


	@Override
	public List<Post> listPostsByUser(User user) {
		return postRepository.findPostsByUser(user.getId());
	}


	@Override
	public void removePost(long id, User owner) {
		// Business Rules
		Post validOne = postRepository.findOne(id);
		if(validOne==null || validOne.getIsComment()) {
			log.info("Could not found valid Post info for: " + id);
			throw new RecordNotFoundException("Publicación no encontrada.");
		}
		else{
	    	try {
	    		if(validOne.getRelatedUser().equals(owner) || owner.hasRole(AuthoritiesConstants.ADMIN)){
	    			postRepository.delete(validOne);
	    			log.info("Post deleted successfully");
	    		}
	    		else{
	    			throw new UnauthorizedException("No tiene permisos para eliminar esta publicación.");
	    		}
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}		
	}
	
	
	@Override
	public Post findOne(long postId, User currentUser) {
		Post post = postRepository.findOne(postId);
		if(post!=null && !post.getIsComment()) {
			try {
	    		if(post.getRelatedUser().equals(currentUser)){
	    			// View notifications if owns it
	    			for(Post c : post.getComments()){
	    				c.setNotified(true);
	    			}
	    			for(Like l : post.getRelatedLikes()){
	    				l.setNotified(true);
	    			}
	    			post = postRepository.saveAndFlush(post);
	    			log.info("Post successfully fully viewed and saved");
	    		}

			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}
		else{
			throw new RecordNotFoundException("Publicación no encontrada.");
		} 
		
		return post; 
	}


	@Override
	public void removeCommentPost(long pId, long cId, User owner) {
		// Business Rules
    	Post parentPost = postRepository.findOne(pId);
    	if(parentPost!=null  && !parentPost.getIsComment()) {
    		for(Iterator<Post> iterator = parentPost.getComments().iterator(); iterator.hasNext();) {
    			Post c = iterator.next();
    			if(c.getId()==cId){
    				if(c.getRelatedUser().equals(owner) || owner.hasRole(AuthoritiesConstants.ADMIN)){
    					c.setParentPost(null);
        				iterator.remove();  // remove Comment from Post here
        				break;
    				}
    				else{
    					throw new UnauthorizedException("No tiene permisos para eliminar este comentario.");
    				}
    			}
			}
			postRepository.saveAndFlush(parentPost);
			log.info("Post's comment successfully deleted");
		}
		else{
			throw new RecordNotFoundException("Publicación no encontrada para dar eliminar comentario.");
		} 
		
	}

    
}




