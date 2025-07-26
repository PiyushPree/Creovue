package com.creovue.Creovue.dto;

import com.creovue.Creovue.entity.UserType;

import java.util.List;

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private UserType userType;
    private Long experienceLevelId;
    private List<Long> roleIds;
    private List<WorkExperienceDTO> workExperiences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getExperienceLevelId() {
        return experienceLevelId;
    }

    public void setExperienceLevelId(Long experienceLevelId) {
        this.experienceLevelId = experienceLevelId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<WorkExperienceDTO> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperienceDTO> workExperiences) {
        this.workExperiences = workExperiences;
    }
}
