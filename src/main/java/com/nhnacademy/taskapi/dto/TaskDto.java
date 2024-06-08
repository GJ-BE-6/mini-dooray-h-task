package com.nhnacademy.taskapi.dto;

import com.nhnacademy.taskapi.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private Long projectId;
    private String userId;

    public TaskDto(Long taskId, Task task) {
        this.taskId = taskId;
        this.taskName = task.getName();
        this.taskDescription = task.getDescription();
        this.taskStatus = task.getStatus();
        this.projectId = task.getProject().getId();
        this.userId = task.getUserId();
    }

    public TaskDto(Task task) {
        this.taskId = task.getId();
        this.taskName = task.getName();
        this.taskDescription = task.getDescription();
        this.taskStatus = task.getStatus();
        this.projectId = task.getProject().getId();
        this.userId = task.getUserId();
    }
}
