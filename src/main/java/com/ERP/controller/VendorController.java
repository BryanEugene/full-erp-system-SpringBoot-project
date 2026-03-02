package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Vendor;
import com.ERP.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Vendor>>> getAll(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(vendorService.getAll(search, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vendor>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(vendorService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Vendor>> create(@RequestBody Vendor vendor) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Vendor created", vendorService.create(vendor)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Vendor>> update(@PathVariable Long id, @RequestBody Vendor vendor) {
        return ResponseEntity.ok(ApiResponse.success("Vendor updated", vendorService.update(id, vendor)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Vendor deactivated"));
    }
}
