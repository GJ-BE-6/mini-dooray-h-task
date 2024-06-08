package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "milestones")
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    @Setter
    @Column(name = "milestone_name", nullable = false, length = 50)
    private String name;

    @Setter
    @Column(columnDefinition = "DATE")
    private ZonedDateTime startDate;

    @Setter
    @Column(columnDefinition = "DATE")
    private ZonedDateTime dueDate;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "project_id")
    private Project project;

    @Setter
    @OneToOne()
    @JoinColumn(name = "task_id")
    private Task task;

    public Milestone(String name, ZonedDateTime startDate, ZonedDateTime dueDate, Project project, Task task) {
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.project = project;
        this.task = task;
    }
}
