package com.dashboard.controller.web;

import com.dashboard.dto.ApplicationHealthDTO;
import com.dashboard.model.Application;
import com.dashboard.service.ApplicationHealthService;
import com.dashboard.service.ApplicationService;
import com.dashboard.service.ExecutionResultService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    private final ExecutionResultService executionResultService;

    private final ApplicationHealthService applicationHealthService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable
                = PageRequest.of(page, size);

        Page<Application> applications
                = service.findFiltered(
                        search,
                        activo,
                        pageable);

        model.addAttribute(
                "applications",
                applications.getContent());

        model.addAttribute(
                "currentPage",
                page);

        model.addAttribute(
                "totalPages",
                applications.getTotalPages());

        model.addAttribute(
                "pageSize",
                size);

        model.addAttribute(
                "search",
                search);

        model.addAttribute(
                "activo",
                activo);

        return "applications/list";
    }

    @GetMapping("/new")
    public String create(Model model) {

        model.addAttribute(
                "monitoredApplication",
                new Application());

        return "applications/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "monitoredApplication",
                service.findById(id));

        return "applications/form";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model) {

        Application application
                = service.findById(id);

        ApplicationHealthDTO health
                = applicationHealthService
                        .buildHealth(id);

        model.addAttribute(
                "monitoredApplication",
                application);

        model.addAttribute(
                "applicationHealth",
                health);

        model.addAttribute(
                "executionResults",
                executionResultService
                        .findByApplicationId(
                                application.getId()));

        return "applications/detail";
    }

    @PostMapping("/deactivate/{id}")
    public String deactivate(
            @PathVariable Long id) {

        service.deactivate(id);

        return "redirect:/applications";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute Application application) {

        service.save(application);

        return "redirect:/applications";
    }
}
