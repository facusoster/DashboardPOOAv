package com.dashboard.controller.web;

import com.dashboard.model.Application;
import com.dashboard.service.ApplicationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "applications",
                service.findAll());

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

        Application application = service.findById(id);

        model.addAttribute("monitoredApplication", application);

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
