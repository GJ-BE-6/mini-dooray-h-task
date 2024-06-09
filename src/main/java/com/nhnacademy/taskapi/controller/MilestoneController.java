package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.MilestoneDTO;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
public class MilestoneController {
    @Autowired
    MilestoneService milestoneService;

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

    @DeleteMapping("/milestones/{milestoneId}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable("milestoneId") Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return ResponseEntity.noContent().build();
    }

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

    @DeleteMapping("/tasks/milestones/{milestoneId}")
    public ResponseEntity<Void> deleteMilestoneFromTask(@PathVariable("milestoneId") Long milestoneId) {
        milestoneService.deleteMilestoneFromTask(milestoneId);
        return ResponseEntity.noContent().build();
    }

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