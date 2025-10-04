package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.WorkExperienceDTO;
import com.creovue.Creovue.dto.WorkExperienceResponse;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.entity.WorkExperience;
import com.creovue.Creovue.repository.UserRepository;
import com.creovue.Creovue.repository.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkExperienceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    public WorkExperienceResponse addWorkExperience(Long userId, WorkExperienceDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        WorkExperience exp = new WorkExperience();
        exp.setTitle(dto.getTitle());
        exp.setCompany(dto.getCompany());
        exp.setDescription(dto.getDescription());
        exp.setStartDate(dto.getStartDate());
        exp.setEndDate(dto.getEndDate());
        exp.setUser(user);

        WorkExperience saved = workExperienceRepository.save(exp);

        return WorkExperienceResponse.builder()
                .title(saved.getTitle())
                .company(saved.getCompany())
                .description(saved.getDescription())
                .userId(saved.getUser().getId())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .build();
    }

    public List<WorkExperienceResponse> getUserWorkExperiences(Long userId) {
        List<WorkExperience> list = workExperienceRepository.findByUserId(userId);
        return list.stream()
                .map(exp -> WorkExperienceResponse.builder()
                        .title(exp.getTitle())
                        .company(exp.getCompany())
                        .description(exp.getDescription())
                        .userId(exp.getUser().getId())
                        .startDate(exp.getStartDate())
                        .endDate(exp.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }
}
