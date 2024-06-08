package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.TaskTag;
import com.nhnacademy.taskapi.entity.pk.TaskTagPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTagPk> {
}
