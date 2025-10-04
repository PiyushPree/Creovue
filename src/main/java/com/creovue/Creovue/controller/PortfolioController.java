package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.PortfolioRequest;
import com.creovue.Creovue.dto.PortfolioResponse;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.PortfolioRepository;
import com.creovue.Creovue.repository.UserRepository;
import com.creovue.Creovue.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @PathVariable Long userId,
            @Valid @RequestBody PortfolioRequest request) {
        return ResponseEntity.ok(portfolioService.createPortfolio(userId, request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PortfolioResponse>> getUserPortfolios(@PathVariable Long userId) {
        return ResponseEntity.ok(portfolioService.getUserPortfolios(userId));
    }

    @PreAuthorize("hasRole('MEDIA_HOUSE')")
    @GetMapping("/user/{userId}/view")
    public ResponseEntity<List<PortfolioResponse>> getPortfolioByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(portfolioService.getUserPortfolios(userId));
    }
}
