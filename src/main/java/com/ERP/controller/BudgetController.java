package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Budget;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Budget>>> getAll(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(repo.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Budget>> create(@RequestBody Budget budget) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(budget)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> update(@PathVariable Long id, @RequestBody Budget budget) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Budget not found: " + id);
        budget.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(budget)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
