package com.creovue.Creovue.service;

import com.creovue.Creovue.dto.PortfolioRequest;
import com.creovue.Creovue.dto.PortfolioResponse;
import com.creovue.Creovue.entity.Portfolio;
import com.creovue.Creovue.entity.User;
import com.creovue.Creovue.repository.PortfolioRepository;
import com.creovue.Creovue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public PortfolioResponse createPortfolio(Long userId, PortfolioRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(request.getTitle());
        portfolio.setDescription(request.getDescription());
        portfolio.setLink(request.getLink());
        portfolio.setUser(user);

        Portfolio saved = portfolioRepository.save(portfolio);

        return mapToResponse(saved);
    }

    public List<PortfolioResponse> getUserPortfolios(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PortfolioResponse mapToResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .title(portfolio.getTitle())
                .description(portfolio.getDescription())
                .link(portfolio.getLink())
                .userId(portfolio.getUser().getId())
                .build();
    }

}
