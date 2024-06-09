package com.nhnacademy.taskapi.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TagResponseDTO {
    private Long projectId;
    private String tagName;
    private Long tagId;
}

