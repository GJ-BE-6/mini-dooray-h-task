package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.ProjectMemberDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import com.nhnacademy.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    // Project 관리자가 멤버 등록
    @Transactional
    public ProjectMemberDto addProjectMember(Long projectId, String userId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            // 이미 멤버로 등록되어 있는지 확인
            if (projectMemberRepository.existsByProjectAndProjectMemberPkUserId(project, userId)) {
                return null;
            }

            // Role 임시로 "Member"로 설정
            ProjectMember projectMember = new ProjectMember("Member", userId, project);
            projectMemberRepository.save(projectMember);

            return new ProjectMemberDto(userId, projectId, "Member");
        } else {
            return null;
        }
    }

    // Project 관리자 등록
    @Transactional
    public ProjectMemberDto addProjectAdmin(Long projectId, String userId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            // Role 임시로 "Admin"로 설정
            ProjectMember projectMember = new ProjectMember("Admin", userId, project);
            projectMemberRepository.save(projectMember);

            return new ProjectMemberDto(userId, projectId, "Admin");
        } else {
            return null;
        }
    }

    // Project 멤버 조회
    public List<ProjectMemberDto> getProjectMembersByProjectId(Long projectId) {

        return projectRepository.findById(projectId)
                .map(project -> {
                    List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
                    return projectMembers.stream()
                            .map(member -> new ProjectMemberDto(
                                    member.getProjectMemberPk().getUserId(),
                                    member.getProject().getId(),
                                    member.getRole()))
                            .collect(Collectors.toList());
                })
                .orElse(Collections.emptyList());
    }

    // Project 멤버 삭제
    public void deleteProjectMember(Long projectId, String userId) {

        ProjectMemberPk projectMemberPk = new ProjectMemberPk(userId, projectId);

        if (projectMemberRepository.existsById(projectMemberPk)) {
            projectMemberRepository.deleteById(projectMemberPk);
        }
    }
}
