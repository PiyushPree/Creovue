package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
    boolean existsByRoleName(String roleName);
    Optional<RoleType> findByRoleName(String roleName);
}
