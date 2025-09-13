package com.creovue.Creovue.dto;

import com.creovue.Creovue.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SignupRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private UserType userType;  // CREATOR, MEDIA_HOUSE, PRODUCTION_HOUSE

    private Long experienceLevelId;

    private List<Long> roleIds;

    private List<WorkExperienceDTO> workExperiences;
}
