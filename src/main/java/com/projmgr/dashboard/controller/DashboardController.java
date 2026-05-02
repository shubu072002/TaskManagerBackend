package com.projmgr.dashboard.controller;

import com.projmgr.dashboard.dto.DashboardResponse;
import com.projmgr.dashboard.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse mine(Authentication authentication) {
        String email = authentication.getName();
        return dashboardService.forUser(email);
    }
}
