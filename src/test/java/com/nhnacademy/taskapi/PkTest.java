package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.TaskTag;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.repository.TaskTagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PkTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskTagRepository taskTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // TODO #3: test case
//    @Sql("project.sql")
    @Test
    void test() {
        Project project = new Project("name1", "status");
        projectRepository.save(project);

        Project foundProject = entityManager.find(Project.class, project.getId());

        Tag tag = new Tag("ff", foundProject);
        Tag savedTag = tagRepository.save(tag);

        Task task = new Task("1", "1", "1", foundProject, "1");
        Task savedTask = taskRepository.save(task);

        //TaskTagsPk taskTagsPk = new TaskTagsPk(savedTask.getId(), savedTag.getId());
        Tag foundTag = tagRepository.findById(1L).orElse(null);
        Task foundTask = taskRepository.findById(1L).orElse(null);

        assert foundTag != null;
        assert foundTask != null;
//        Assertions.assertNotNull(foundTag.getId());

        TaskTag savedtaskTag = new TaskTag(foundTag, foundTask);
        TaskTag savedTaskTag = taskTagRepository.save(savedtaskTag);

        Assertions.assertEquals(savedTag.getId(), savedTaskTag.getTaskTagPk().getTagId());
        Assertions.assertEquals(savedTask.getId(), savedTaskTag.getTaskTagPk().getTaskId());
        entityManager.flush();
    }
}
