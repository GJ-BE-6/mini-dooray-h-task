package com.nhnacademy.taskapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDTO {
    private String userId;
    private String content;
    private Long taskId;
}
