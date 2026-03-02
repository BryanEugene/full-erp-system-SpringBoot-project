package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Payroll;
import com.ERP.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Payroll>>> getAll(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(payrollService.getAll(year, month, status, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payroll>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(payrollService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Payroll>> create(@RequestBody Payroll payroll) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payroll created", payrollService.create(payroll)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Payroll>> updateStatus(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                payrollService.updateStatus(id, body.get("status"))));
    }
}
