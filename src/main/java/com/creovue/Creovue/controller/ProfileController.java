package com.creovue.Creovue.controller;

import com.creovue.Creovue.dto.PortfolioRequest;
import com.creovue.Creovue.dto.SkillRequest;
import com.creovue.Creovue.entity.Portfolio;
import com.creovue.Creovue.entity.Skill;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.PortfolioRepository;
import com.creovue.Creovue.repository.SkillRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final PortfolioRepository portfolioRepository;

    public ProfileController(UserRepository userRepository,
                             SkillRepository skillRepository,
                             PortfolioRepository portfolioRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.portfolioRepository = portfolioRepository;
    }

    // --- existing endpoint ---
    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    // --- add new skill ---
    @PostMapping("/skills")
    public ResponseEntity<?> addSkill(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestBody SkillRequest skillRequest) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = new Skill();
        skill.setName(skillRequest.getName());
        skill.setUser(user);

        return ResponseEntity.ok(skillRepository.save(skill));
    }

    // --- add new portfolio ---
    @PostMapping("/portfolios")
    public ResponseEntity<?> addPortfolio(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody PortfolioRequest portfolioRequest) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(portfolioRequest.getTitle());
        portfolio.setDescription(portfolioRequest.getDescription());
        portfolio.setLink(portfolioRequest.getLink());
        portfolio.setUser(user);

        return ResponseEntity.ok(portfolioRepository.save(portfolio));
    }

    @GetMapping("/portfolios/my")
    public ResponseEntity<?> getMyPortfolios(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(portfolioRepository.findByUser(user));
    }
}
