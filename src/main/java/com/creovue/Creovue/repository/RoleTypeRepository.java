package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
    RoleType findByRoleName(String roleName);
}
