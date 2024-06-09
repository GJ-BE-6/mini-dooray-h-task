package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.dto.MilestoneDTO;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("project.sql")
public class MilestoneRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void findMilestoneByProjectId() {
        Long projectId = 1000L;
        Project project = projectRepository.findById(projectId).get();
        Milestone milestone = new Milestone("name", ZonedDateTime.now(), ZonedDateTime.now().plusDays(3), project, null);
        entityManager.persist(milestone);
        entityManager.flush();
        entityManager.clear();

        List<Milestone> foundMilestones = milestoneRepository.findMilestonesByProjectId(projectId);

        assertThat(foundMilestones).hasSize(1);
        assertThat(foundMilestones.getFirst().getName()).isEqualTo(milestone.getName());
    }

    @Test
    public void findMilestoneByTaskId() {
        Long projectId = 999L;
        Project project = projectRepository.findById(projectId).get();
        Long taskId = 1234L;
        Task task = taskRepository.findById(taskId).get();

        Milestone milestone1 = new Milestone("testMilestone1", ZonedDateTime.now(), ZonedDateTime.now().plusDays(3), project, task);

        entityManager.persist(milestone1);
        entityManager.flush();
        entityManager.clear();

        Milestone foundMilestone = milestoneRepository.findMilestoneByTaskId(taskId);

        assertThat(foundMilestone.getName()).isEqualTo("testMilestone1");
        assertThat(foundMilestone.getProject().getId()).isEqualTo(projectId);
        assertThat(foundMilestone.getTask().getId()).isEqualTo(taskId);
    }
}

