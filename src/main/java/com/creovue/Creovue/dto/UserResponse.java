package com.creovue.Creovue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String userType;
    private String experienceLevel;
    private List<String> roles;
    private List<WorkExperienceResponse> workExperiences;
    private LocalDateTime createdAt;
}
