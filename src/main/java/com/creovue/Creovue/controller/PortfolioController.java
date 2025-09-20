package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.PortfolioRequest;
import com.creovue.Creovue.dto.PortfolioResponse;
import com.creovue.Creovue.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

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
}
