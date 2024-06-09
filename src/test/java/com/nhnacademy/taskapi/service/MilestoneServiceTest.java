package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.MilestoneDTO;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class MilestoneServiceTest {

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private MilestoneService milestoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMilestone() {
        MilestoneDTO milestoneDTO = new MilestoneDTO();
        milestoneDTO.setMilestoneName("New Milestone");
        milestoneDTO.setStartDate(ZonedDateTime.now());
        milestoneDTO.setDueDate(ZonedDateTime.now().plusDays(10));
        milestoneDTO.setProjectId(10000L);

        Project project = new Project("project", "활성");

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Milestone createdMilestone = milestoneService.createMilestone(milestoneDTO);

        assertNotNull(createdMilestone);
        assertEquals("New Milestone", createdMilestone.getName());
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }

    @Test
    void testUpdateMilestone() {
        Milestone existingMilestone = new Milestone();
        existingMilestone.setName("Old Milestone");

        MilestoneDTO milestoneDTO = new MilestoneDTO();
        milestoneDTO.setMilestoneName("Updated Milestone");
        milestoneDTO.setStartDate(ZonedDateTime.now());
        milestoneDTO.setDueDate(ZonedDateTime.now().plusDays(10));

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(existingMilestone));
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Milestone updatedMilestone = milestoneService.updateMilestone(1L, milestoneDTO);

        assertNotNull(updatedMilestone);
        assertEquals("Updated Milestone", updatedMilestone.getName());
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }

    @Test
    void testDeleteMilestone() {
        doNothing().when(milestoneRepository).deleteById(anyLong());

        milestoneService.deleteMilestone(1L);

        verify(milestoneRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetMilestone() {
        Milestone milestone = new Milestone();
        milestone.setName("Test Milestone");

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(milestone));

        Milestone retrievedMilestone = milestoneService.getMilestone(1L);

        assertNotNull(retrievedMilestone);
        assertEquals("Test Milestone", retrievedMilestone.getName());
        verify(milestoneRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMilestoneByProjectId() {
        Milestone milestone1 = new Milestone();
        milestone1.setName("Milestone 1");

        Milestone milestone2 = new Milestone();
        milestone2.setName("Milestone 2");

        List<Milestone> milestones = Arrays.asList(milestone1, milestone2);

        when(milestoneRepository.findMilestonesByProjectId(anyLong())).thenReturn(milestones);

        List<Milestone> retrievedMilestones = milestoneService.getMilestoneByProjectId(1L);

        assertNotNull(retrievedMilestones);
        assertEquals(2, retrievedMilestones.size());
        verify(milestoneRepository, times(1)).findMilestonesByProjectId(1L);
    }

    @Test
    void testSetMileStoneToTask() {
        Milestone milestone = new Milestone();
        milestone.setName("Milestone");

        Task task = new Task();

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(milestone));
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Milestone updatedMilestone = milestoneService.setMileStoneToTask(1L, 1L);

        assertNotNull(updatedMilestone);
        assertEquals(task, updatedMilestone.getTask());
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }

    @Test
    void testDeleteMilestoneFromTask() {
        Milestone milestone = new Milestone();
        milestone.setName("Milestone");
        milestone.setTask(new Task());

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(milestone));
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        milestoneService.deleteMilestoneFromTask(1L);

        assertNotNull(milestone);
        assertNull(milestone.getTask());
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }
}

