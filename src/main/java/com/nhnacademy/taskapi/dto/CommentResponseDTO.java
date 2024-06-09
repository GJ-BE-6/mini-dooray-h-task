package com.nhnacademy.taskapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponseDTO {
    private String comment;
    private String userId;
    private Long taskId;
    private Long commentId;
}
