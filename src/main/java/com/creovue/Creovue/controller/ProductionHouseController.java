package com.creovue.Creovue.controller;

import com.creovue.Creovue.entity.Application;
import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.repository.ApplicationRepository;
import com.creovue.Creovue.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-house")
public class ProductionHouseController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @PreAuthorize("hasRole('PRODUCTION_HOUSE')")
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @PreAuthorize("hasRole('PRODUCTION_HOUSE')")
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
