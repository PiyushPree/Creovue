package com.creovue.Creovue.repository;

import com.creovue.Creovue.entity.Portfolio;
import com.creovue.Creovue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
}
