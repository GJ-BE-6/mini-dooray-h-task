package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberPk> {


}
