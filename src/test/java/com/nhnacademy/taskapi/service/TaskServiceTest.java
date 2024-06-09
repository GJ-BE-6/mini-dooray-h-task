package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.TaskCreateDto;
import com.nhnacademy.taskapi.dto.TaskDto;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import com.nhnacademy.taskapi.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private MilestoneService milestoneService;

    @Mock
    private TagService tagService;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Project project = new Project("Project 1", "Active");
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setTaskName("Task 1");
        taskCreateDto.setTaskDescription("Description 1");
        taskCreateDto.setTaskStatus("Active");
        taskCreateDto.setProjectId(1L);
        taskCreateDto.setUserId("user1");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.existsById(new ProjectMemberPk("user1", 1L))).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDto taskDto = taskService.createTask(taskCreateDto);

        assertNotNull(taskDto);
        assertEquals("Task 1", taskDto.getTaskName());
        assertEquals("Description 1", taskDto.getTaskDescription());
        assertEquals("Active", taskDto.getTaskStatus());
    }

    @Test
    void testUpdateTask() {
        Project project = new Project("Project 1", "Active");
        Task task = new Task("Task 1", "Description 1", "Active", project, "user1");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDto taskDto = taskService.updateTask(1L, "Updated Task", "Updated Description", "Completed", 1L);

        assertNotNull(taskDto);
        assertEquals("Updated Task", taskDto.getTaskName());
        assertEquals("Updated Description", taskDto.getTaskDescription());
        assertEquals("Completed", taskDto.getTaskStatus());
    }

    @Test
    void testDeleteTask() {
        Project project = new Project("Project 1", "Active");
        Task task = new Task("Task 1", "Description 1", "Active", project, "user1");

        project.setId(1L);
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Tag tag = new Tag("Tag 1", project);
        tag.setId(1L);
        when(tagRepository.findAllByProjectId(1L)).thenReturn(Collections.singletonList(tag));

        Milestone milestone = new Milestone("Milestone 1", ZonedDateTime.now(), ZonedDateTime.now() ,project, task);
        milestone.setId(1L);
        when(milestoneRepository.findMilestoneByTaskId(1L)).thenReturn(milestone);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
        verify(tagService, times(1)).deleteTagFromTask(anyLong(), anyLong());
        verify(milestoneService, times(1)).deleteMilestoneFromTask(anyLong());
    }

    @Test
    void testGetTasksByProjectId() {
        Project project = new Project("Project 1", "Active");
        Task task1 = new Task("Task 1", "Description 1", "Active", project, "user1");
        Task task2 = new Task("Task 2", "Description 2", "Active", project, "user2");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.findByProjectId(1L)).thenReturn(Arrays.asList(task1, task2));

        List<TaskDto> taskDtos = taskService.getTasksByProjectId(1L);

        assertNotNull(taskDtos);
        assertEquals(2, taskDtos.size());
        assertEquals("Task 1", taskDtos.get(0).getTaskName());
        assertEquals("Task 2", taskDtos.get(1).getTaskName());
    }

    @Test
    void testGetTaskById() {
        Project project = new Project("Project 1", "Active");
        Task task = new Task("Task 1", "Description 1", "Active", project, "user1");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDto taskDto = taskService.getTaskById(1L);

        assertNotNull(taskDto);
        assertEquals("Task 1", taskDto.getTaskName());
        assertEquals("Description 1", taskDto.getTaskDescription());
        assertEquals("Active", taskDto.getTaskStatus());
    }
}
