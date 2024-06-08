package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Project 멤버는 회원관리에서 가입한 회원만 가능합니다.
        // todo : Account-api 선에서 관리

    // Project 멤버는 자신이 속한 Project 목록만 확인할 수 있습니다.
    @Query("SELECT pm.project FROM ProjectMember pm WHERE pm.projectMemberPk.userId = :userId")
    List<Project> findProjectByUserId(String userId);

    List<Project> findDistinctByProjectMembers_ProjectMemberPk_UserId(String userId);

}
