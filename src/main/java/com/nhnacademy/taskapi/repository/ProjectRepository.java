package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
