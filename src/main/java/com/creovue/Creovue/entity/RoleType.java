package com.creovue.Creovue.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName; // e.g. "Actor", "Writer", "Designer"

    public RoleType(String roleName) {
        this.roleName = roleName;
    }
}
