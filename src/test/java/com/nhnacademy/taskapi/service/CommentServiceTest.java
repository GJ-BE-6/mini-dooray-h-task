package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.CommentDTO;
import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.CommentRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateComment() {
        long taskId = 1L;
        CommentDTO commentDTO = new CommentDTO("test-user", "Test content");
        Task task = new Task();
        Comment comment = new Comment(commentDTO.getContent(), commentDTO.getUserId(), task);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment createdComment = commentService.createComment(taskId, commentDTO);

        assertNotNull(createdComment);
        assertEquals(commentDTO.getContent(), createdComment.getContent());
        assertEquals(commentDTO.getUserId(), createdComment.getUserId());
        assertEquals(task, createdComment.getTask());

        verify(taskRepository, times(1)).findById(taskId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testUpdateComment() {
        long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO("test-user", "Updated content");
        Comment comment = new Comment("Original content", "test-user", new Task());

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment updatedComment = commentService.updateComment(commentId, commentDTO);

        assertNotNull(updatedComment);
        assertEquals(commentDTO.getContent(), updatedComment.getContent());

        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testDeleteComment() {
        long commentId = 1L;

        doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void testGetComments() {
        long taskId = 1L;
        Task task = new Task();
        Comment comment = new Comment("test-user","Test content", task);
        List<Comment> comments = Collections.singletonList(comment);

        when(commentRepository.findCommentByTaskId(taskId)).thenReturn(comments);

        List<Comment> retrievedComments = commentService.getComments(taskId);

        assertNotNull(retrievedComments);
        assertEquals(1, retrievedComments.size());
        assertEquals(comment.getContent(), retrievedComments.get(0).getContent());

        verify(commentRepository, times(1)).findCommentByTaskId(taskId);
    }
}
