package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Project;
import com.ERP.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Project>>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getAll(search, status, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Project>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Project>> create(@RequestBody Project project) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created", projectService.create(project)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Project>> update(@PathVariable Long id, @RequestBody Project project) {
        return ResponseEntity.ok(ApiResponse.success("Project updated", projectService.update(id, project)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Project>> updateStatus(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                projectService.updateStatus(id, body.get("status"))));
    }
}
