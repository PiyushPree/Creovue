package com.creovue.Creovue.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String link;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
