package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.ChartOfAccount;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.service.ChartOfAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chart-of-accounts")
@RequiredArgsConstructor
public class ChartOfAccountController {

    private final ChartOfAccountService coaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChartOfAccount>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(coaService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChartOfAccount>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(coaService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ChartOfAccount>> create(@RequestBody ChartOfAccount coa) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created", coaService.create(coa)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ChartOfAccount>> update(@PathVariable Long id,
                                                              @RequestBody ChartOfAccount coa) {
        return ResponseEntity.ok(ApiResponse.success("Account updated", coaService.update(id, coa)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        coaService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted"));
    }
}
