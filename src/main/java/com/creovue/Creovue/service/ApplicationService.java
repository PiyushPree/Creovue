package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.ApplicationRequest;
import com.creovue.Creovue.dto.ApplicationResponse;
import com.creovue.Creovue.entity.*;
import com.creovue.Creovue.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ApplicationResponse applyToProject(ApplicationRequest request, String email) {
        User creator = userRepository.findByEmail(email).orElseThrow();
        Project project = projectRepository.findById(request.getProjectId()).orElseThrow();

        // Prevent duplicate application
        applicationRepository.findByProjectAndCreator(project, creator).ifPresent(a -> {
            throw new RuntimeException("Already applied to this project");
        });

        Application application = Application.builder()
                .project(project)
                .creator(creator)
                .status(ApplicationStatus.PENDING)
                .build();

        return mapToResponse(applicationRepository.save(application));
    }

    public List<ApplicationResponse> getApplicationsForProject(Long projectId, String email) {
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();

        if (!project.getCreatedBy().getId().equals(mediaHouse.getId())) {
            throw new RuntimeException("You are not the owner of this project");
        }

        return applicationRepository.findAll().stream()
                .filter(app -> app.getProject().getId().equals(projectId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApplicationResponse updateStatus(Long applicationId, String email, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();

        if (!application.getProject().getCreatedBy().getEmail().equals(email)) {
            throw new RuntimeException("You are not authorized to update this application");
        }

        application.setStatus(status);
        return mapToResponse(applicationRepository.save(application));
    }

    private ApplicationResponse mapToResponse(Application app) {
        return ApplicationResponse.builder()
                .id(app.getId())
                .status(app.getStatus().name())
                .projectId(app.getProject().getId())
                .projectTitle(app.getProject().getTitle())
                .creatorName(app.getCreator().getName())
                .creatorEmail(app.getCreator().getEmail())
                .appliedAt(app.getCreatedDate()) // 👈 if you add auditing
                .build();
    }

}
