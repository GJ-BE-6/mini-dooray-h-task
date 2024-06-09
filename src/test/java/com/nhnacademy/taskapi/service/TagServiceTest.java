package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.TagDTO;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.repository.TaskTagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTag() {
        Project project = new Project();

        TagDTO tagDTO = new TagDTO();
        tagDTO.setProjectId(1L);
        tagDTO.setTagName("testTag");

        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(tagRepository.save(any(Tag.class))).thenAnswer(i -> i.getArguments()[0]);

        Tag result = tagService.createTag(tagDTO);

        verify(projectRepository, times(1)).findById(any());
        verify(tagRepository, times(1)).save(any(Tag.class));
        assert "testTag".equals(result.getName());
    }

    @Test
    void testUpdateTag() {
        Tag tag = new Tag();
        tag.setName("old tag");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setProjectId(1L);
        tagDTO.setTagName("new tag");

        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenAnswer(i -> i.getArguments()[0]);

        Tag result = tagService.updateTag(1L, tagDTO);

        verify(tagRepository, times(1)).findById(any());
        verify(tagRepository, times(1)).save(any(Tag.class));
        assert "new tag".equals(result.getName());
    }
}
