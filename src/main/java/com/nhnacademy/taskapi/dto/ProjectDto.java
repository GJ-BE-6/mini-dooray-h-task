package com.nhnacademy.taskapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String projectName;
    private String projectStatus;
}
