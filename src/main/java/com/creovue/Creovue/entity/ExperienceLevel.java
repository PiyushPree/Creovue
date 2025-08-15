package com.creovue.Creovue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExperienceLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String levelName;

    public ExperienceLevel(Long id, String levelName){
        this.id = id;
        this.levelName = levelName;
    }

    public ExperienceLevel() {

    }

    public ExperienceLevel(String beginner) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "ExperienceLevel{" +
                "id=" + id +
                ", levelName='" + levelName + '\'' +
                '}';
    }
}
