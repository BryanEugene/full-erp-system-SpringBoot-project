package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.PurchaseOrder;
import com.ERP.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PurchaseOrder>>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long vendorId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.getAll(status, vendorId, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrder>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrder>> create(@RequestBody PurchaseOrder order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Purchase order created", purchaseOrderService.create(order)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PurchaseOrder>> updateStatus(@PathVariable Long id,
                                                                    @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                purchaseOrderService.updateStatus(id, body.get("status"))));
    }
}
