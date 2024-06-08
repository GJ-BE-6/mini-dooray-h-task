package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findMilestonesByProjectId(Long projectId);

    Milestone findMilestoneByTaskId(Long taskId);

    @Query("UPDATE Milestone m SET m.task.id = null WHERE m.id = :id")
    void deleteMilestoneFromTask(long id);
}
