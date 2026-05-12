package com.project.VISA.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.VISA.config.FileStorageConfig;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    private final FileStorageConfig config;
    private Path uploadPath;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/bmp",
            "image/webp"
    );

    public FileStorageService(FileStorageConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        this.uploadPath = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire d'upload: " + uploadPath, e);
        }
    }

    /**
     * Valide et sauvegarde un fichier image.
     *
     * @param file       le fichier multipart envoyé
     * @param subDir     sous-répertoire (ex: "photos", "signatures")
     * @param demandeId  identifiant de la demande
     * @return le chemin relatif du fichier sauvegardé
     */
    public String storeFile(MultipartFile file, String subDir, Long demandeId) {
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);
        String uniqueFilename = demandeId + "_" + UUID.randomUUID() + extension;

        Path targetDir = uploadPath.resolve(subDir);
        try {
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire: " + targetDir, e);
        }

        Path targetPath = targetDir.resolve(uniqueFilename);
        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier: " + uniqueFilename, e);
        }

        return subDir + "/" + uniqueFilename;
    }

    /**
     * Supprime un fichier s'il existe (cleanup en cas d'annulation).
     */
    public void deleteFile(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return;
        }
        Path filePath = uploadPath.resolve(relativePath);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log silencieux, ne pas bloquer l'opération
        }
    }

    /**
     * Valide le type MIME et la taille du fichier.
     */
    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessValidationException("Le fichier est vide ou manquant.");
        }

        if (file.getSize() > config.getMaxFileSize()) {
            throw new BusinessValidationException(
                    "Le fichier dépasse la taille maximale autorisée de "
                            + (config.getMaxFileSize() / (1024 * 1024)) + " MB.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            throw new BusinessValidationException(
                    "Type de fichier non autorisé: " + contentType
                            + ". Types acceptés: " + ALLOWED_MIME_TYPES);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".jpg";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * Construit l'URL publique pour accéder au fichier.
     */
    public String buildFileUrl(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return null;
        }
        return "/api/v1/files/" + relativePath;
    }
}
