package com.dashboard.service;

import com.dashboard.dto.ApplicationHealthDTO;
import com.dashboard.dto.DashboardHealthDTO;
import com.dashboard.model.Application;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardHealthService {

    private final ApplicationService applicationService;

    private final ApplicationHealthService applicationHealthService;

    public DashboardHealthDTO buildDashboard() {

        List<Application> applications =
                applicationService.findAll();

        List<ApplicationHealthDTO> applicationCards =
                new ArrayList<>();

        int activeApplications = 0;
        int inactiveApplications = 0;
        int unknownApplications = 0;

        int healthy = 0;
        int degraded = 0;
        int critical = 0;

        int totalScore = 0;
        int evaluatedApplications = 0;

        int bestScore = 0;
        int worstScore = 100;

        String bestApplicationName = "-";
        String worstApplicationName = "-";

        for (Application application : applications) {

            ApplicationHealthDTO health =
                    applicationHealthService
                            .buildHealth(
                                    application.getId());

            applicationCards.add(
                    health);

            if (Boolean.FALSE.equals(
                    application.getActivo())) {

                inactiveApplications++;
                continue;
            }

            activeApplications++;

            if (health.getTotalExecutions() == 0) {

                unknownApplications++;
                continue;
            }

            int score =
                    health.getHealthScore();

            evaluatedApplications++;

            totalScore += score;

            if (score > bestScore) {

                bestScore = score;

                bestApplicationName =
                        health.getApplicationName();
            }

            if (score < worstScore) {

                worstScore = score;

                worstApplicationName =
                        health.getApplicationName();
            }

            if (score >= 80) {

                healthy++;

            } else if (score >= 50) {

                degraded++;

            } else {

                critical++;
            }
        }

        int averageScore = 0;

        if (evaluatedApplications > 0) {

            averageScore =
                    totalScore / evaluatedApplications;

        } else {

            worstScore = 0;
        }

        return DashboardHealthDTO.builder()
                .applicationsCount(
                        applications.size())
                .activeApplications(
                        activeApplications)
                .inactiveApplications(
                        inactiveApplications)
                .unknownApplications(
                        unknownApplications)
                .healthyCount(
                        healthy)
                .degradedCount(
                        degraded)
                .criticalCount(
                        critical)
                .averageHealthScore(
                        averageScore)
                .bestHealthScore(
                        bestScore)
                .bestApplicationName(
                        bestApplicationName)
                .worstHealthScore(
                        worstScore)
                .worstApplicationName(
                        worstApplicationName)
                .applications(
                        applicationCards)
                .build();
    }
}