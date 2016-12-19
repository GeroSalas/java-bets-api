package com.gen.desafio.api.services;


import java.util.List;
import org.springframework.stereotype.Service;
import com.gen.desafio.api.domain.model.Like;
import com.gen.desafio.api.domain.model.Post;
import com.gen.desafio.api.domain.model.User;


@Service
public interface PostService extends BaseService {
	
	 boolean createPost(Post post);
	 boolean modifyPost(Post post, User owner);
	 //boolean modifyComment(Post comment, User owner);
	 boolean addComment(Post comment, long postId);
	 boolean addLike(Like like, long postId);
	 boolean disLike(long postId, User currentUser);
	 Post findOne(long postId, User currentUser);
     List<Post> listPostsByUser(User currentUser);
     void removePost(long id, User currentUser);
     void removeCommentPost(long pId, long cId, User currentUser);

}
