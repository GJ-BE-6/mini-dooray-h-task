package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.ProjectDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProjects() {
        Project project1 = new Project("Project 1", "Active");
        Project project2 = new Project("Project 2", "Inactive");

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<ProjectDto> projects = projectService.getProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals("Project 1", projects.get(0).getProjectName());
    }

    @Test
    void testGetProjectsByUserId() {
        Project project = new Project("Project 1", "Active");

        when(projectRepository.findDistinctByProjectMembers_ProjectMemberPk_UserId("user1")).thenReturn(Arrays.asList(project));

        List<ProjectDto> projects = projectService.getProjectsByUserId("user1");

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Project 1", projects.get(0).getProjectName());
    }

    @Test
    void testGetProjectByProjectId() {
        Project project = new Project("Project 1", "Active");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectDto projectDto = projectService.getProjectByProjectId(1L);

        assertNotNull(projectDto);
        assertEquals("Project 1", projectDto.getProjectName());
    }

    @Test
    void testCreateProject() {
        Project project = new Project("New Project", "Active");
        Project savedProject = new Project("New Project", "Active");

        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        ProjectDto projectDto = projectService.createProject(project);

        assertNotNull(projectDto);
        assertEquals("New Project", projectDto.getProjectName());
    }

    @Test
    void testUpdateProject() {
        Project project = new Project("Old Project", "Inactive");
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project updatedProject = new Project("Updated Project", "Active");
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);

        ProjectDto projectDto = projectService.updateProject(1L, "Updated Project", "Active");

        assertNotNull(projectDto);
        assertEquals("Updated Project", projectDto.getProjectName());
    }

    @Test
    void testDeleteProject() {
        Project project = new Project("Project to delete", "Active");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }
}
