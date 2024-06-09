package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.TagDTO;
import com.nhnacademy.taskapi.dto.TagResponseDTO;
import com.nhnacademy.taskapi.dto.TaskTagDTO;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag API", description = "태그 관련 API 입니다.")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @Operation(summary = "태그 가져오기", description = "ID를 통해 특정 태그에 대한 정보를 검색합니다.")
    @GetMapping("/tags/{tagId}")
    public ResponseEntity<TagResponseDTO> getTag(@PathVariable Long tagId) {
        Tag tag = tagService.getTagByTagId(tagId);
        return ResponseEntity.ok(new TagResponseDTO(tag.getProject().getId(), tag.getName(), tag.getId()));
    }

    @Operation(summary = "태그 생성", description = "새로운 태그를 생성합니다.")
    @PostMapping("/tags")
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
        Tag createdTag = tagService.createTag(tagDTO);
        TagDTO resp = TagDTO.builder()
                .projectId(createdTag.getProject().getId())
                .tagName(createdTag.getName())
                .build();

        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "태그 수정", description = "기존의 태그를 수정합니다.")
    @PutMapping("/tags/{tagId}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable("tagId") Long tagId, @RequestBody TagDTO tagDTO) {
        Tag tag = tagService.updateTag(tagId, tagDTO);
        TagDTO resp = TagDTO.builder()
                .projectId(tag.getProject().getId())
                .tagName(tag.getName())
                .build();
        return ResponseEntity.ok(resp);
    }
    @Operation(summary = "태그 삭제", description = "특정 태그를 삭제합니다.")
    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable("tagId") Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "프로젝트별 태그 가져오기", description = "특정 프로젝트와 관련된 모든 태그를 검색합니다.")
    @GetMapping("/projects/{projectId}/tags")
    public ResponseEntity<List<TagResponseDTO>> getTagByProjectId(@PathVariable("projectId") Long projectId) {
        List<TagResponseDTO> resp = tagService.getTagsByProjectId(projectId).stream()
                .map(tag -> new TagResponseDTO(tag.getProject().getId(), tag.getName(), tag.getId()))
                .toList();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "태스크에 태그 할당", description = "특정 태스크에 태그를 할당합니다.")
    @PostMapping("/tasks/tag")
    public ResponseEntity<Void> setTagToTask(@RequestBody TaskTagDTO taskTagDTO) {
        tagService.setTagToTask(taskTagDTO.getTaskId(), taskTagDTO.getTagId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "태스크에서 태그 제거", description = "특정 태스크에서 태그를 제거합니다.")
    @DeleteMapping("/tasks/tag")
    public ResponseEntity<Void> deleteTagFromTask(@RequestBody TaskTagDTO taskTagDTO) {
        tagService.deleteTagFromTask(taskTagDTO.getTaskId(), taskTagDTO.getTagId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "태스크별 태그 가져오기", description = "특정 태스크와 관련된 모든 태그를 검색합니다.")
    @GetMapping("/tasks/{taskId}/tags")
    public ResponseEntity<List<TagResponseDTO>> getTaskTagsByTaskId(@PathVariable("taskId") Long taskId) {
        List<TagResponseDTO> tags = tagService.getTagsByTaskId(taskId);
        return ResponseEntity.ok(tags);
    }
}
