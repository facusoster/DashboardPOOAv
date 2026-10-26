package com.dashboard.service;

import com.dashboard.dto.IisHealthCheckDTO;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    public void process(IisHealthCheckDTO dto) {

        System.out.println("Health Check recibido:");
        System.out.println(dto);

    }
}