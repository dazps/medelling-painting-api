package com.medellinpainting.medellingpaintingapi.controllers;

import com.medellinpainting.medellingpaintingapi.services.CloudinaryService;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private IProjectService projectService;

    @PostMapping("/imagen")
    public ResponseEntity<?> subirImagen(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }
        try {
            String url = cloudinaryService.uploadFile(file, "projects");
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/video")
    public ResponseEntity<?> subirVideo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }
        try {
            String url = cloudinaryService.uploadFile(file, "videos");
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/asset")
    public ResponseEntity<?> deleteAsset(@RequestParam("url") String url) {
        try {
            cloudinaryService.deleteByUrl(url);
            projectService.clearImageUrl(url);
            projectService.removeUrlFromGallery(url);
            return ResponseEntity.ok(Map.of("deleted", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Delete failed: " + e.getMessage());
        }
    }

    @GetMapping("/usage")
    public ResponseEntity<?> getUsage() {
        try {
            Map<?, ?> usage = cloudinaryService.getUsage();
            Map<?, ?> storage = (Map<?, ?>) usage.get("storage");
            Map<?, ?> credits = (Map<?, ?>) usage.get("credits");

            long usedBytes = 0L;
            double creditsUsed = 0.0;
            double creditsLimit = 25.0;
            double usedPercent = 0.0;

            if (storage != null && storage.get("usage") != null) {
                usedBytes = ((Number) storage.get("usage")).longValue();
            }
            if (credits != null) {
                if (credits.get("usage") != null) creditsUsed = ((Number) credits.get("usage")).doubleValue();
                if (credits.get("limit") != null) creditsLimit = ((Number) credits.get("limit")).doubleValue();
                if (credits.get("used_percent") != null) usedPercent = ((Number) credits.get("used_percent")).doubleValue();
            }

            return ResponseEntity.ok(Map.of(
                "usedBytes", usedBytes,
                "creditsUsed", creditsUsed,
                "creditsLimit", creditsLimit,
                "usedPercent", usedPercent
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not fetch usage: " + e.getMessage());
        }
    }
}
