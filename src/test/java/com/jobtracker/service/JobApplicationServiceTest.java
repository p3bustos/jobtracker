package com.jobtracker.service;

import com.jobtracker.dto.JobApplicationRequest;
import com.jobtracker.dto.JobApplicationResponse;
import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.JobApplication.ApplicationStatus;
import com.jobtracker.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for JobApplicationService.
 * Demonstrates testing best practices: mocking, assertions, test coverage.
 */
@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {
    
    @Mock
    private JobApplicationRepository repository;
    
    @InjectMocks
    private JobApplicationService service;
    
    private JobApplication testApplication;
    private JobApplicationRequest testRequest;
    
    @BeforeEach
    void setUp() {
        // Setup test data
        testApplication = new JobApplication();
        testApplication.setId(1L);
        testApplication.setCompanyName("TestCorp");
        testApplication.setJobTitle("Software Engineer");
        testApplication.setStatus(ApplicationStatus.APPLIED);
        testApplication.setLocation("Remote");
        testApplication.setSalaryMin(150000);
        testApplication.setSalaryMax(180000);
        testApplication.setCreatedAt(LocalDateTime.now());
        testApplication.setUpdatedAt(LocalDateTime.now());
        
        testRequest = new JobApplicationRequest(
            "TestCorp",
            "Software Engineer",
            ApplicationStatus.APPLIED,
            "Job description",
            "Some notes",
            "Remote",
            "http://example.com/job",
            150000,
            180000,
            LocalDateTime.now()
        );
    }
    
    @Test
    void createApplication_ShouldReturnSavedApplication() {
        // Arrange
        when(repository.save(any(JobApplication.class))).thenReturn(testApplication);
        
        // Act
        JobApplicationResponse response = service.createApplication(testRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals("TestCorp", response.companyName());
        assertEquals("Software Engineer", response.jobTitle());
        assertEquals(ApplicationStatus.APPLIED, response.status());
        verify(repository, times(1)).save(any(JobApplication.class));
    }
    
    @Test
    void updateApplication_WhenExists_ShouldReturnUpdatedApplication() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(testApplication));
        when(repository.save(any(JobApplication.class))).thenReturn(testApplication);
        
        JobApplicationRequest updateRequest = new JobApplicationRequest(
            "UpdatedCorp",
            "Senior Engineer",
            ApplicationStatus.TECHNICAL_INTERVIEW,
            null, null, null, null, null, null, null
        );
        
        // Act
        JobApplicationResponse response = service.updateApplication(1L, updateRequest);
        
        // Assert
        assertNotNull(response);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(JobApplication.class));
    }
    
    @Test
    void updateApplication_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.updateApplication(999L, testRequest);
        });
        
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any(JobApplication.class));
    }
    
    @Test
    void getApplicationById_WhenExists_ShouldReturnApplication() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(testApplication));
        
        // Act
        JobApplicationResponse response = service.getApplicationById(1L);
        
        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("TestCorp", response.companyName());
        verify(repository, times(1)).findById(1L);
    }
    
    @Test
    void getApplicationById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.getApplicationById(999L)
        );
        
        assertTrue(exception.getMessage().contains("999"));
        verify(repository, times(1)).findById(999L);
    }
    
    @Test
    void getAllApplications_ShouldReturnAllApplications() {
        // Arrange
        JobApplication app2 = new JobApplication();
        app2.setId(2L);
        app2.setCompanyName("AnotherCorp");
        app2.setJobTitle("DevOps Engineer");
        app2.setStatus(ApplicationStatus.PHONE_SCREEN);
        
        when(repository.findAll()).thenReturn(Arrays.asList(testApplication, app2));
        
        // Act
        List<JobApplicationResponse> responses = service.getAllApplications();
        
        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(repository, times(1)).findAll();
    }
    
    @Test
    void getApplicationsByStatus_ShouldReturnFilteredApplications() {
        // Arrange
        when(repository.findByStatus(ApplicationStatus.APPLIED))
            .thenReturn(Arrays.asList(testApplication));
        
        // Act
        List<JobApplicationResponse> responses = 
            service.getApplicationsByStatus(ApplicationStatus.APPLIED);
        
        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(ApplicationStatus.APPLIED, responses.get(0).status());
        verify(repository, times(1)).findByStatus(ApplicationStatus.APPLIED);
    }
    
    @Test
    void getActiveApplications_ShouldReturnOnlyActiveApplications() {
        // Arrange
        when(repository.findActiveApplications())
            .thenReturn(Arrays.asList(testApplication));
        
        // Act
        List<JobApplicationResponse> responses = service.getActiveApplications();
        
        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertTrue(responses.get(0).active());
        verify(repository, times(1)).findActiveApplications();
    }
    
    @Test
    void deleteApplication_WhenExists_ShouldDeleteSuccessfully() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        
        // Act
        service.deleteApplication(1L);
        
        // Assert
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
    
    @Test
    void deleteApplication_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repository.existsById(999L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteApplication(999L);
        });
        
        verify(repository, times(1)).existsById(999L);
        verify(repository, never()).deleteById(any());
    }
    
    @Test
    void getStatistics_ShouldReturnCorrectStats() {
        // Arrange
        when(repository.count()).thenReturn(10L);
        when(repository.findActiveApplications()).thenReturn(Arrays.asList(
            testApplication, testApplication, testApplication
        ));
        when(repository.findApplicationsInInterview()).thenReturn(Arrays.asList(
            testApplication, testApplication
        ));
        when(repository.countByStatus(ApplicationStatus.REJECTED)).thenReturn(3L);
        when(repository.countByStatus(ApplicationStatus.ACCEPTED)).thenReturn(1L);
        
        // Act
        JobApplicationService.ApplicationStats stats = service.getStatistics();
        
        // Assert
        assertNotNull(stats);
        assertEquals(10L, stats.total());
        assertEquals(3L, stats.active());
        assertEquals(2L, stats.inInterview());
        assertEquals(3L, stats.rejected());
        assertEquals(1L, stats.accepted());
    }
}