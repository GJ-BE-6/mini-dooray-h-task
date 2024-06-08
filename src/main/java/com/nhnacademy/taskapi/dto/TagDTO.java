package com.nhnacademy.taskapi.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TagDTO {
    private Long projectId;
    private String tagName;
}
