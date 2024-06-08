package com.nhnacademy.taskapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectMemberDto {
    private String userId;
    private Long projectId;
    private String role;
}
