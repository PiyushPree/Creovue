package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.WorkExperienceDTO;
import com.creovue.Creovue.dto.WorkExperienceResponse;
import com.creovue.Creovue.service.WorkExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-experiences")
public class WorkExperienceController {

    @Autowired
    private WorkExperienceService workExperienceService;

    @PostMapping("/{userId}")
    public ResponseEntity<WorkExperienceResponse> addWorkExperience(
            @PathVariable Long userId,
            @Valid @RequestBody WorkExperienceDTO request) {
        return ResponseEntity.ok(workExperienceService.addWorkExperience(userId, request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkExperienceResponse>> getUserWorkExperiences(
            @PathVariable Long userId) {
        return ResponseEntity.ok(workExperienceService.getUserWorkExperiences(userId));
    }
}
