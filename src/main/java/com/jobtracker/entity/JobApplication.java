package com.jobtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String companyName;
    
    @NotBlank(message = "Job title is required")
    @Column(nullable = false)
    private String jobTitle;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;
    
    @Column(length = 2000)
    private String description;
    
    @Column(length = 1000)
    private String notes;
    
    private String location;
    
    private String jobUrl;
    
    private Integer salaryMin;
    
    private Integer salaryMax;
    
    private LocalDateTime appliedDate;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public JobApplication() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public ApplicationStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getJobUrl() {
        return jobUrl;
    }
    
    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }
    
    public Integer getSalaryMin() {
        return salaryMin;
    }
    
    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }
    
    public Integer getSalaryMax() {
        return salaryMax;
    }
    
    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }
    
    public LocalDateTime getAppliedDate() {
        return appliedDate;
    }
    
    public void setAppliedDate(LocalDateTime appliedDate) {
        this.appliedDate = appliedDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Enum
    public enum ApplicationStatus {
        RESEARCHING,
        APPLIED,
        PHONE_SCREEN,
        TECHNICAL_INTERVIEW,
        ONSITE_INTERVIEW,
        OFFER,
        REJECTED,
        WITHDRAWN,
        ACCEPTED
    }
    
    // Business logic methods
    public boolean isActive() {
        return status != ApplicationStatus.REJECTED 
            && status != ApplicationStatus.WITHDRAWN 
            && status != ApplicationStatus.ACCEPTED;
    }
    
    public boolean isInInterviewProcess() {
        return status == ApplicationStatus.PHONE_SCREEN
            || status == ApplicationStatus.TECHNICAL_INTERVIEW
            || status == ApplicationStatus.ONSITE_INTERVIEW;
    }
}