package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Setter
    @Column(name = "project_name", length = 50, nullable = false)
    private String name;

    @Setter
    @Column(name = "project_status", length = 50, nullable = false)
    private String status;

    // todo Project : tag, project_members, task, milestones @OneToMany mapped

    // todo Project : 생성자 생성
    public Project(String name, String status) {
        this.name = name;
        this.status = status;
    }
}
