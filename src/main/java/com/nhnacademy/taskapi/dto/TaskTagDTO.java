package com.nhnacademy.taskapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskTagDTO {
    private Long tagId;
    private Long taskId;
}
