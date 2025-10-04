package com.creovue.Creovue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class WorkExperienceResponse {
    private String title;
    private String company;
    private String description;
    private Long userId;
    private LocalDate startDate;   // <-- LocalDate instead of String
    private LocalDate endDate;
}
