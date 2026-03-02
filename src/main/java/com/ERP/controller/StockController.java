package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.StockLevel;
import com.ERP.entity.StockMovement;
import com.ERP.repository.StockLevelRepository;
import com.ERP.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StockController {
    private final StockLevelRepository stockLevelRepo;
    private final StockMovementRepository stockMovementRepo;

    @GetMapping("/stock-levels")
    public ResponseEntity<ApiResponse<List<StockLevel>>> getStockLevels() {
        return ResponseEntity.ok(ApiResponse.success(stockLevelRepo.findAll()));
    }

    @GetMapping("/stock-movements")
    public ResponseEntity<ApiResponse<Page<StockMovement>>> getStockMovements(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            stockMovementRepo.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @PostMapping("/stock-movements")
    public ResponseEntity<ApiResponse<StockMovement>> createMovement(@RequestBody StockMovement movement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", stockMovementRepo.save(movement)));
    }
}
