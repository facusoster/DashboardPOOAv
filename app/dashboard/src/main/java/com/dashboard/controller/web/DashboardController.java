package com.dashboard.controller.web;

import com.dashboard.dto.DashboardHealthDTO;
import com.dashboard.service.DashboardHealthService;
import com.dashboard.service.ExecutionResultService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardHealthService dashboardHealthService;

    private final ExecutionResultService executionResultService;

    @GetMapping("/")
    public String dashboard(Model model) {

        DashboardHealthDTO dashboard
                = dashboardHealthService
                        .buildDashboard();

        model.addAttribute(
                "dashboardHealth",
                dashboard);

        model.addAttribute(
                "topBestPerformance",
                executionResultService
                        .getTop5BestPerformance());

        model.addAttribute(
                "topWorstPerformance",
                executionResultService
                        .getTop5WorstPerformance());

        model.addAttribute(
                "topOk",
                executionResultService
                        .getTop5Ok());

        model.addAttribute(
                "topWarning",
                executionResultService
                        .getTop5Warning());

        model.addAttribute(
                "topError",
                executionResultService
                        .getTop5Error());

        model.addAttribute(
                "topDegraded",
                executionResultService
                        .getTop5Degraded());

        return "dashboard";
    }
}
