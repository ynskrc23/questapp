package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.LikeRepository;
import com.project.questapp.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId)
    {
        if(userId.isPresent() && postId.isPresent())
        {
            return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent())
        {
            return likeRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent())
        {
            return likeRepository.findByPostId(postId.get());
        }
        else
            return likeRepository.findAll();
    }

    public Like getOneLike(Long likeId)
    {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createLike(LikeCreateRequest request)
    {
        User user = userService.getOneUser(request.getUserId());
        Post post = postService.getOnePost(request.getPostId());

        if(user != null && post != null)
        {
            Like toSave = new Like();
            toSave.setId(request.getId());
            toSave.setPost(post);
            toSave.setUser(user);
            return likeRepository.save(toSave);
        }
        else
            return null;
    }

    public void deleteLike(Long likeId)
    {
        likeRepository.deleteById(likeId);
    }
}
