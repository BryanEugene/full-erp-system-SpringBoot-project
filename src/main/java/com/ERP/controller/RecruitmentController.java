package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Recruitment;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recruitment")
@RequiredArgsConstructor
public class RecruitmentController {
    private final RecruitmentRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Recruitment>>> getAll(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Recruitment>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recruitment not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Recruitment>> create(@RequestBody Recruitment r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(r)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Recruitment>> update(@PathVariable Long id, @RequestBody Recruitment r) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Recruitment not found: " + id);
        r.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(r)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
