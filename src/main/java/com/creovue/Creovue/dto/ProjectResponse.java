package com.creovue.Creovue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private Double budget;
    private String createdBy; // MediaHouse name/email
    private LocalDateTime createdAt;
}
