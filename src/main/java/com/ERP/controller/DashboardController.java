package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.repository.SalesOrderRepository;
import com.ERP.repository.PurchaseOrderRepository;
import com.ERP.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final EmployeeService employeeService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final VendorService vendorService;
    private final SalesOrderService salesOrderService;
    private final PurchaseOrderService purchaseOrderService;
    private final ProjectService projectService;
    private final SalesOrderRepository salesOrderRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSummary() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalEmployees", employeeService.count());
        data.put("activeEmployees", employeeService.countActive());
        data.put("totalProducts", productService.count());
        data.put("totalCustomers", customerService.count());
        data.put("totalVendors", vendorService.count());
        data.put("totalSalesOrders", salesOrderService.count());
        data.put("totalPurchaseOrders", purchaseOrderService.count());
        data.put("totalProjects", projectService.count());
        data.put("recentSalesOrders", salesOrderRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"))).getContent());
        data.put("recentPurchaseOrders", purchaseOrderRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"))).getContent());
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
