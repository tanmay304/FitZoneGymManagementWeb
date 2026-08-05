package com.fitzone.controller;

import com.fitzone.service.BackupService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.nio.file.Files;

@Controller
@RequestMapping("/settings")
public class WebSettingsController {

    @GetMapping
    public String viewSettings(Model model) {
        return "settings";
    }

    @GetMapping("/backup")
    public ResponseEntity<byte[]> downloadBackup() {
        try {
            File sqlBackup = BackupService.createDatabaseBackup();
            if (sqlBackup != null && sqlBackup.exists()) {
                byte[] bytes = Files.readAllBytes(sqlBackup.toPath());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + sqlBackup.getName())
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/restore")
    public String restoreBackup(@RequestParam("backupFile") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (!file.isEmpty()) {
                File tempFile = File.createTempFile("restore_", ".sql");
                file.transferTo(tempFile);
                if (BackupService.restoreDatabaseBackup(tempFile)) {
                    tempFile.delete();
                    redirectAttributes.addFlashAttribute("successMessage", "Database restored successfully!");
                    return "redirect:/settings";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to restore database backup.");
        return "redirect:/settings";
    }
}
