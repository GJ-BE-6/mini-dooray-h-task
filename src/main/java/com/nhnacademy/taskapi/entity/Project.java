package com.nhnacademy.taskapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Setter
    @Column(name = "project_name", length = 50, nullable = false)
    private String name;

    @Setter
    @Column(name = "project_status", length = 50, nullable = false)
    private String status;  // Status : Active, Dormant, End


    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Tag> tags;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Milestone> milestones;

    public Project(String name, String status) {
        this.name = name;
        this.status = status;
    }
}
