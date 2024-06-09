package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.CommentDTO;
import com.nhnacademy.taskapi.dto.CommentResponseDTO;
import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long taskId) {
        List<CommentResponseDTO> comments = commentService.getComments(taskId).stream()
                .map(comment -> new CommentResponseDTO(comment.getContent(), comment.getUserId(), comment.getTask().getId(), comment.getId()))
                .toList();
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable("taskId") Long taskId, @RequestBody CommentDTO commentDTO){
        Comment createdComment = commentService.createComment(taskId, commentDTO);
        return ResponseEntity.ok(new CommentResponseDTO(createdComment.getContent(), createdComment.getUserId(), createdComment.getTask().getId(), createdComment.getId()));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO){
        Comment updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(new CommentResponseDTO(updatedComment.getContent(), updatedComment.getUserId(), updatedComment.getTask().getId(), updatedComment.getId()));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
