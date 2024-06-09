package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.ProjectDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.service.ProjectMemberService;
import com.nhnacademy.taskapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project API", description = "프로젝트 관련 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;

    // 모든 프로젝트 가져오기
    @Operation(summary = "모든 프로젝트 조회", description = "모든 프로젝트를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.getProjects(), HttpStatus.OK);
    }

    // User가 속한 프로젝트들만 가져오기
    @Operation(summary = "특정 프로젝트 조회", description = "User가 속한 프로젝트만 조회합니다.")
    @GetMapping("/users/{id}")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUserId(@PathVariable String id) {
        return new ResponseEntity<>(projectService.getProjectsByUserId(id), HttpStatus.OK);
    }

    // ProjectId에 해당하는 프로젝트 가져오기
    @Operation(summary = "특정 프로젝트 조회", description = "ProjectId에 해당하는 프로젝트만 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectByProjectId(id), HttpStatus.OK);
    }

    // 프로젝트 생성 (생성자가 자동으로 관리자로 지정)
    @Operation(summary = "프로젝트 생성", description = "프로젝트를 생성합니다. 생성자는 자동으로 관리자가 됩니다.")
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
    @Operation(summary = "프로젝트 수정", description = "프로젝트를 수정합니다.")
    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody Project project) {
        return new ResponseEntity<>(projectService.updateProject(project.getId(), project.getName(), project.getStatus()), HttpStatus.NO_CONTENT);
    }

    // 삭제
    @Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
