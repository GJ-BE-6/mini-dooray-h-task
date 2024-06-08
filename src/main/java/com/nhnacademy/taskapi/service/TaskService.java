package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.TagDTO;
import com.nhnacademy.taskapi.dto.TaskDto;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import com.nhnacademy.taskapi.repository.*;
import com.nhnacademy.taskapi.dto.TaskCreateDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final MilestoneService milestoneService;
    private final TagService tagService;
    private final MilestoneRepository milestoneRepository;
    private final EntityManager entityManager;

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

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        // tag 연결 해제
        List<Tag> tags = tagRepository.findAllByProjectId(task.getProject().getId());
        for (Tag tag : tags) {
            tagService.deleteTagFromTask(taskId, tag.getId());
//            log.info("Delete tagId: {}", );
        }

        // milestone 연결 해제
        Milestone milestone = milestoneRepository.findMilestoneByTaskId(taskId);
        milestoneService.deleteMilestoneFromTask(milestone.getId(), taskId);
        log.info("milestone get task : {}", milestone.getTask());

        entityManager.flush();
        entityManager.clear();

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
