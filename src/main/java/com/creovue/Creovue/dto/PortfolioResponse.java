package com.creovue.Creovue.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioResponse {
    private Long id;
    private String title;
    private String description;
    private String link;
    private Long userId;
}
