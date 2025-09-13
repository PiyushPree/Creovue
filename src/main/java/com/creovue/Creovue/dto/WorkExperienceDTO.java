package com.creovue.Creovue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company is required")
    private String company;

    private String description;

    private LocalDate startDate;
    private LocalDate endDate;
}
