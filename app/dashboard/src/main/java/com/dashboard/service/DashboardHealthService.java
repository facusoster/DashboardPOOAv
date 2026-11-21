package com.dashboard.service;

import com.dashboard.dto.ApplicationHealthDTO;
import com.dashboard.dto.DashboardHealthDTO;
import com.dashboard.model.Application;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardHealthService {

    private final ApplicationService applicationService;

    private final ApplicationHealthService applicationHealthService;

    public DashboardHealthDTO buildDashboard() {

        List<Application> applications
                = applicationService.findAll();

        List<ApplicationHealthDTO> applicationCards
                = new ArrayList<>();

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

            ApplicationHealthDTO health
                    = applicationHealthService
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

            int score
                    = health.getHealthScore();

            evaluatedApplications++;

            totalScore += score;

            if (score > bestScore) {

                bestScore = score;

                bestApplicationName
                        = health.getApplicationName();
            }

            if (score < worstScore) {

                worstScore = score;

                worstApplicationName
                        = health.getApplicationName();
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

            averageScore
                    = totalScore / evaluatedApplications;

        } else {

            worstScore = 0;
        }

        List<ApplicationHealthDTO> top5BestScore
                = applicationCards
                        .stream()
                        .filter(app
                                -> app.getTotalExecutions() > 0)
                        .sorted(
                                Comparator.comparingInt(
                                        ApplicationHealthDTO::getHealthScore)
                                        .reversed())
                        .limit(5)
                        .toList();

        List<ApplicationHealthDTO> top5WorstScore
                = applicationCards
                        .stream()
                        .filter(app
                                -> app.getTotalExecutions() > 0)
                        .sorted(
                                Comparator.comparingInt(
                                        ApplicationHealthDTO::getHealthScore))
                        .limit(5)
                        .toList();

        List<ApplicationHealthDTO> top5BestPerformance
                = applicationCards
                        .stream()
                        .filter(app
                                -> app.getTotalExecutions() > 0)
                        .filter(app
                                -> app.getLastResponseTime() != null)
                        .sorted(
                                Comparator.comparingInt(
                                        ApplicationHealthDTO::getLastResponseTime))
                        .limit(5)
                        .toList();

        List<ApplicationHealthDTO> top5WorstPerformance
                = applicationCards
                        .stream()
                        .filter(app
                                -> app.getTotalExecutions() > 0)
                        .filter(app
                                -> app.getLastResponseTime() != null)
                        .sorted(
                                Comparator.comparingInt(
                                        ApplicationHealthDTO::getLastResponseTime)
                                        .reversed())
                        .limit(5)
                        .toList();

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
                .top5BestScore(
                        top5BestScore)
                .top5WorstScore(
                        top5WorstScore)
                .top5BestPerformance(
                        top5BestPerformance)
                .top5WorstPerformance(
                        top5WorstPerformance)
                .build();
    }
}
