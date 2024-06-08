package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.ProjectDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.service.ProjectMemberService;
import com.nhnacademy.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;

    // 모든 프로젝트 가져오기
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.getProjects(), HttpStatus.OK);
    }

    // User가 속한 프로젝트들만 가져오기
    @GetMapping("/users/{id}")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUserId(@PathVariable String id) {
        return new ResponseEntity<>(projectService.getProjectsByUserId(id), HttpStatus.OK);
    }

    // ProjectId에 해당하는 프로젝트 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectByProjectId(id), HttpStatus.OK);
    }

    // 프로젝트 생성 (생성자가 자동으로 관리자로 지정)
    @PostMapping("/users/{id}")
    public ResponseEntity<ProjectDto> createProject(@PathVariable String id,
                                                 @RequestBody Project project) {
        // 사용자는 Project를 생성
        // Project를 생성한 사용자는 Project의 관리자
        ResponseEntity<ProjectDto> response = new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
        projectMemberService.addProjectAdmin(project.getId(), id);

        return response;
    }

    // 수정
    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody Project project) {
        return new ResponseEntity<>(projectService.updateProject(project.getId(), project.getName(), project.getStatus()), HttpStatus.NO_CONTENT);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
