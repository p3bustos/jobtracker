package com.jobtracker.dto;

import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.JobApplication.ApplicationStatus;

import java.time.LocalDateTime;

public record JobApplicationResponse(
    Long id,
    String companyName,
    String jobTitle,
    ApplicationStatus status,
    String description,
    String notes,
    String location,
    String jobUrl,
    Integer salaryMin,
    Integer salaryMax,
    LocalDateTime appliedDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    boolean active,
    boolean inInterviewProcess
) {
    public static JobApplicationResponse fromEntity(JobApplication entity) {
        return new JobApplicationResponse(
            entity.getId(),
            entity.getCompanyName(),
            entity.getJobTitle(),
            entity.getStatus(),
            entity.getDescription(),
            entity.getNotes(),
            entity.getLocation(),
            entity.getJobUrl(),
            entity.getSalaryMin(),
            entity.getSalaryMax(),
            entity.getAppliedDate(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.isActive(),
            entity.isInInterviewProcess()
        );
    }
}