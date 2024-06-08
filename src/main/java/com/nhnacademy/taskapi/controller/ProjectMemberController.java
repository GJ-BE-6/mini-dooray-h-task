package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.ProjectMemberDto;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.dto.UserDto;
import com.nhnacademy.taskapi.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    // 해당 프로젝트에 멤버 추가
    @PostMapping("/{id}/members")
    public ResponseEntity<ProjectMemberDto> addMember(@PathVariable Long id, @RequestBody UserDto member) {
        return new ResponseEntity<>(projectMemberService.addProjectMember(id, member.getUserId()), HttpStatus.CREATED);
    }

    // 해당 프로젝트 멤버 조회
    @GetMapping("/{id}/members")
    public ResponseEntity<List<ProjectMemberDto>> getMembers(@PathVariable Long id) {
        return new ResponseEntity<>(projectMemberService.getProjectMembersByProjectId(id), HttpStatus.OK);
    }

    // 해당 프로젝트에서 특정 멤버 삭제
    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ProjectMember> deleteMember(@PathVariable Long projectId, @PathVariable String userId) {

        projectMemberService.deleteProjectMember(projectId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
