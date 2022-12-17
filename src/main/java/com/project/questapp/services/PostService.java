package com.project.questapp.services;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.PostRepository;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId)
    {
        if(userId.isPresent()){
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }

    public Post save(PostCreateRequest newPostRequest)
    {
        User user = userService.getOneUser(newPostRequest.getUserId());
        if (user == null)
            return null;

        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setText(newPostRequest.getText());
        toSave.setUser(user);
        return postRepository.save(toSave);
    }

    public Post getOnePost(Long postId)
    {
        return postRepository.findById(postId).orElse(null);
    }

    public void deletePost(Long postId)
    {
        postRepository.deleteById(postId);
    }

    public Post updatePost(Long postId, PostUpdateRequest updatePostRequest)
    {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isPresent())
        {
            Post foundPost = post.get();
            foundPost.setTitle(updatePostRequest.getTitle());
            foundPost.setText(updatePostRequest.getText());
            postRepository.save(foundPost);
            return foundPost;
        }
        else
            return null;
    }
}
