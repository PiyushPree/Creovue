package com.creovue.Creovue.mapper;

import com.creovue.Creovue.dto.UserResponse;
import com.creovue.Creovue.dto.WorkExperienceResponse;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.entity.WorkExperience;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userType(user.getUserType().name())
                .experienceLevel(user.getExperienceLevel().getLevelName())
                .roles(user.getRoles().stream()
                        .map(r -> r.getRoleName())
                        .collect(Collectors.toList()))
                .workExperiences(user.getWorkExperiences().stream()
                        .map(UserMapper::mapWorkExperience)
                        .collect(Collectors.toList()))
                .createdAt(user.getCreatedAt())
                .build();
    }

    private static WorkExperienceResponse mapWorkExperience(WorkExperience we) {
        return WorkExperienceResponse.builder()
                .title(we.getTitle())
                .company(we.getCompany())
                .description(we.getDescription())
                .startDate(we.getStartDate())
                .endDate(we.getEndDate())
                .build();
    }
}
