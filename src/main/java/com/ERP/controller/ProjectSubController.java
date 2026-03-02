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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectSubController {
    private final MilestoneRepository milestoneRepo;
    private final TaskRepository taskRepo;
    private final TimeEntryRepository timeEntryRepo;

    // Milestones
    @GetMapping("/milestones")
    public ResponseEntity<ApiResponse<Page<Milestone>>> getAllMilestones(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(milestoneRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/milestones")
    public ResponseEntity<ApiResponse<Milestone>> createMilestone(@RequestBody Milestone m) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", milestoneRepo.save(m)));
    }
    @PutMapping("/milestones/{id}")
    public ResponseEntity<ApiResponse<Milestone>> updateMilestone(@PathVariable Long id, @RequestBody Milestone m) {
        if (!milestoneRepo.existsById(id)) throw new ResourceNotFoundException("Milestone not found: " + id);
        m.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", milestoneRepo.save(m)));
    }
    @DeleteMapping("/milestones/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMilestone(@PathVariable Long id) {
        milestoneRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Tasks
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<Page<Task>>> getAllTasks(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(taskRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse<Task>> createTask(@RequestBody Task t) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", taskRepo.save(t)));
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id, @RequestBody Task t) {
        if (!taskRepo.existsById(id)) throw new ResourceNotFoundException("Task not found: " + id);
        t.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", taskRepo.save(t)));
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Time Entries
    @GetMapping("/time-entries")
    public ResponseEntity<ApiResponse<Page<TimeEntry>>> getAllTimeEntries(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(
            timeEntryRepo.findAll(PageRequest.of(page, size, Sort.by("date").descending()))));
    }
    @PostMapping("/time-entries")
    public ResponseEntity<ApiResponse<TimeEntry>> createTimeEntry(@RequestBody TimeEntry te) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", timeEntryRepo.save(te)));
    }
    @PutMapping("/time-entries/{id}")
    public ResponseEntity<ApiResponse<TimeEntry>> updateTimeEntry(@PathVariable Long id, @RequestBody TimeEntry te) {
        if (!timeEntryRepo.existsById(id)) throw new ResourceNotFoundException("Time entry not found: " + id);
        te.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", timeEntryRepo.save(te)));
    }
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTimeEntry(@PathVariable Long id) {
        timeEntryRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
