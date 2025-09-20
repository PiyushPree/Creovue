package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByUserId(Long userId);
}
