package com.project.questapp.services;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repository.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId)
    {
        if(userId.isPresent() && postId.isPresent())
        {
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent())
        {
            return commentRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent())
        {
            return commentRepository.findByPostId(postId.get());
        }
        else
            return commentRepository.findAll();
    }

    public Comment getOneComment(Long commentId)
    {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(CommentCreateRequest request)
    {
        User user = userService.getOneUser(request.getUserId());
        Post post = postService.getOnePost(request.getPostId());

        if(user != null && post != null)
        {
            Comment toSave = new Comment();
            toSave.setId(request.getId());
            toSave.setUser(user);
            toSave.setPost(post);
            toSave.setText(request.getText());
            return commentRepository.save(toSave);
        }
        else
            return null;
    }

    public Comment updateComment(Long commentId, CommentUpdateRequest request)
    {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent())
        {
            Comment foundComment = comment.get();
            foundComment.setText(request.getText());
            return commentRepository.save(foundComment);
        }
        else
            return null;
    }

    public void deleteComment(Long commentId)
    {
        commentRepository.deleteById(commentId);
    }
}
