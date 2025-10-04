package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.ProjectRequest;
import com.creovue.Creovue.dto.ProjectResponse;
import com.creovue.Creovue.service.ProjectService;
import com.creovue.Creovue.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProjectResponse> createProject(@ModelAttribute @Valid ProjectRequest requestDto,
                                                         @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Long userId = Long.valueOf(jwtService.extractId(token)); // ✅ use JwtService

        ProjectResponse created = projectService.createProject(requestDto, userId);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(projectService.getProjectsByUser(userId));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PutMapping(value = "/{projectId}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @ModelAttribute ProjectRequest request,
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        Long userId = Long.valueOf(jwtService.extractId(token));

        return ResponseEntity.ok(projectService.updateProject(projectId, request, userId));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        Long userId = Long.valueOf(jwtService.extractId(token));

        projectService.deleteProject(projectId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/close")
    public ResponseEntity<ProjectResponse> toggleClose(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        Long userId = Long.valueOf(jwtService.extractId(token));

        ProjectResponse updated = projectService.toggleProjectClosed(projectId, userId);
        return ResponseEntity.ok(updated);
    }

}
