package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findAllByUserId(Long userId);
    List<WorkExperience> findByUserId(Long userId);

}
