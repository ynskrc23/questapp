package com.project.questapp.controllers;

import com.project.questapp.entities.Like;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService)
    {
        this.likeService = likeService;
    }

    @GetMapping
    public List<Like> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId)
    {
        return likeService.getAllLikes(userId, postId);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId)
    {
        return likeService.getOneLike(likeId);
    }

    @PostMapping
    public Like createLike(@RequestBody LikeCreateRequest request)
    {
        return likeService.createLike(request);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId)
    {
        likeService.deleteLike(likeId);
    }
}
