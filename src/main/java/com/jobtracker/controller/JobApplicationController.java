package com.jobtracker.controller;

import com.jobtracker.dto.JobApplicationRequest;
import com.jobtracker.dto.JobApplicationResponse;
import com.jobtracker.entity.JobApplication.ApplicationStatus;
import com.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for job application endpoints.
 * Demonstrates REST API design and Spring MVC patterns.
 */
@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {
    
    private final JobApplicationService service;
    
    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<JobApplicationResponse> createApplication(
            @Valid @RequestBody JobApplicationRequest request) {
        JobApplicationResponse response = service.createApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(service.getAllApplications());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getApplicationById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(service.updateApplication(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        service.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsByStatus(
            @PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(service.getApplicationsByStatus(status));
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<JobApplicationResponse>> getActiveApplications() {
        return ResponseEntity.ok(service.getActiveApplications());
    }
    
    @GetMapping("/interview")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsInInterview() {
        return ResponseEntity.ok(service.getApplicationsInInterview());
    }
    
    @GetMapping("/stats")
    public ResponseEntity<JobApplicationService.ApplicationStats> getStatistics() {
        return ResponseEntity.ok(service.getStatistics());
    }
}