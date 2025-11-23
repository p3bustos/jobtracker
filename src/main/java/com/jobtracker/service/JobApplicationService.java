package com.jobtracker.service;

import com.jobtracker.dto.JobApplicationRequest;
import com.jobtracker.dto.JobApplicationResponse;
import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.JobApplication.ApplicationStatus;
import com.jobtracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class JobApplicationService {
    
    private final JobApplicationRepository repository;
    
    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }
    
    public JobApplicationResponse createApplication(JobApplicationRequest request) {
        JobApplication application = new JobApplication();
        mapRequestToEntity(request, application);
        
        JobApplication saved = repository.save(application);
        return JobApplicationResponse.fromEntity(saved);
    }
    
    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request) {
        JobApplication application = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        
        mapRequestToEntity(request, application);
        JobApplication updated = repository.save(application);
        return JobApplicationResponse.fromEntity(updated);
    }
    
    public JobApplicationResponse getApplicationById(Long id) {
        JobApplication application = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return JobApplicationResponse.fromEntity(application);
    }
    
    public List<JobApplicationResponse> getAllApplications() {
        return repository.findAll().stream()
            .map(JobApplicationResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public List<JobApplicationResponse> getApplicationsByStatus(ApplicationStatus status) {
        return repository.findByStatus(status).stream()
            .map(JobApplicationResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public List<JobApplicationResponse> getActiveApplications() {
        return repository.findActiveApplications().stream()
            .map(JobApplicationResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public List<JobApplicationResponse> getApplicationsInInterview() {
        return repository.findApplicationsInInterview().stream()
            .map(JobApplicationResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public void deleteApplication(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    public ApplicationStats getStatistics() {
        long total = repository.count();
        long active = repository.findActiveApplications().size();
        long inInterview = repository.findApplicationsInInterview().size();
        long rejected = repository.countByStatus(ApplicationStatus.REJECTED);
        long accepted = repository.countByStatus(ApplicationStatus.ACCEPTED);
        
        return new ApplicationStats(total, active, inInterview, rejected, accepted);
    }
    
    private void mapRequestToEntity(JobApplicationRequest request, JobApplication entity) {
        entity.setCompanyName(request.companyName());
        entity.setJobTitle(request.jobTitle());
        entity.setStatus(request.status());
        entity.setDescription(request.description());
        entity.setNotes(request.notes());
        entity.setLocation(request.location());
        entity.setJobUrl(request.jobUrl());
        entity.setSalaryMin(request.salaryMin());
        entity.setSalaryMax(request.salaryMax());
        entity.setAppliedDate(request.appliedDate() != null ? request.appliedDate() : LocalDateTime.now());
    }
    
    public record ApplicationStats(
        long total,
        long active,
        long inInterview,
        long rejected,
        long accepted
    ) {}
}