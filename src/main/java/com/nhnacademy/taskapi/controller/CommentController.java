package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.CommentDTO;
import com.nhnacademy.taskapi.dto.CommentResponseDTO;
import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment API", description = "코멘트 관련 API 입니다.")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(summary = "코멘트 조회", description = "특정 태스크에 등록된 코멘트 정보를 가져옵니다.")
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long taskId) {
        List<CommentResponseDTO> comments = commentService.getComments(taskId).stream()
                .map(comment -> new CommentResponseDTO(comment.getContent(), comment.getUserId(), comment.getTask().getId(), comment.getId()))
                .toList();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "코멘트 등록", description = "특정 태스크에 코멘트를 등록합니다.")
    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable("taskId") Long taskId, @RequestBody CommentDTO commentDTO){
        Comment createdComment = commentService.createComment(taskId, commentDTO);
        return ResponseEntity.ok(new CommentResponseDTO(createdComment.getContent(), createdComment.getUserId(), createdComment.getTask().getId(), createdComment.getId()));
    }

    @Operation(summary = "코멘트 수정", description = "코멘트 정보를 수정합니다.")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO){
        Comment updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(new CommentResponseDTO(updatedComment.getContent(), updatedComment.getUserId(), updatedComment.getTask().getId(), updatedComment.getId()));
    }

    @Operation(summary = "코멘트 삭제", description = "코멘트 정보를 삭제합니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
