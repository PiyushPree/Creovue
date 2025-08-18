package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.Portfolio;
import com.creovue.Creovue.entity.ProjectApplication;
import com.creovue.Creovue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    List<ProjectApplication> findByPortfolio(Portfolio portfolio);
    List<ProjectApplication> findByCreator(User creator);
}
