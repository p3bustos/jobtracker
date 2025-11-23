package com.jobtracker.dto;

import com.jobtracker.entity.JobApplication.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record JobApplicationRequest(
    @NotBlank(message = "Company name is required")
    String companyName,
    
    @NotBlank(message = "Job title is required")
    String jobTitle,
    
    @NotNull(message = "Status is required")
    ApplicationStatus status,
    
    String description,
    String notes,
    String location,
    String jobUrl,
    Integer salaryMin,
    Integer salaryMax,
    LocalDateTime appliedDate
) {
    public JobApplicationRequest {
        if (salaryMin != null && salaryMax != null && salaryMin > salaryMax) {
            throw new IllegalArgumentException("Minimum salary cannot be greater than maximum salary");
        }
    }
}