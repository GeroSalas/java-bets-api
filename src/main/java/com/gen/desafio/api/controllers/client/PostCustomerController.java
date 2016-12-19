package com.gen.desafio.api.controllers.client;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.CommentPostReqDTO;
import com.gen.desafio.api.domain.dto.req.PostReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.dto.res.PostResponseDTO;
import com.gen.desafio.api.domain.exception.BadRequestException;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.Like;
import com.gen.desafio.api.domain.model.Post;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.PostService;
import com.gen.desafio.api.utils.EmailSender;
import com.gen.desafio.api.utils.mappers.PostMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1c")
@Secured(AuthoritiesConstants.ADMIN)
public class PostCustomerController {
	
	private final Logger log = LoggerFactory.getLogger(PostCustomerController.class);
	
	
	@Autowired
    PostService postService;
	
	
	
	//------------------- Retrieve User Single Post -----------------------------------
	
	@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostResponseDTO> retrieveUserPost(@PathVariable("id") long postId, 
			                                                @AuthenticationPrincipal User authUser) {
		log.debug("Retrieving Single Post...");
		
		Post post = postService.findOne(postId, authUser);
		
		PostResponseDTO response = PostMapper.buildCustomSinglePostDTO(post, authUser);
		
        return new ResponseEntity<PostResponseDTO>(response, HttpStatus.OK);	
	}	
	
	
	//------------------- Retrieve User Posts -----------------------------------
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PostResponseDTO>> retrieveClientPosts(@AuthenticationPrincipal User authUser) {
		log.debug("Retrieving User Posts...");
		
		List<Post> userPosts = postService.listPostsByUser(authUser);
		
		if(userPosts==null || userPosts.isEmpty()){
            return new ResponseEntity<List<PostResponseDTO>>(HttpStatus.NO_CONTENT);
        }
        else{
        	List<PostResponseDTO> response = PostMapper.buildCustomPostsDTO(userPosts, authUser);
        	return new ResponseEntity<List<PostResponseDTO>>(response, HttpStatus.OK);	
        }
	}
	
	
	//------------------- Create Post -----------------------------------
	
	@RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> createAdminPost(@RequestBody @Validated PostReqDTO postReq, BindingResult bindingResult, 
    		                                                @AuthenticationPrincipal User authUser) {
    	log.debug("Creating new User Post...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        if(postReq.getSectors().isEmpty()){
        	throw new BadRequestException("Debe indicar a que Sectores de la Empresa va dirigida esta publicación.");
        }
        
        Post p = PostMapper.convertPostDTOToEntity(postReq, authUser);
        boolean saved = postService.createPost(p);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);     
        
        if(saved) 
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        else 
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
    }
	
	
	//------------------- Edit Post (for censure purposes)-----------------------------------
	
	@RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BooleanResultDTO> editToCensurePost(@PathVariable("id") long postId,
    														  @RequestBody @Validated PostReqDTO postReq, BindingResult bindingResult, 
    		                                                  @AuthenticationPrincipal User authUser) {
    	log.debug("Editing content of User Post...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Post post = postService.findOne(postId, authUser);
         post.setText(postReq.getTextContent());
        boolean updated = postService.modifyPost(post, authUser);
        
        BooleanResultDTO response  = new BooleanResultDTO(updated);
        
        if(updated){
        	EmailSender.notifyMobileUserPostCensure(post.getRelatedUser());  //  Notify via Email
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        }
        else{
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
        }
        	
    }
	
	
	//------------------- Edit Comment (for censure purposes)-----------------------------------
	
	@RequestMapping(value = "/posts/{pid}/comments/{cid}", method = RequestMethod.PUT)
    public ResponseEntity<BooleanResultDTO> editToCensureComment(@PathVariable("pid") long postId, 
    															 @PathVariable("cid") long commentId, 
    															 @RequestBody @Validated CommentPostReqDTO cReq, 
    															 BindingResult bindingResult, 
    															 @AuthenticationPrincipal User authUser) {
    	log.debug("Editing content of User comment...");
    	
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Post post = postService.findOne(postId, authUser);
        boolean found = false;
         for(Post comment : post.getComments()){
        	 if(comment.getId().equals(commentId)){
        		 comment.setText(cReq.getTextContent());
        		 found = true;
        		 break;
        	 }
         }
        
        if(found){
	        boolean updated = postService.modifyPost(post, authUser);
	        
	        BooleanResultDTO response  = new BooleanResultDTO(updated);
	        
	        if(updated){
	        	EmailSender.notifyMobileUserPostCensure(post.getRelatedUser());  //  Notify via Email
	        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
	        }
	        else{ 
	        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
	        }
	    }
        else{
        	throw new BadRequestException("Comentario inválido para editar.");
        }
    }
	
	
	//------------------- Comment on Post -----------------------------------
	
	@RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> commentPost(@PathVariable("id") long postId, 
    		                                            @RequestBody @Validated CommentPostReqDTO commentReq, BindingResult bindingResult, 
    		                                            @AuthenticationPrincipal User authUser) {
    	log.debug("Adding comment to existing Post...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Post comment = PostMapper.convertPostCommentDTOToEntity(commentReq, authUser);
        boolean saved = postService.addComment(comment, postId);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);     
        
        if(saved) 
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        else 
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
    }	
	
	
	//------------------- Like on Post -----------------------------------
	
	@RequestMapping(value = "/posts/{id}/likes", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> likePost(@PathVariable("id") long postId, 
    		                                         @AuthenticationPrincipal User authUser) {
    	log.debug("Adding like to existing Post...");
    	
        Like like = new Like();
         like.setRelatedUser(authUser);
         like.setNotified(false);
        boolean saved = postService.addLike(like, postId);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);     
        
        if(saved) 
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        else 
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
    }	
	
	
	
	//------------------- Delete a Post of Mobile User-------------------------------------------------    
	
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BooleanResultDTO> removeAdminPost(@PathVariable("id") long postId, 
    		                                                @AuthenticationPrincipal User authUser) {
    	log.debug("Fetching & Deleting Post with Id " + postId);
    	
    	postService.removePost(postId, authUser);
        
        return new ResponseEntity<BooleanResultDTO>(new BooleanResultDTO(true), HttpStatus.OK);
    }
    
    
    //------------------- Delete comment on Post -----------------------------------
	
    @RequestMapping(value = "/posts/{pId}/comments/{cId}", method = RequestMethod.DELETE)
    public ResponseEntity<BooleanResultDTO> removeComment(@PathVariable("pId") long postId, 
    		                                              @PathVariable("cId") long commentId, 
    		                                              @AuthenticationPrincipal User authUser) {
     	log.debug("Deleting comment on existing Post...");
  
      	postService.removeCommentPost(postId, commentId, authUser);
             
        return new ResponseEntity<BooleanResultDTO>(new BooleanResultDTO(true), HttpStatus.OK);
    }	
	
    
    //------------------- DisLike on Post -----------------------------------
	
  	 @RequestMapping(value = "/posts/{id}/likes", method = RequestMethod.DELETE)
     public ResponseEntity<BooleanResultDTO> dislikePost(@PathVariable("id") long postId, 
    		                                             @AuthenticationPrincipal User authUser) {
      	 log.debug("Dislike to existing Post...");
   
       	 boolean saved = postService.disLike(postId, authUser);
          
         BooleanResultDTO response  = new BooleanResultDTO(saved);     
         
         if(saved) 
         	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
         else 
         	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
     }

 
  	
}