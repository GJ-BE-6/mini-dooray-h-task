package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("project.sql")
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveTag() {
        long projectId = 999L;
        Optional<Project> project = projectRepository.findById(projectId);
        Tag tag = new Tag("test-tag-name", project.orElse(null));
        entityManager.persist(tag);
        entityManager.flush();

        List<Tag> tags = tagRepository.findAllByProjectId(projectId);
        assertThat(tags).hasSize(1).contains(tag);
    }

    @Test
    public void findByTagId() {
        long projectId = 999L;
        Optional<Project> project = projectRepository.findById(projectId);
        Tag tag = new Tag("test-tag-name", project.orElse(null));
        entityManager.persist(tag);
        entityManager.flush();
        List<Tag> tags = tagRepository.findAllByProjectId(projectId);

        Tag foundTag = tags.getFirst();
        Long tagId = tag.getId();

        tagRepository.findById(tagId);

        assertThat(foundTag.getName()).isEqualTo("test-tag-name");
        assertThat(foundTag.getProject().getId()).isEqualTo(projectId);
    }
}
