package com.nhnacademy.taskapi;

import com.nhnacademy.taskapi.entity.*;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.service.ProjectService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PkTest {

//    @Autowired
//    private TagRepository tagRepository;

//    @Autowired
//    private TaskRepository taskRepository;
//
    @Autowired
    private ProjectRepository projectRepository;

//    @Autowired
//    private TaskTagRepository taskTagRepository;

//    @Autowired
//    private ProjectMemberRepository projectMemberRepository;

    @PersistenceContext
    private EntityManager entityManager;

//    @Sql("project.sql")
    @Test
    void test() {
//        ProjectMember projectMember = new ProjectMember("1", "name", "status");

        Project project = new Project("name1", "status");
        projectRepository.save(project);

        Project foundProject = entityManager.find(Project.class, project.getId());

        Project foundProject2 = projectRepository.findById(project.getId()).get();

        Assertions.assertEquals(foundProject.getId(), foundProject2.getId());

//        Tag tag = new Tag("ff", foundProject);
//        Tag savedTag = tagRepository.save(tag);
//
//        Task task = new Task("1", "1", "1", foundProject, "1");
//        Task savedTask = taskRepository.save(task);
//
//        //TaskTagsPk taskTagsPk = new TaskTagsPk(savedTask.getTaskId(), savedTag.getTaskId());
//        Tag foundTag = tagRepository.findById(1L).orElse(null);
//        Task foundTask = taskRepository.findById(1L).orElse(null);
//
//        assert foundTag != null;
//        assert foundTask != null;
//        Assertions.assertNotNull(foundTag.getTaskId());
//
//        TaskTag savedtaskTag = new TaskTag(foundTag, foundTask);
//        TaskTag savedTaskTag = taskTagRepository.save(savedtaskTag);
//
//        Assertions.assertEquals(savedTag.getTaskId(), savedTaskTag.getTaskTagPk().getTagId());
//        Assertions.assertEquals(savedTask.getTaskId(), savedTaskTag.getTaskTagPk().getTaskId());
//        entityManager.flush();
    }
}
