package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.dto.TaskCreateDto;
import com.nhnacademy.taskapi.dto.TaskDto;
import com.nhnacademy.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class TaskController {

    private final TaskService taskService;

    // ProjectId에 해당하는 Task 목록 조회
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTasksByProjectId(id), HttpStatus.OK);
    }

    // 특정 Task 조회
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    // 등록
    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreateDto taskCreateDto) {
        return new ResponseEntity<>(taskService.createTask(taskCreateDto), HttpStatus.CREATED);
    }

    // 수정
    @PutMapping("/tasks")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskDto.getTaskId(),
                taskDto.getTaskName(), taskDto.getTaskDescription(), taskDto.getTaskStatus(), taskDto.getProjectId()), HttpStatus.NO_CONTENT);
    }

    // 삭제
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
