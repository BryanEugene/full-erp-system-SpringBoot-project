package com.ERP.controller;

import com.ERP.dto.ApiResponse;
import com.ERP.entity.JournalEntry;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/journal-entries")
@RequiredArgsConstructor
public class JournalEntryController {
    private final JournalEntryRepository repo;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<JournalEntry>>> getAll(
            @RequestParam(required=false) String status,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<JournalEntry> result = status != null ? repo.findByStatus(status, pageable) : repo.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JournalEntry>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal entry not found: " + id))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JournalEntry>> create(@RequestBody JournalEntry entry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", repo.save(entry)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JournalEntry>> update(@PathVariable Long id, @RequestBody JournalEntry entry) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Journal entry not found: " + id);
        entry.setId(id);
        return ResponseEntity.ok(ApiResponse.success("Updated", repo.save(entry)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
