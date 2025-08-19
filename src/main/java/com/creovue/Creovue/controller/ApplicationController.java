package com.creovue.Creovue.controller;

import com.creovue.Creovue.entity.*;
import com.creovue.Creovue.repository.ApplicationRepository;
import com.creovue.Creovue.repository.ProjectRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATOR applies to a project
    @PreAuthorize("hasRole('CREATOR')")
    @PostMapping("/{projectId}")
    public Application applyToProject(@PathVariable Long projectId, Authentication authentication) {
        String email = authentication.getName();
        User creator = userRepository.findByEmail(email).orElseThrow();

        Project project = projectRepository.findById(projectId).orElseThrow();

        // prevent duplicate applications
        applicationRepository.findByProjectAndCreator(project, creator).ifPresent(app -> {
            throw new RuntimeException("Already applied to this project");
        });

        Application application = Application.builder()
                .project(project)
                .creator(creator)
                .status(ApplicationStatus.PENDING)
                .build();

        return applicationRepository.save(application);
    }

    // MEDIA_HOUSE can view applicants of its project
    @PreAuthorize("hasRole('MEDIA_HOUSE')")
    @GetMapping("/project/{projectId}")
    public List<Application> getApplicationsForProject(@PathVariable Long projectId, Authentication authentication) {
        String email = authentication.getName();
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();

        Project project = projectRepository.findById(projectId).orElseThrow();

        if (!project.getCreatedBy().getId().equals(mediaHouse.getId())) {
            throw new RuntimeException("You are not the owner of this project");
        }

        return applicationRepository.findAll()
                .stream()
                .filter(app -> app.getProject().getId().equals(projectId))
                .toList();
    }

    // MEDIA_HOUSE accepts an application
    @PreAuthorize("hasRole('MEDIA_HOUSE')")
    @PutMapping("/{applicationId}/accept")
    public Application acceptApplication(@PathVariable Long applicationId, Authentication authentication) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();

        String email = authentication.getName();
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();

        if (!application.getProject().getCreatedBy().getId().equals(mediaHouse.getId())) {
            throw new RuntimeException("You are not authorized to accept this application");
        }

        application.setStatus(ApplicationStatus.ACCEPTED);
        return applicationRepository.save(application);
    }

    // MEDIA_HOUSE rejects an application
    @PreAuthorize("hasRole('MEDIA_HOUSE')")
    @PutMapping("/{applicationId}/reject")
    public Application rejectApplication(@PathVariable Long applicationId, Authentication authentication) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();

        String email = authentication.getName();
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();

        if (!application.getProject().getCreatedBy().getId().equals(mediaHouse.getId())) {
            throw new RuntimeException("You are not authorized to reject this application");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    // PRODUCTION_HOUSE can view all applications (read-only)
    @PreAuthorize("hasRole('PRODUCTION_HOUSE')")
    @GetMapping("/all")
    public List<Application> getAllApplicationsForProductionHouse() {
        return applicationRepository.findAll();
    }
}
