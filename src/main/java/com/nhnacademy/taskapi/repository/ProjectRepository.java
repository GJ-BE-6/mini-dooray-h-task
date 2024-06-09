package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findDistinctByProjectMembers_ProjectMemberPk_UserId(String userId);
}
