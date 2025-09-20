package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.ProjectRequest;
import com.creovue.Creovue.dto.ProjectResponse;
import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.ProjectRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponse createProject(ProjectRequest request) {
        User creator = userRepository.findById(request.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // only allow Media/Production house
        switch (creator.getUserType()) {
            case MEDIA_HOUSE, PRODUCTION_HOUSE -> {}
            default -> throw new RuntimeException("Only Media/Production House can create projects");
        }

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setCreatedBy(creator);

        Project saved = projectRepository.save(project);

        return mapToResponse(saved);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> getProjectsByUser(Long userId) {
        return projectRepository.findByCreatedById(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .createdByName(project.getCreatedBy().getName())
                .build();
    }
}
