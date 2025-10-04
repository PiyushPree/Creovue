package com.creovue.Creovue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectRequest {
    @NotBlank
    private String title;

    private String description;
    private Integer vacancies;
    private String link;

    private MultipartFile image;
//    @NotNull
//    private Long createdByUserId;
}
