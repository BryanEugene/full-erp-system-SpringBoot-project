package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.Department;
import com.ERP.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(departmentService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(departmentService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Department dept) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Department created", departmentService.create(dept)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> update(@PathVariable Long id,
                                                          @RequestBody Department dept) {
        return ResponseEntity.ok(ApiResponse.success("Department updated", departmentService.update(id, dept)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Department deactivated"));
    }
}
