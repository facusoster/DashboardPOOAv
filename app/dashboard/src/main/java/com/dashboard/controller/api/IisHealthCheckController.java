package com.dashboard.controller;

import com.dashboard.dto.IisHealthCheckDTO;
import com.dashboard.service.HealthCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthchecks/iis")
public class IisHealthCheckController {

    private final HealthCheckService service;

    public IisHealthCheckController(HealthCheckService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody IisHealthCheckDTO dto) {
        service.process(dto);
        return ResponseEntity.ok().build();
    }
}
