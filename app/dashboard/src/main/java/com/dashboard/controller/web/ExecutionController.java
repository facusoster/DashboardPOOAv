package com.dashboard.controller.web;

import com.dashboard.service.ExecutionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/executions")
@RequiredArgsConstructor
public class ExecutionController {

    private final ExecutionService executionService;

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "executions",
                executionService.findAll());

        return "executions/list";
    }
}