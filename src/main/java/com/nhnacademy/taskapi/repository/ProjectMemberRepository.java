package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberPk> {

    boolean existsByProjectAndProjectMemberPkUserId(Project project, String userId);

    List<ProjectMember> findByProject(Project project);
}
