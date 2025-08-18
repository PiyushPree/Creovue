package com.creovue.Creovue.controller;

import com.creovue.Creovue.entity.Application;
import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.entity.User;
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

    @PreAuthorize("hasRole('CREATOR')")
    @PostMapping("/{projectId}")
    public Application applyToProject(@PathVariable Long projectId, Authentication authentication) {
        String email = authentication.getName();
        User creator = userRepository.findByEmail(email).orElseThrow();

        Project project = projectRepository.findById(projectId).orElseThrow();

        Application application = Application.builder()
                .project(project)
                .creator(creator)
                .status("PENDING")
                .build();

        return applicationRepository.save(application);
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
