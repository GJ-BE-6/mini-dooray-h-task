package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.TaskTag;
import com.nhnacademy.taskapi.entity.pk.TaskTagPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTagPk> {

    @Modifying
    @Query("DELETE FROM TaskTag tt WHERE tt.task.id = :taskId AND tt.tag.id = :tagId")
    void deleteTaskTag(long taskId, long tagId);

    List<TaskTag> findByTaskId(Long taskId);

}
