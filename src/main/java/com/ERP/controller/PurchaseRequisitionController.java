package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.PurchaseRequisition;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.PurchaseRequisitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase-requisitions")
@RequiredArgsConstructor
public class PurchaseRequisitionController {
    private final PurchaseRequisitionRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PurchaseRequisition>>> getAll(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findAll(PageRequest.of(page, size, Sort.by("date").descending()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseRequisition>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("PR not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseRequisition>> create(@RequestBody PurchaseRequisition pr) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(pr)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseRequisition>> update(@PathVariable Long id, @RequestBody PurchaseRequisition pr) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("PR not found: " + id);
        pr.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(pr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
