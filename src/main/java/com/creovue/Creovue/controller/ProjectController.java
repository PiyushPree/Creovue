package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.ProjectRequest;
import com.creovue.Creovue.dto.ProjectResponse;
import com.creovue.Creovue.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(projectService.getProjectsByUser(userId));
    }
}
