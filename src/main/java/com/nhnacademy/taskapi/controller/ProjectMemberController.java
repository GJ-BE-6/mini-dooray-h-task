package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.ProjectMemberDto;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.dto.UserDto;
import com.nhnacademy.taskapi.service.ProjectMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProjectMember API", description = "프로젝트 멤버 관련 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    // 해당 프로젝트에 멤버 추가
    @Operation(summary = "프로젝트 멤버 추가", description = "프로젝트에 멤버를 추가합니다.")
    @PostMapping("/{id}/members")
    public ResponseEntity<ProjectMemberDto> addMember(@PathVariable Long id, @RequestBody UserDto member) {
        return new ResponseEntity<>(projectMemberService.addProjectMember(id, member.getUserId()), HttpStatus.CREATED);
    }

    // 해당 프로젝트 멤버 조회
    @Operation(summary = "프로젝트 멤버 조회", description = "프로젝트 멤버를 조회합니다.")
    @GetMapping("/{id}/members")
    public ResponseEntity<List<ProjectMemberDto>> getMembers(@PathVariable Long id) {
        return new ResponseEntity<>(projectMemberService.getProjectMembersByProjectId(id), HttpStatus.OK);
    }

    // 해당 프로젝트에서 특정 멤버 삭제
    @Operation(summary = "프로젝트 멤버 삭제", description = "프로젝트 멤버를 삭제합니다.")
    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ProjectMember> deleteMember(@PathVariable Long projectId, @PathVariable String userId) {

        projectMemberService.deleteProjectMember(projectId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
