package com.creovue.Creovue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PortfolioRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String link;
}
