package com.creovue.Creovue.config;

import com.creovue.Creovue.entity.RoleType;
import com.creovue.Creovue.repository.RoleTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleTypeSeeder {

    @Autowired
    private RoleTypeRepository roleTypeRepository;

    @PostConstruct
    public void seedRoleTypes() {
        List<String> roles = Arrays.asList(
                "ROLE_CREATOR",
                "ROLE_MEDIA_HOUSE",
                "ROLE_PRODUCTION_HOUSE"
        );

        roles.forEach(roleName -> {
            if (!roleTypeRepository.existsByRoleName(roleName)) {
                roleTypeRepository.save(RoleType.builder().roleName(roleName).build());
            }
        });
    }
}
