package com.creovue.Creovue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long createdByUserId;  // 👈 Add this
}
