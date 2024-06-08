package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.TaskDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import com.nhnacademy.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.dto.TaskCreateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    // Task 등록 (projectId를 가지고)
    @Transactional
    public TaskDto createTask(TaskCreateDto taskCreateDto) {
        Project project = projectRepository.findById(taskCreateDto.getProjectId())
            .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        ProjectMemberPk projectMemberPk = new ProjectMemberPk(taskCreateDto.getUserId(), taskCreateDto.getProjectId());
        if (!projectMemberRepository.existsById(projectMemberPk)) {
            throw new IllegalArgumentException("User not found");
        }

        Task task = new Task(taskCreateDto.getTaskName(), taskCreateDto.getTaskDescription(), taskCreateDto.getTaskStatus(), project, taskCreateDto.getUserId());
        taskRepository.save(task);

        return new TaskDto(task.getId(), task);
    }

    // Task 수정 (projectId를 가지고)
    @Transactional
    public TaskDto updateTask(Long taskId, String name, String description, String status, Long projectId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);

        taskRepository.save(task);

        return new TaskDto(task);
    }

    // Task 삭제
    @Transactional
    public void deleteTask(Long taskId) {

        taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        taskRepository.deleteById(taskId);
    }

    // Task 목록
    public List<TaskDto> getTasksByProjectId(Long projectId) {

        projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        List<Task> tasks = taskRepository.findByProjectId(projectId);

        if (Objects.isNull(tasks) || tasks.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }

        return tasks.stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    // Task 내용
    public TaskDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        return new TaskDto(task);
    }
}
