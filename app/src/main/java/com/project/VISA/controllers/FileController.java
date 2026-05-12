package com.project.VISA.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.VISA.config.FileStorageConfig;
import com.project.VISA.services.ResourceNotFoundException;

/**
 * Contrôleur pour servir les fichiers uploadés (photos, signatures).
 * Sert les fichiers depuis le répertoire d'upload configuré.
 */
@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final Path uploadPath;

    public FileController(FileStorageConfig config) {
        this.uploadPath = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
    }

    /**
     * GET /api/v1/files/{subDir}/{filename}
     * Sert un fichier uploadé (photo ou signature).
     */
    @GetMapping("/{subDir}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(
            @PathVariable String subDir,
            @PathVariable String filename) {

        try {
            Path filePath = uploadPath.resolve(subDir).resolve(filename).normalize();

            // Sécurité: vérifier que le fichier est bien dans le répertoire d'upload
            if (!filePath.startsWith(uploadPath)) {
                throw new ResourceNotFoundException("Accès non autorisé au fichier.");
            }

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceNotFoundException("Fichier introuvable: " + filename);
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (IOException e) {
            throw new ResourceNotFoundException("Erreur lors de la lecture du fichier: " + filename);
        }
    }
}
