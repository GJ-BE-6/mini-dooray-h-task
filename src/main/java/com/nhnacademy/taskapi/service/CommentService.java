package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.CommentDTO;
import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Comment createComment(long taskId, CommentDTO commentDTO) {
        Task task = taskRepository.findById(taskId).orElse(null);
        Comment createdComment = new Comment(commentDTO.getContent(), commentDTO.getUserId(), task);
        return commentRepository.save(createdComment);
    }

    public Comment updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setContent(commentDTO.getContent());
            return commentRepository.save(comment);
        }
        return null;
    }

    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getComments(long taskId) {
        return commentRepository.findCommentByTaskId(taskId);
    }

}
