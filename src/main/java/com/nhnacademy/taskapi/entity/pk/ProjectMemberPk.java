package com.nhnacademy.taskapi.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectMemberPk {

    @Column(nullable = false, length = 20)
    private String userId;

    @Column(nullable = false)
    private Long projectId;
}
