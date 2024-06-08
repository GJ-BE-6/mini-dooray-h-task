package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Setter
    private String content;

    @Column(nullable = false, length = 20)
    private String userId;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "task_id")
    private Task task;

    public Comment(String content, String userId, Task task) {
        this.content = content;
        this.userId = userId;
        this.task = task;
    }
}
