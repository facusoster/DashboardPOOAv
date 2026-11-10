package com.dashboard.controller.web;

import com.dashboard.service.ApplicationImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ApplicationImportController {

    private final ApplicationImportService importService;

    @GetMapping("/applications/import")
    public String importPage() {
        return "applications/import";
    }

    @PostMapping("/applications/import")
    public String importApplications(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        // Import the applications from the uploaded JSON file
        int imported = importService.importFromJson(file);

        return "redirect:/applications";
    }
}