package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.AuditLog;
import com.ERP.entity.Setting;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.AuditLogRepository;
import com.ERP.repository.RoleRepository;
import com.ERP.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/system")
@RequiredArgsConstructor
public class SystemController {
    private final AuditLogRepository auditLogRepo;
    private final SettingRepository settingRepo;
    private final RoleRepository roleRepository;

    @GetMapping("/audit-logs")
    public ResponseEntity<ApiResponse<Page<AuditLog>>> getAuditLogs(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            auditLogRepo.findAll(PageRequest.of(page, size, Sort.by("timestamp").descending()))));
    }

    @GetMapping("/settings")
    public ResponseEntity<ApiResponse<Page<Setting>>> getSettings(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="100") int size) {
        return ResponseEntity.ok(ApiResponse.success(settingRepo.findAll(PageRequest.of(page, size))));
    }

    @PutMapping("/settings/{id}")
    public ResponseEntity<ApiResponse<Setting>> updateSetting(@PathVariable Long id, @RequestBody Setting setting) {
        Setting existing = settingRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Setting not found: " + id));
        existing.setValue(setting.getValue());
        existing.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(ApiResponse.success("Updated", settingRepo.save(existing)));
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<?>> getRoles() {
        return ResponseEntity.ok(ApiResponse.success(roleRepository.findAll()));
    }
}
