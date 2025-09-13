package com.creovue.Creovue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private String status; // PENDING / ACCEPTED / REJECTED
    private Long projectId;
    private String projectTitle;
    private String creatorName;
    private String creatorEmail;
    private LocalDateTime appliedAt;
}
