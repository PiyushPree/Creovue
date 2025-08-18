package com.creovue.Creovue.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProjectApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;   // instead of Project

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;          // must be of type CREATOR

    private String status = "PENDING"; // PENDING, ACCEPTED, REJECTED

    private LocalDateTime appliedAt = LocalDateTime.now();

    public ProjectApplication() {}

    public ProjectApplication(Portfolio portfolio, User creator) {
        this.portfolio = portfolio;
        this.creator = creator;
        this.status = "PENDING";
        this.appliedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
