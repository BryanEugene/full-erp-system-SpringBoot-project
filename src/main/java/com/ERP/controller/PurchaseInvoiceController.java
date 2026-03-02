package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.PurchaseInvoice;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.PurchaseInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase-invoices")
@RequiredArgsConstructor
public class PurchaseInvoiceController {
    private final PurchaseInvoiceRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PurchaseInvoice>>> getAll(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findAll(PageRequest.of(page, size, Sort.by("date").descending()))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseInvoice>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase invoice not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseInvoice>> create(@RequestBody PurchaseInvoice inv) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(inv)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseInvoice>> update(@PathVariable Long id, @RequestBody PurchaseInvoice inv) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Purchase invoice not found: " + id);
        inv.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(inv)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
