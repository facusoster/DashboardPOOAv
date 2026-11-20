package com.dashboard.controller.web;

import com.dashboard.dto.DashboardHealthDTO;
import com.dashboard.service.DashboardHealthService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardHealthService dashboardHealthService;

    @GetMapping("/")
    public String dashboard(Model model) {

        DashboardHealthDTO dashboard
                = dashboardHealthService
                        .buildDashboard();

        model.addAttribute(
                "dashboardHealth",
                dashboard);

        return "dashboard";
    }
}
