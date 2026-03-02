package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Employee;
import com.ERP.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Employee>>> getAll(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(employeeService.getAll(search, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(employeeService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> create(@RequestBody Employee emp) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Employee created", employeeService.create(emp)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> update(@PathVariable Long id,
                                                        @RequestBody Employee emp) {
        return ResponseEntity.ok(ApiResponse.success("Employee updated", employeeService.update(id, emp)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> terminate(@PathVariable Long id) {
        employeeService.terminate(id);
        return ResponseEntity.ok(ApiResponse.success("Employee terminated"));
    }
}
