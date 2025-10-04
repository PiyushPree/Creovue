package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.ProjectRequest;
import com.creovue.Creovue.dto.ProjectResponse;
import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.ProjectRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Create project
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        switch (creator.getUserType()) {
            case MEDIA_HOUSE, PRODUCTION_HOUSE -> {}
            default -> throw new RuntimeException("Only Media/Production House can create projects");
        }

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setVacancies(request.getVacancies());
        project.setLink(request.getLink());
        project.setCreatedBy(creator);

        // Handle image upload
        MultipartFile imageFile = request.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                project.setImage(imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image file", e);
            }
        }

        Project saved = projectRepository.save(project);
        return mapToResponse(saved);
    }

    // Get all projects
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get projects by user
    public List<ProjectResponse> getProjectsByUser(Long userId) {
        return projectRepository.findByCreatedById(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get project by ID
    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapToResponse(project);
    }

    public ProjectResponse updateProject(Long projectId, ProjectRequest request, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // ✅ Only the creator (Media/Production House) can edit
        if (!project.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to edit this project");
        }

        // update fields
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setVacancies(request.getVacancies());
        project.setLink(request.getLink());

        MultipartFile imageFile = request.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                project.setImage(imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image file", e);
            }
        }

        Project saved = projectRepository.save(project);
        return mapToResponse(saved);
    }

    public void deleteProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // ✅ Only the creator (Media/Production House) can delete
        if (!project.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this project");
        }

        projectRepository.delete(project);
    }

    public ProjectResponse toggleProjectClosed(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to close/reopen this project");
        }

        project.setClosed(!project.isClosed()); // flip state
        Project saved = projectRepository.save(project);

        return mapToResponse(saved);
    }


    private ProjectResponse mapToResponse(Project project) {
        String imageBase64 = null;
        if (project.getImage() != null && project.getImage().length > 0) {
            imageBase64 = java.util.Base64.getEncoder().encodeToString(project.getImage());
        }

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .vacancies(project.getVacancies())
                .link(project.getLink())
                .createdByName(project.getCreatedBy().getName())
                .imageBase64(imageBase64)
                .closed(project.isClosed()) // ✅ add
                .statusLabel(project.isClosed() ? "Closed" : "Open") // ✅ add
                .build();
    }

}
