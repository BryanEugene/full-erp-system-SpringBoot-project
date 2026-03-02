package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.SalesQuotation;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.SalesQuotationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales-quotations")
@RequiredArgsConstructor
public class SalesQuotationController {
    private final SalesQuotationRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SalesQuotation>>> getAll(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findAll(PageRequest.of(page, size, Sort.by("date").descending()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesQuotation>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quotation not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalesQuotation>> create(@RequestBody SalesQuotation q) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(q)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesQuotation>> update(@PathVariable Long id, @RequestBody SalesQuotation q) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Quotation not found: " + id);
        q.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(q)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
