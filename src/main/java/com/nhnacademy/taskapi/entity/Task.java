package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Setter
    @Column(name = "task_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "task_description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Setter
    @Column(name = "task_status", nullable = false, length = 50)
    private String status;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Setter
    @Column(nullable = false, length = 20)
    private String userId;

    // todo Task : task_tags, comments @OneToMany mapped

    // todo Task : milestones @OneToOne mapped

    public Task(String name, String description, String status, Project project, String userId) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.project = project;
        this.userId = userId;
    }

    public Task(Long id, Project project) {
        this.id = id;
        this.project = project;
    }
}
