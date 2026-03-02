package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.StockLevel;
import com.ERP.entity.Warehouse;
import com.ERP.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Warehouse>>> getAll(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getById(id)));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<List<StockLevel>>> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getStock(id)));
    }
}
