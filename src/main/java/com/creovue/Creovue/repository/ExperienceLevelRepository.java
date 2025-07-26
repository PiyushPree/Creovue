package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.ExperienceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceLevelRepository extends JpaRepository<ExperienceLevel, Long> {
    Optional<ExperienceLevel> findByLevelName(String levelName);
}
