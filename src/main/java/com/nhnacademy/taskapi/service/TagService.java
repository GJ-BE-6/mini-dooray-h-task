package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.TagDTO;
import com.nhnacademy.taskapi.dto.TagResponseDTO;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.TaskTag;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.repository.TaskTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TaskTagRepository taskTagRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Tag createTag(TagDTO tagDTO) {
        Project project = projectRepository.findById(tagDTO.getProjectId()).orElse(null);
        return tagRepository.save(new Tag(tagDTO.getTagName(), project));
    }

    public Tag updateTag(long tagId, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(tagId).orElseThrow();
        tag.setName(tagDTO.getTagName());
        return tagRepository.save(tag);
    }

    public void deleteTag(long tagId) {
        tagRepository.deleteById(tagId);
    }

    public List<Tag> getTagsByProjectId(Long projectId) {
        return tagRepository.findAllByProjectId(projectId);
    }

    public List<TagResponseDTO> getTagsByTaskId(Long taskId) {
        List<TaskTag> taskTags = taskTagRepository.findByTaskId(taskId);
        List<TagResponseDTO> tags = new ArrayList<>();
        for (TaskTag taskTag : taskTags) {
            tags.add(new TagResponseDTO(taskTag.getTask().getProject().getId(), taskTag.getTag().getName(), taskTag.getTag().getId()));
        }
        return tags;
    }

    // tag를 task에 등록 또는 삭제
    public void setTagToTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        Tag tag = tagRepository.findById(tagId).orElseThrow();
        taskTagRepository.save(new TaskTag(tag, task));
    }

    public void deleteTagFromTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        Tag tag = tagRepository.findById(tagId).orElseThrow();

        taskTagRepository.delete(new TaskTag(tag, task));
    }

    public Tag getTagByTagId(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow();
    }

}
