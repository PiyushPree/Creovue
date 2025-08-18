package com.creovue.Creovue.controller;

import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.ProjectRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('MEDIA_HOUSE')")
    @PostMapping
    public Project createProject(@RequestBody Project project, Authentication authentication) {
        String email = authentication.getName();
        User mediaHouse = userRepository.findByEmail(email).orElseThrow();
        project.setCreatedBy(mediaHouse);
        return projectRepository.save(project);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
