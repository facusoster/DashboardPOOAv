package com.dashboard.controller.web;

import com.dashboard.model.Execution;
import com.dashboard.service.ExecutionService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/executions")
@RequiredArgsConstructor
public class ExecutionController {

    private final ExecutionService executionService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Execution> executions =
                executionService.findAll(pageable);

        model.addAttribute(
                "executions",
                executions.getContent());

        model.addAttribute(
                "currentPage",
                page);

        model.addAttribute(
                "totalPages",
                executions.getTotalPages());

        model.addAttribute(
                "pageSize",
                size);

        return "executions/list";
    }
}