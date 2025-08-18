package com.creovue.Creovue.controller;

import com.creovue.Creovue.entity.Portfolio;
import com.creovue.Creovue.entity.ProjectApplication;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.PortfolioRepository;
import com.creovue.Creovue.repository.ProjectApplicationRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ProjectApplicationController {
    private final ProjectApplicationRepository applicationRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public ProjectApplicationController(ProjectApplicationRepository applicationRepository,
                                        PortfolioRepository portfolioRepository,
                                        UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    // ✅ Creator applies to Portfolio
    @PostMapping("/{portfolioId}/apply")
    public ResponseEntity<?> applyToPortfolio(@PathVariable Long portfolioId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        User creator = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        ProjectApplication application = new ProjectApplication(portfolio, creator);
        return ResponseEntity.ok(applicationRepository.save(application));
    }

    // ✅ Media House sees all applications for their Portfolio
    @GetMapping("/{portfolioId}")
    public ResponseEntity<?> getApplications(@PathVariable Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return ResponseEntity.ok(applicationRepository.findByPortfolio(portfolio));
    }

    // ✅ Media House updates application status
    @PutMapping("/{applicationId}")
    public ResponseEntity<?> updateApplication(@PathVariable Long applicationId,
                                               @RequestParam String status) {
        ProjectApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status.toUpperCase());
        return ResponseEntity.ok(applicationRepository.save(application));
    }
}
