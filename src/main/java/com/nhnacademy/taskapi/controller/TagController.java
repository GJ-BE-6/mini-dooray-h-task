package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.TagDTO;
import com.nhnacademy.taskapi.dto.TagResponseDTO;
import com.nhnacademy.taskapi.dto.TaskTagDTO;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/tags")
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
        Tag createdTag = tagService.createTag(tagDTO);
        TagDTO resp = TagDTO.builder()
                .projectId(createdTag.getProject().getId())
                .tagName(createdTag.getName())
                .build();

        return ResponseEntity.ok(resp);
    }

    @PutMapping("/tags/{tagId}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable("tagId") Long tagId, @RequestBody TagDTO tagDTO) {
        Tag tag = tagService.updateTag(tagId, tagDTO);
        TagDTO resp = TagDTO.builder()
                .projectId(tag.getProject().getId())
                .tagName(tag.getName())
                .build();
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagId") Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projects/{projectId}/tags")
    public ResponseEntity<List<TagResponseDTO>> getTagByProjectId(@PathVariable("projectId") Long projectId) {
        List<TagResponseDTO> resp = tagService.getTagsByProjectId(projectId).stream()
                .map(tag -> new TagResponseDTO(tag.getProject().getId(), tag.getName(), tag.getId()))
                .toList();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/tasks/tag")
    public ResponseEntity<Void> setTagToTask(@RequestBody TaskTagDTO taskTagDTO) {
        tagService.setTagToTask(taskTagDTO.getTaskId(), taskTagDTO.getTagId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/tag")
    public ResponseEntity<Void> deleteTagFromTask(@RequestBody TaskTagDTO taskTagDTO) {
        tagService.deleteTagFromTask(taskTagDTO.getTaskId(), taskTagDTO.getTagId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{taskId}/tags")
    public ResponseEntity<List<TagResponseDTO>> getTaskTagsByTaskId(@PathVariable("taskId") Long taskId) {
        List<TagResponseDTO> tags = tagService.getTagsByTaskId(taskId);
        return ResponseEntity.ok(tags);
    }
}
