package com.project.questapp.controllers;

import com.project.questapp.entities.Post;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;
import com.project.questapp.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId)
    {
        return postService.getAllPosts(userId);
    }

    @GetMapping("{postId}")
    public Post getOnePost(@PathVariable Long postId)
    {
        return postService.getOnePost(postId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newPostRequest)
    {
        return postService.save(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePostRequest)
    {
        return postService.updatePost(postId, updatePostRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId)
    {
        postService.deletePost(postId);
    }
}
