package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.TagDTO;
import com.nhnacademy.taskapi.dto.TaskTagDTO;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.TaskTag;
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

    @GetMapping("/tags/{projectId}")
    public ResponseEntity<List<TagDTO>> getTagByProjectId(@PathVariable("projectId") Long projectId) {
        List<TagDTO> resp = tagService.getTagsByProjectId(projectId).stream()
                .map(tag -> new TagDTO(tag.getProject().getId(), tag.getName()))
                .toList();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/task-tags")
    public ResponseEntity<Void> setTagToTask(@RequestBody TaskTagDTO taskTagDTO) {
        tagService.setTagToTask(taskTagDTO.getTaskId(), taskTagDTO.getTagId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/task-tags")
    public ResponseEntity<Void> deleteTagFromTask(@RequestBody TaskTagDTO taskTagDTO) {
        tagService.deleteTagFromTask(taskTagDTO.getTaskId(), taskTagDTO.getTagId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/task-tags/{taskId}")
    public ResponseEntity<List<TagDTO>> getTaskTagsByTaskId(@PathVariable("taskId") Long taskId) {
        List<TagDTO> tags = tagService.getTagsByTaskId(taskId);
        return ResponseEntity.ok(tags);
    }
}
