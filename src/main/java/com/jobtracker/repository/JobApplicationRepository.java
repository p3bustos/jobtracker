package com.jobtracker.repository;

import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.JobApplication.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    
    List<JobApplication> findByStatus(ApplicationStatus status);
    
    List<JobApplication> findByCompanyNameContainingIgnoreCase(String companyName);
    
    @Query("SELECT ja FROM JobApplication ja WHERE ja.status NOT IN " +
           "('REJECTED', 'WITHDRAWN', 'ACCEPTED') ORDER BY ja.updatedAt DESC")
    List<JobApplication> findActiveApplications();
    
    @Query("SELECT ja FROM JobApplication ja WHERE ja.status IN " +
           "('PHONE_SCREEN', 'TECHNICAL_INTERVIEW', 'ONSITE_INTERVIEW') " +
           "ORDER BY ja.updatedAt DESC")
    List<JobApplication> findApplicationsInInterview();
    
    List<JobApplication> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    long countByStatus(ApplicationStatus status);
    
    List<JobApplication> findTop10ByOrderByUpdatedAtDesc();
}