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

        long executions = executionService.count();
        long ok = executionService.countOk();
        long warning = executionService.countWarning();
        long error = executionService.countError();

        double successRate = executions == 0
                ? 0
                : ((double) ok / executions) * 100;

        model.addAttribute(
                "applicationsCount",
                applicationService.count());

        model.addAttribute(
                "executionsCount",
                executions);

        model.addAttribute(
                "okCount",
                ok);

        model.addAttribute(
                "warningCount",
                warning);

        model.addAttribute(
                "errorCount",
                error);

        model.addAttribute(
                "successRate",
                Math.round(successRate));

        return "dashboard";
    }
}