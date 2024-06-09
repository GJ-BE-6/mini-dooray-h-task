package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Comment;
import com.nhnacademy.taskapi.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("project.sql")
public class CommentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void saveComment() {
        long taskId = 1234L;
        Task task = taskRepository.findById(taskId).get();
        Comment comment1 = new Comment("test content", "test-user", task);
        Comment comment2 = new Comment("test content2", "test-user2", task);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> commentList = commentRepository.findCommentByTaskId(taskId);

        assertThat(commentList).hasSize(2);
        assertThat(commentList.get(0).getUserId()).isEqualTo(comment1.getUserId());
        assertThat(commentList.get(1).getContent()).isEqualTo(comment2.getContent());
    }

    @Test
    public void deleteComment() {
        // 코멘트1 코멘트2 등록
        long taskId = 1234L;
        Task task = taskRepository.findById(taskId).get();
        Comment comment1 = new Comment("test content", "test-user", task);
        Comment comment2 = new Comment("test content2", "test-user2", task);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        entityManager.flush();
        entityManager.clear();
        // 코멘트2 삭제
        List<Comment> commentList = commentRepository.findCommentByTaskId(taskId);
        Long commentId = commentList.getLast().getId();
        commentRepository.deleteById(commentId);
        entityManager.flush();
        // 확인
        List<Comment> commentListAfterDelete = commentRepository.findCommentByTaskId(taskId);
        assertThat(commentListAfterDelete).hasSize(1);
        assertThat(commentListAfterDelete.getLast().getContent()).isEqualTo(comment1.getContent());
    }

}
