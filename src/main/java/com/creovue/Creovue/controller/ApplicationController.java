package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.ApplicationResponse;
import com.creovue.Creovue.entity.*;
import com.creovue.Creovue.repository.ApplicationRepository;
import com.creovue.Creovue.repository.ProjectRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ApplicationResponse applyToProject(@PathVariable Long projectId, Authentication authentication) {
        String email = authentication.getName();
        User creator = userRepository.findByEmail(email).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();

        applicationRepository.findByProjectAndCreator(project, creator).ifPresent(app -> {
            throw new RuntimeException("Already applied to this project");
        });

        Application application = Application.builder()
                .project(project)
                .creator(creator)
                .status(ApplicationStatus.PENDING)
                .build();

        return mapToResponse(applicationRepository.save(application));
    }

    // MEDIA_HOUSE can view applicants of its project
//    @PreAuthorize("hasRole('MEDIA_HOUSE')")
//    @GetMapping("/project/{projectId}")
//    public List<ApplicationResponse> getApplicationsForProject(@PathVariable Long projectId, Authentication authentication) {
//        String email = authentication.getName();
//        User mediaHouse = userRepository.findByEmail(email).orElseThrow();
//        Project project = projectRepository.findById(projectId).orElseThrow();
//
//        if (!project.getCreatedBy().getId().equals(mediaHouse.getId())) {
//            throw new RuntimeException("You are not the owner of this project");
//        }
//
//        return applicationRepository.findAll().stream()
//                .filter(app -> app.getProject().getId().equals(projectId))
//                .map(this::mapToResponse)
//                .toList();
//    }

    @GetMapping("/project/{projectId}")
    public List<ApplicationResponse> getApplicationsForProject(
            @PathVariable Long projectId,
            Authentication authentication) {

        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        // ✅ If user is MEDIA_HOUSE → must be the owner
        if (currentUser.getUserType() == UserType.MEDIA_HOUSE) {
            if (!project.getCreatedBy().getId().equals(currentUser.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this project");
            }
        }

        // ✅ If user is PRODUCTION_HOUSE → allow viewing all
        if (currentUser.getUserType() == UserType.PRODUCTION_HOUSE) {
            // no ownership check
        }

        // ✅ Return applications for this project only
        return applicationRepository.findByProjectId(projectId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // MEDIA_HOUSE accepts an application
    @PutMapping("/{applicationId}/accept")
    public ApplicationResponse acceptApplication(@PathVariable Long applicationId, Authentication authentication) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        String email = authentication.getName();
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();

        if (!application.getProject().getCreatedBy().getId().equals(mediaHouse.getId())) {
            throw new RuntimeException("You are not authorized to accept this application");
        }

        application.setStatus(ApplicationStatus.ACCEPTED);
        return mapToResponse(applicationRepository.save(application));
    }

    @PutMapping("/{applicationId}/reject")
    public ApplicationResponse rejectApplication(@PathVariable Long applicationId, Authentication authentication) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        String email = authentication.getName();
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();

        if (!application.getProject().getCreatedBy().getId().equals(mediaHouse.getId())) {
            throw new RuntimeException("You are not authorized to reject this application");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        return mapToResponse(applicationRepository.save(application));
    }

    // PRODUCTION_HOUSE can view all applications (read-only)
    @PreAuthorize("hasRole('PRODUCTION_HOUSE')")
    @GetMapping("/all")
    public List<ApplicationResponse> getAllApplicationsForProductionHouse() {
        // Use repository method that fetches project and creator eagerly
        return applicationRepository.findAllWithProjectAndCreator()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // mapper
    private ApplicationResponse mapToResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .status(application.getStatus().name())
                .projectId(application.getProject().getId())
                .projectTitle(application.getProject().getTitle())
                .creatorName(application.getCreator().getName())
                .creatorEmail(application.getCreator().getEmail())
                .creatorId(application.getCreator().getId())
                .appliedAt(application.getCreatedDate()) // assuming auditing enabled
                .build();
    }
}
