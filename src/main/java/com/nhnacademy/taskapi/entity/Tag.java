package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Setter
    @Column(name = "tag_name", length = 50, nullable = false)
    private String name;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // todo Tag : task_tags @OneToMany mapped

    public Tag(String name, Project project) {
        this.name = name;
        this.project = project;
    }
}
