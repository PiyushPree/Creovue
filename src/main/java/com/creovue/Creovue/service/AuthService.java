package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.SignupRequest;
import com.creovue.Creovue.dto.WorkExperienceDTO;
import com.creovue.Creovue.entity.ExperienceLevel;
import com.creovue.Creovue.entity.RoleType;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.entity.WorkExperience;
import com.creovue.Creovue.repository.ExperienceLevelRepository;
import com.creovue.Creovue.repository.RoleTypeRepository;
import com.creovue.Creovue.repository.UserRepository;
import com.creovue.Creovue.repository.WorkExperienceRepository;
import com.creovue.Creovue.security.JwtService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleTypeRepository roleTypeRepository;

    @Autowired
    private ExperienceLevelRepository experienceLevelRepository;

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // 👤 User Registration Logic
    public User registerUser(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setUserType(request.getUserType());

        // Set Experience Level
        ExperienceLevel experienceLevel = experienceLevelRepository.findById(request.getExperienceLevelId())
                .orElseThrow(() -> new RuntimeException("Experience Level not found"));
        user.setExperienceLevel(experienceLevel);

        // Set Roles
        Set<RoleType> roles = new HashSet<>();
        for (Long roleId : request.getRoleIds()) {
            RoleType role = roleTypeRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
            roles.add(role);
        }
        user.setRoles(roles);

        // Save user to generate ID
        User savedUser = userRepository.save(user);

        // Save Work Experience
        List<WorkExperience> workExperiences = new ArrayList<>();
        for (WorkExperienceDTO weDto : request.getWorkExperiences()) {
            WorkExperience we = new WorkExperience();
            we.setTitle(weDto.getTitle());
            we.setCompany(weDto.getCompany());
            we.setDescription(weDto.getDescription());
            we.setStartDate(weDto.getStartDate());
            we.setEndDate(weDto.getEndDate());
            we.setUser(savedUser);
            workExperiences.add(workExperienceRepository.save(we));
        }

        savedUser.setWorkExperiences(workExperiences);
        userRepository.save(savedUser);

        return savedUser;
    }

    // 🪙 Login and Generate JWT
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtService.generateToken(user);
    }

    // 🌱 Seed default data
    @PostConstruct
    public void seedDefaults() {
        if (roleTypeRepository.count() == 0) {
            roleTypeRepository.saveAll(List.of(
                    new RoleType("Actor"),
                    new RoleType("Writer"),
                    new RoleType("Designer"),
                    new RoleType("Director")
            ));
        }

        if (experienceLevelRepository.count() == 0) {
            experienceLevelRepository.saveAll(List.of(
                    new ExperienceLevel("Beginner"),
                    new ExperienceLevel("Intermediate"),
                    new ExperienceLevel("Expert")
            ));
        }
    }
}
