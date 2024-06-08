package com.nhnacademy.taskapi.entity;

import com.nhnacademy.taskapi.entity.pk.TaskTagPk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "task_tags")
public class TaskTag {

    @EmbeddedId
    private TaskTagPk taskTagPk;

    @Setter
    @MapsId("tagId")
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "tag_id")
    private Tag tag;

    @Setter
    @MapsId("taskId")
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "task_id")
    private Task task;

    public TaskTag(Tag tag, Task task) {
        this.tag = tag;
        this.task = task;
        this.taskTagPk = new TaskTagPk(tag.getId(), task.getId());
    }
}
