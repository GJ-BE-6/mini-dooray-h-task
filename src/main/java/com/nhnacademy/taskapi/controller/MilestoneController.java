package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.MilestoneDTO;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.service.MilestoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Milestone API", description = "마일스톤 관련 API 입니다.")
@RestController()
public class MilestoneController {
    @Autowired
    MilestoneService milestoneService;

    @Operation(summary = "마일스톤 조회", description = "마일스톤 아이디를 통해 조회합니다.")
    @GetMapping("/milestones/{milestoneId}")
    public ResponseEntity<MilestoneDTO> getMilestone(@PathVariable Long milestoneId) {
        Milestone milestone = milestoneService.getMilestone(milestoneId);
        MilestoneDTO resp = MilestoneDTO.builder()
                .milestoneId(milestone.getId())
                .projectId(milestone.getProject().getId())
                .milestoneName(milestone.getName())
                .startDate(milestone.getStartDate())
                .dueDate(milestone.getDueDate())
                .taskId(Optional.ofNullable(milestone.getTask()).map(Task::getId).orElse(null))
                .build();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "마일스톤 등록", description = "특정 프로젝트에 마일스톤을 등록합니다.")
    @PostMapping("/milestones")
    public ResponseEntity<MilestoneDTO> createMilestone(@RequestBody MilestoneDTO milestoneDTO) {
        Milestone milestone = milestoneService.createMilestone(milestoneDTO);
        MilestoneDTO resp = MilestoneDTO.builder()
                .milestoneId(milestone.getId())
                .projectId(milestone.getProject().getId())
                .milestoneName(milestone.getName())
                .startDate(milestone.getStartDate())
                .dueDate(milestone.getDueDate())
                .build();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "마일스톤 수정", description = "마일스톤 정보를 수정합니다.")
    @PutMapping("/milestones/{milestoneId}")
    public ResponseEntity<MilestoneDTO> updateMilestone(@PathVariable("milestoneId") Long milestoneId, @RequestBody MilestoneDTO milestoneDTO) {
        Milestone milestone = milestoneService.updateMilestone(milestoneId, milestoneDTO);
        MilestoneDTO resp = MilestoneDTO.builder()
                .milestoneId(milestone.getId())
                .projectId(milestone.getProject().getId())
                .milestoneName(milestone.getName())
                .startDate(milestone.getStartDate())
                .dueDate(milestone.getDueDate())
                .build();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "마일스톤 삭제", description = "마일스톤 정보를 삭제합니다.")
    @DeleteMapping("/milestones/{milestoneId}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable("milestoneId") Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마일스톤 태스크에 할당", description = "마일스톤을 태스크에 등록합니다.")
    @PostMapping("/milestones/{milestoneId}/tasks/{taskId}")
    public ResponseEntity<MilestoneDTO> setMilestoneToTask(@PathVariable("milestoneId") Long milestoneId, @PathVariable("taskId") Long taskId) {
        Milestone milestone = milestoneService.setMileStoneToTask(taskId, milestoneId);
        MilestoneDTO resp = MilestoneDTO.builder()
                .milestoneId(milestone.getId())
                .projectId(milestone.getProject().getId())
                .milestoneName(milestone.getName())
                .startDate(milestone.getStartDate())
                .dueDate(milestone.getDueDate())
                .taskId(milestone.getTask().getId())
                .build();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "마일스톤 태스크에서 할당 해제", description = "마일스톤을 태스크에서 등록 해제합니다.")
    @DeleteMapping("/tasks/milestones/{milestoneId}")
    public ResponseEntity<Void> deleteMilestoneFromTask(@PathVariable("milestoneId") Long milestoneId) {
        milestoneService.deleteMilestoneFromTask(milestoneId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마일스톤 조회", description = "특정 프로젝트에 등록된 마일스톤을 조회합니다.")
    @GetMapping("/projects/{projectId}/milestones")
    public ResponseEntity<List<MilestoneDTO>> getMilestones(@PathVariable("projectId") Long projectId) {
        List<MilestoneDTO> resp = milestoneService.getMilestoneByProjectId(projectId).stream()
                .map(milestone -> MilestoneDTO.builder()
                        .milestoneId(milestone.getId())
                        .projectId(milestone.getProject().getId())
                        .milestoneName(milestone.getName())
                        .startDate(milestone.getStartDate())
                        .dueDate(milestone.getDueDate())
                        .taskId(Optional.ofNullable(milestone.getTask()).map(Task::getId).orElse(null))
                        .build())
                .toList();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "마일스톤 조회", description = "특정 태스크에 등록된 마일스톤을 조회합니다.")
    @GetMapping("/tasks/{taskId}/milestone")
    public ResponseEntity<MilestoneDTO> getMilestoneByTaskId(@PathVariable("taskId") Long taskId) {
        Milestone milestone = milestoneService.getMilestoneByTaskId(taskId);
        if (milestone == null) {
            return ResponseEntity.noContent().build();
        }
        MilestoneDTO resp = MilestoneDTO.builder()
                .milestoneId(milestone.getId())
                .projectId(milestone.getProject().getId())
                .milestoneName(milestone.getName())
                .startDate(milestone.getStartDate())
                .dueDate(milestone.getDueDate())
                .taskId(Optional.ofNullable(milestone.getTask()).map(Task::getId).orElse(null))
                .build();
        return ResponseEntity.ok(resp);
    }
}