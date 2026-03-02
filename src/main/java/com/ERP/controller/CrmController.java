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
@RequestMapping("/api/v1/crm")
@RequiredArgsConstructor
public class CrmController {
    private final LeadRepository leadRepo;
    private final OpportunityRepository opportunityRepo;
    private final ActivityRepository activityRepo;
    private final CampaignRepository campaignRepo;

    // Leads
    @GetMapping("/leads")
    public ResponseEntity<ApiResponse<Page<Lead>>> getLeads(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(leadRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/leads")
    public ResponseEntity<ApiResponse<Lead>> createLead(@RequestBody Lead l) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", leadRepo.save(l)));
    }
    @PutMapping("/leads/{id}")
    public ResponseEntity<ApiResponse<Lead>> updateLead(@PathVariable Long id, @RequestBody Lead l) {
        if (!leadRepo.existsById(id)) throw new ResourceNotFoundException("Lead not found: " + id);
        l.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", leadRepo.save(l)));
    }
    @DeleteMapping("/leads/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLead(@PathVariable Long id) {
        leadRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Opportunities
    @GetMapping("/opportunities")
    public ResponseEntity<ApiResponse<Page<Opportunity>>> getOpportunities(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(opportunityRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/opportunities")
    public ResponseEntity<ApiResponse<Opportunity>> createOpportunity(@RequestBody Opportunity o) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", opportunityRepo.save(o)));
    }
    @PutMapping("/opportunities/{id}")
    public ResponseEntity<ApiResponse<Opportunity>> updateOpportunity(@PathVariable Long id, @RequestBody Opportunity o) {
        if (!opportunityRepo.existsById(id)) throw new ResourceNotFoundException("Opportunity not found: " + id);
        o.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", opportunityRepo.save(o)));
    }
    @DeleteMapping("/opportunities/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOpportunity(@PathVariable Long id) {
        opportunityRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Activities
    @GetMapping("/activities")
    public ResponseEntity<ApiResponse<Page<Activity>>> getActivities(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(activityRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/activities")
    public ResponseEntity<ApiResponse<Activity>> createActivity(@RequestBody Activity a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", activityRepo.save(a)));
    }
    @PutMapping("/activities/{id}")
    public ResponseEntity<ApiResponse<Activity>> updateActivity(@PathVariable Long id, @RequestBody Activity a) {
        if (!activityRepo.existsById(id)) throw new ResourceNotFoundException("Activity not found: " + id);
        a.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", activityRepo.save(a)));
    }
    @DeleteMapping("/activities/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(@PathVariable Long id) {
        activityRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }

    // Campaigns
    @GetMapping("/campaigns")
    public ResponseEntity<ApiResponse<Page<Campaign>>> getCampaigns(
            @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="20") int size) {
        return ResponseEntity.ok(ApiResponse.success(campaignRepo.findAll(PageRequest.of(page, size))));
    }
    @PostMapping("/campaigns")
    public ResponseEntity<ApiResponse<Campaign>> createCampaign(@RequestBody Campaign c) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Created", campaignRepo.save(c)));
    }
    @PutMapping("/campaigns/{id}")
    public ResponseEntity<ApiResponse<Campaign>> updateCampaign(@PathVariable Long id, @RequestBody Campaign c) {
        if (!campaignRepo.existsById(id)) throw new ResourceNotFoundException("Campaign not found: " + id);
        c.setId(id); return ResponseEntity.ok(ApiResponse.success("Updated", campaignRepo.save(c)));
    }
    @DeleteMapping("/campaigns/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCampaign(@PathVariable Long id) {
        campaignRepo.deleteById(id); return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
