package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.ProjectMemberDto;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMember;
import com.nhnacademy.taskapi.entity.pk.ProjectMemberPk;
import com.nhnacademy.taskapi.repository.ProjectMemberRepository;
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

class ProjectMemberServiceTest {

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectMemberService projectMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProjectMember() {
        Project project = new Project("Project 1", "Active");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.existsByProjectAndProjectMemberPkUserId(project, "user1")).thenReturn(false);
        when(projectMemberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectMemberDto projectMemberDto = projectMemberService.addProjectMember(1L, "user1");

        assertNotNull(projectMemberDto);
        assertEquals("user1", projectMemberDto.getUserId());
        assertEquals("Member", projectMemberDto.getRole());
    }

    @Test
    void testAddProjectAdmin() {
        Project project = new Project("Project 1", "Active");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectMemberDto projectMemberDto = projectMemberService.addProjectAdmin(1L, "admin1");

        assertNotNull(projectMemberDto);
        assertEquals("admin1", projectMemberDto.getUserId());
        assertEquals("Admin", projectMemberDto.getRole());
    }

    @Test
    void testGetProjectMembersByProjectId() {
        Project project = new Project("Project 1", "Active");
        ProjectMember member1 = new ProjectMember("Member", "user1", project);
        ProjectMember member2 = new ProjectMember("Admin", "admin1", project);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMemberRepository.findByProject(project)).thenReturn(Arrays.asList(member1, member2));

        List<ProjectMemberDto> projectMembers = projectMemberService.getProjectMembersByProjectId(1L);

        assertNotNull(projectMembers);
        assertEquals(2, projectMembers.size());
        assertEquals("user1", projectMembers.get(0).getUserId());
        assertEquals("admin1", projectMembers.get(1).getUserId());
    }

    @Test
    void testDeleteProjectMember() {
        ProjectMemberPk projectMemberPk = new ProjectMemberPk("user1", 1L);

        when(projectMemberRepository.existsById(projectMemberPk)).thenReturn(true);
        doNothing().when(projectMemberRepository).deleteById(projectMemberPk);

        projectMemberService.deleteProjectMember(1L, "user1");

        verify(projectMemberRepository, times(1)).deleteById(projectMemberPk);
    }
}
