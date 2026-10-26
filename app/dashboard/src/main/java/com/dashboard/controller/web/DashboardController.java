package com.dashboard.controller.web;

import com.dashboard.service.ApplicationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ApplicationService applicationService;

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute(
                "applicationsCount",
                applicationService.count());

        return "dashboard";
    }
}