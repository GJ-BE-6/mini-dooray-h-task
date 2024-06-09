package com.nhnacademy.taskapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskCreateDto {
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private Long projectId;
    private String userId;
}
