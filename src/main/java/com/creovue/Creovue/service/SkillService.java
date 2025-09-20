package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.SkillRequest;
import com.creovue.Creovue.dto.SkillResponse;
import com.creovue.Creovue.entity.Skill;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.SkillRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    public SkillResponse addSkill(Long userId, SkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setUser(user);

        Skill saved = skillRepository.save(skill);

        return mapToResponse(saved);
    }

    public List<SkillResponse> getUserSkills(Long userId) {
        return skillRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SkillResponse mapToResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
