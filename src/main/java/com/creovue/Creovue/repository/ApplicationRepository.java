package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.Application;
import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByProjectAndCreator(Project project, User creator);
}
