package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.LeaveRequest;
import com.ERP.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<LeaveRequest>>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long employeeId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(leaveRequestService.getAll(status, employeeId, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveRequest>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(leaveRequestService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeaveRequest>> create(@RequestBody LeaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Leave request submitted", leaveRequestService.create(request)));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveRequest>> approve(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Leave request updated",
                leaveRequestService.approve(id, body.get("status"))));
    }
}
