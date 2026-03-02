package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.*;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manufacturing")
@RequiredArgsConstructor
public class ManufacturingController {
    private final BillOfMaterialsRepository bomRepo;
    private final ProductionOrderRepository productionRepo;
    private final WorkOrderRepository workOrderRepo;
    private final QualityControlRepository qcRepo;

    // BOM
    @GetMapping("/bill-of-materials")
    public ResponseEntity<ApiResponse<Page<BillOfMaterials>>> getAllBom(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(bomRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/bill-of-materials")
    public ResponseEntity<ApiResponse<BillOfMaterials>> createBom(@RequestBody BillOfMaterials bom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", bomRepo.save(bom)));
    }
    @PutMapping("/bill-of-materials/{id}")
    public ResponseEntity<ApiResponse<BillOfMaterials>> updateBom(@PathVariable Long id, @RequestBody BillOfMaterials bom) {
        if (!bomRepo.existsById(id)) throw new ResourceNotFoundException("BOM not found: " + id);
        bom.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", bomRepo.save(bom)));
    }
    @DeleteMapping("/bill-of-materials/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBom(@PathVariable Long id) {
        bomRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Production Orders
    @GetMapping("/production-orders")
    public ResponseEntity<ApiResponse<Page<ProductionOrder>>> getAllProduction(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(productionRepo.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }
    @PostMapping("/production-orders")
    public ResponseEntity<ApiResponse<ProductionOrder>> createProduction(@RequestBody ProductionOrder po) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", productionRepo.save(po)));
    }
    @PutMapping("/production-orders/{id}")
    public ResponseEntity<ApiResponse<ProductionOrder>> updateProduction(@PathVariable Long id, @RequestBody ProductionOrder po) {
        if (!productionRepo.existsById(id)) throw new ResourceNotFoundException("Production order not found: " + id);
        po.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", productionRepo.save(po)));
    }
    @DeleteMapping("/production-orders/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduction(@PathVariable Long id) {
        productionRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Work Orders
    @GetMapping("/work-orders")
    public ResponseEntity<ApiResponse<Page<WorkOrder>>> getAllWorkOrders(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(workOrderRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/work-orders")
    public ResponseEntity<ApiResponse<WorkOrder>> createWorkOrder(@RequestBody WorkOrder wo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", workOrderRepo.save(wo)));
    }
    @PutMapping("/work-orders/{id}")
    public ResponseEntity<ApiResponse<WorkOrder>> updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrder wo) {
        if (!workOrderRepo.existsById(id)) throw new ResourceNotFoundException("Work order not found: " + id);
        wo.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", workOrderRepo.save(wo)));
    }
    @DeleteMapping("/work-orders/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkOrder(@PathVariable Long id) {
        workOrderRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Quality Control
    @GetMapping("/quality-control")
    public ResponseEntity<ApiResponse<Page<QualityControl>>> getAllQC(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(qcRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/quality-control")
    public ResponseEntity<ApiResponse<QualityControl>> createQC(@RequestBody QualityControl qc) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", qcRepo.save(qc)));
    }
    @PutMapping("/quality-control/{id}")
    public ResponseEntity<ApiResponse<QualityControl>> updateQC(@PathVariable Long id, @RequestBody QualityControl qc) {
        if (!qcRepo.existsById(id)) throw new ResourceNotFoundException("QC not found: " + id);
        qc.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", qcRepo.save(qc)));
    }
    @DeleteMapping("/quality-control/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQC(@PathVariable Long id) {
        qcRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
