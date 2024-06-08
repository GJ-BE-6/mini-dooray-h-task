package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(nullable = false, name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<TaskTag> taskTags = new ArrayList<>();

    // todo Tag : task_tags @OneToMany mapped

    // todo Tag : 생성자 생성
    public Tag(String name, Project project) {
        this.name = name;
        this.project = project;
    }
}
