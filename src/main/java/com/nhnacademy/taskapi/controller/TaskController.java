package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.dto.TaskCreateDto;
import com.nhnacademy.taskapi.dto.TaskDto;
import com.nhnacademy.taskapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task API", description = "태스크 관련 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class TaskController {

    private final TaskService taskService;

    // ProjectId에 해당하는 Task 목록 조회
    @Operation(summary = "태스크 목록 조회", description = "ProjectId에 속한 태스크 목록을 조회합니다.")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTasksByProjectId(id), HttpStatus.OK);
    }

    // 특정 Task 조회
    @Operation(summary = "특정 태스트 조회", description = "특정 태스크를 조회합니다.")
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    // 등록
    @Operation(summary = "태스트 등록", description = "태스크를 등록합니다.")
    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreateDto taskCreateDto) {
        return new ResponseEntity<>(taskService.createTask(taskCreateDto), HttpStatus.CREATED);
    }

    // 수정
    @Operation(summary = "태스크 수정", description = "태스그를 수정합니다.")
    @PutMapping("/tasks")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskDto.getTaskId(),
                taskDto.getTaskName(), taskDto.getTaskDescription(), taskDto.getTaskStatus(), taskDto.getProjectId()), HttpStatus.NO_CONTENT);
    }

    // 삭제
    @Operation(summary = "태스크 삭제", description = "태스크를 삭제합니다.")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
