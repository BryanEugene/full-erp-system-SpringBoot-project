package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.SalesInvoice;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.SalesInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales-invoices")
@RequiredArgsConstructor
public class SalesInvoiceController {
    private final SalesInvoiceRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SalesInvoice>>> getAll(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findAll(PageRequest.of(page, size, Sort.by("date").descending()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesInvoice>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalesInvoice>> create(@RequestBody SalesInvoice inv) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(inv)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesInvoice>> update(@PathVariable Long id, @RequestBody SalesInvoice inv) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Invoice not found: " + id);
        inv.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(inv)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
