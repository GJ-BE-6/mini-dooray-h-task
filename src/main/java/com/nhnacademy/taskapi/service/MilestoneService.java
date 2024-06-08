package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.MilestoneDTO;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Task;

import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MilestoneService {
    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Milestone createMilestone(MilestoneDTO mileStoneDTO) {
        Project project =projectRepository.findById(mileStoneDTO.getProjectId()).orElse(null);
//        Task task = taskRepository.findById(mileStoneDTO.getTaskId()).orElse(null);
        Milestone createdMilestone = new Milestone(mileStoneDTO.getMilestoneName(), mileStoneDTO.getStartDate(), mileStoneDTO.getDueDate(), project, null);
        return milestoneRepository.save(createdMilestone);
    }

    public Milestone updateMilestone(Long id, MilestoneDTO mileStoneDTO) {
        Milestone milestone = milestoneRepository.findById(id).orElse(null);
        assert milestone != null;
        milestone.setName(mileStoneDTO.getMilestoneName());
        milestone.setStartDate(mileStoneDTO.getStartDate());
        milestone.setDueDate(mileStoneDTO.getDueDate());
        return milestoneRepository.save(milestone);
    }

    public void deleteMilestone(long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }

    public Milestone getMilestone(long milestoneId) {
        return milestoneRepository.findById(milestoneId).orElse(null);
    }

    public List<Milestone> getMilestoneByProjectId(long projectId) {
        return milestoneRepository.findMilestonesByProjectId(projectId);
    }

    public Milestone getMilestoneByTaskId(long taskId) {
        return milestoneRepository.findMilestoneByTaskId(taskId);
    }

    public Milestone setMileStoneToTask(long taskId, long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow();
        Task task = taskRepository.findById(taskId).orElseThrow();
        milestone.setTask(task);
        return milestoneRepository.save(milestone);
    }

    public void deleteMilestoneFromTask(long milestoneId, long taskId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow();

        log.info("getTask.getid : {}, taskId : {}", milestone.getTask().getId(), taskId);
        if (milestone.getTask().getId() == taskId) {
            milestone.setTask(null);
        }
        milestoneRepository.save(milestone);
    }
}
