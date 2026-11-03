package com.dashboard.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/executions")
public class ExecutionController {

    @GetMapping
    public String list() {
        return "executions/list";
    }
}
