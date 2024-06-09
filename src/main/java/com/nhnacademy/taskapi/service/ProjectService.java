package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.ProjectDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    // 모든 프로젝트 불러오기
    public List<ProjectDto> getProjects() {

        List<Project> projects = projectRepository.findAll();

        if (projects.isEmpty()) {
            return null;
        }

        return projects.stream()
                .map(project -> new ProjectDto(project.getId(), project.getName(), project.getStatus()))
                .collect(Collectors.toList());
    }

    // userId에 해당하는 프로젝트 불러오기
    public List<ProjectDto> getProjectsByUserId(String userId) {

        List<Project> projects = projectRepository.findDistinctByProjectMembers_ProjectMemberPk_UserId(userId);//findProjectByUserId(userId);

        if (Objects.isNull(projects) || projects.isEmpty()) {
            return null;
        }

        return projects.stream()
                .map(project -> new ProjectDto(project.getId(), project.getName(), project.getStatus()))
                .collect(Collectors.toList());
    }

    // projectId에 해당하는 프로젝트 불러오기
    public ProjectDto getProjectByProjectId(Long projectId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            return new ProjectDto(projectId, project.getName(), project.getStatus());
        } else {
            return null;
        }
    }

    // 프로젝트 생성
    @Transactional
    public ProjectDto createProject(Project project) {
        Project createProject = projectRepository.save(project);

        return new ProjectDto(createProject.getId(), project.getName(), project.getStatus());
    }

    // 프로젝트 수정
    @Transactional
    public ProjectDto updateProject(Long projectId, String name, String status) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(name);
            project.setStatus(status);

            Project updatedProject = projectRepository.save(project);

            return new ProjectDto(updatedProject.getId(), updatedProject.getName(), updatedProject.getStatus());
        } else {
            return null;
        }
    }

    // 프로젝트 삭제
    @Transactional
    public void deleteProject(Long projectId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            projectRepository.deleteById(projectId);
        }
    }
}
