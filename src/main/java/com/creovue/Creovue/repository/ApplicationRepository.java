package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.Application;
import com.creovue.Creovue.entity.Project;
import com.creovue.Creovue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByProjectAndCreator(Project project, User creator);
    List<Application> findByProject(Project project);
    List<Application> findByProjectId(Long projectId);
    // Fetch project and creator eagerly for production house
    @Query("SELECT a FROM Application a JOIN FETCH a.project JOIN FETCH a.creator")
    List<Application> findAllWithProjectAndCreator();
}
