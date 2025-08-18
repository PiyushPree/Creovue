package com.creovue.Creovue.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;       // e.g., Short Film, Ad Campaign
    private String description; // Details about the project
    private String link;        // URL to demo reel, video, images, etc.

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Portfolio() {}

    public Portfolio(Long id, String title, String description, String link, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
