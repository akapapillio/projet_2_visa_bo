package com.project.VISA.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.project.VISA.config.FileStorageConfig;

class FileStorageServiceTest {

    private FileStorageService fileStorageService;
    private FileStorageConfig config;

    @BeforeEach
    void setUp() {
        config = new FileStorageConfig();
        config.setUploadDir("./test-uploads");
        config.setMaxFileSize(5 * 1024 * 1024); // 5MB
        fileStorageService = new FileStorageService(config);
        fileStorageService.init();
    }

    @Test
    void validateFile_emptyFile_throwsException() {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", new byte[0]);
        assertThrows(BusinessValidationException.class,
                () -> fileStorageService.validateFile(emptyFile));
    }

    @Test
    void validateFile_nullFile_throwsException() {
        assertThrows(BusinessValidationException.class,
                () -> fileStorageService.validateFile(null));
    }

    @Test
    void validateFile_tooLarge_throwsException() {
        byte[] largeContent = new byte[6 * 1024 * 1024]; // 6MB
        MockMultipartFile largeFile = new MockMultipartFile(
                "file", "large.jpg", "image/jpeg", largeContent);
        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> fileStorageService.validateFile(largeFile));
        assertTrue(ex.getMessage().contains("taille maximale"));
    }

    @Test
    void validateFile_invalidMimeType_throwsException() {
        MockMultipartFile pdfFile = new MockMultipartFile(
                "file", "doc.pdf", "application/pdf", "content".getBytes());
        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> fileStorageService.validateFile(pdfFile));
        assertTrue(ex.getMessage().contains("Type de fichier non autorisé"));
    }

    @Test
    void validateFile_validJpeg_noException() {
        MockMultipartFile jpegFile = new MockMultipartFile(
                "file", "photo.jpg", "image/jpeg", "imagedata".getBytes());
        assertDoesNotThrow(() -> fileStorageService.validateFile(jpegFile));
    }

    @Test
    void validateFile_validPng_noException() {
        MockMultipartFile pngFile = new MockMultipartFile(
                "file", "photo.png", "image/png", "imagedata".getBytes());
        assertDoesNotThrow(() -> fileStorageService.validateFile(pngFile));
    }

    @Test
    void storeFile_validFile_returnsRelativePath() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "imagedata".getBytes());
        String result = fileStorageService.storeFile(file, "photos", 1L);
        assertNotNull(result);
        assertTrue(result.startsWith("photos/"));
        assertTrue(result.contains("1_"));
        assertTrue(result.endsWith(".jpg"));
    }

    @Test
    void buildFileUrl_validPath_returnsUrl() {
        String url = fileStorageService.buildFileUrl("photos/1_abc.jpg");
        assertEquals("/api/v1/files/photos/1_abc.jpg", url);
    }

    @Test
    void buildFileUrl_nullPath_returnsNull() {
        assertNull(fileStorageService.buildFileUrl(null));
    }

    @Test
    void buildFileUrl_blankPath_returnsNull() {
        assertNull(fileStorageService.buildFileUrl("  "));
    }

    @Test
    void deleteFile_nonExistentFile_doesNotThrow() {
        assertDoesNotThrow(() -> fileStorageService.deleteFile("nonexistent/file.jpg"));
    }

    @Test
    void deleteFile_nullPath_doesNotThrow() {
        assertDoesNotThrow(() -> fileStorageService.deleteFile(null));
    }
}
