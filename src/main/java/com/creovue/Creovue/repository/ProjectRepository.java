package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByCreatedById(Long userId);

}
