package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.SalesOrder;
import com.ERP.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/sales-orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SalesOrder>>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(salesOrderService.getAll(status, customerId, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesOrder>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(salesOrderService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalesOrder>> create(@RequestBody SalesOrder order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order created", salesOrderService.create(order)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SalesOrder>> updateStatus(@PathVariable Long id,
                                                                 @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                salesOrderService.updateStatus(id, body.get("status"))));
    }
}
