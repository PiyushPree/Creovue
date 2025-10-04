package com.creovue.Creovue.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private Integer vacancies;
    private String link;
    private String createdByName;
    private String imageBase64;
    private boolean closed;      // ✅ add this
    private String statusLabel;  // ✅ optional: "Open" or "Closed"
}
