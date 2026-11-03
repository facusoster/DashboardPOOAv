package com.dashboard.controller.web;

import com.dashboard.service.ApplicationService;
import com.dashboard.service.ExecutionService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ApplicationService applicationService;
    private final ExecutionService executionService;
    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute(
                "applicationsCount",
                applicationService.count());

        model.addAttribute(
                "executionsCount",
                executionService.count());

        return "dashboard";
    }
}