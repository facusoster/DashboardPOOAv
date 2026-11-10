package com.dashboard.service;

import com.dashboard.dto.ApplicationImportDTO;
import com.dashboard.model.Application;
// import com.fasterxml.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationImportService {

    private final ApplicationService applicationService;

    private final ObjectMapper objectMapper;

    public int importFromJson(MultipartFile file)
            throws Exception {

        ApplicationImportDTO[] applications =
        objectMapper.readValue(
                file.getInputStream(),
                ApplicationImportDTO[].class);

        int imported = 0;

        for (ApplicationImportDTO dto : applications) {

            if (applicationService.findByExternalId(dto.getId()) != null) {
                continue;
            }

            Application application =
                    Application.builder()
                            .externalId(dto.getId())
                            .nombre(dto.getName())
                            .url(dto.getUrl())
                            .descripcion(dto.getDescription())
                            .criticality(dto.getCriticality())
                            .tipo("IIS")
                            .umbralRespuestaMs(
                                    dto.getTimeoutSeconds() != null
                                            ? dto.getTimeoutSeconds() * 1000
                                            : 1000
                            )
                            .activo(
                                    dto.getEnabled() != null
                                            ? dto.getEnabled()
                                            : true)
                            .build();

            applicationService.save(application);

            imported++;
        }

        return imported;
    }
}