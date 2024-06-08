package com.nhnacademy.taskapi.entity;

import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "project_members")
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberPk projectMemberPk;

    @Setter
    @Column(nullable = false, length = 50)
    private String role;

    @Setter
    @MapsId("projectId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public ProjectMember(String role, String userId, Project project) {
        this.role = role;
        this.project = project;
        this.projectMemberPk = new ProjectMemberPk(userId, project.getId());
    }
}
