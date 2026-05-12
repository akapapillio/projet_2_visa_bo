package com.project.VISA.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.project.VISA.dtos.DemandeResponse;
import com.project.VISA.dtos.FileUploadResponse;
import com.project.VISA.services.BusinessValidationException;
import com.project.VISA.services.DemandeService;

@WebMvcTest(DemandeController.class)
class DemandeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DemandeService demandeService;

    // ==================== Photo Upload Tests ====================

    @Test
    void uploadPhoto_validFile_returns200() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", "image/jpeg", "imagedata".getBytes());

        FileUploadResponse response = new FileUploadResponse();
        response.setDemandeId(1L);
        response.setFilePath("photos/1_uuid.jpg");
        response.setFileUrl("/api/v1/files/photos/1_uuid.jpg");
        response.setType("photo");
        response.setUploadDate(LocalDateTime.now());

        when(demandeService.uploadPhoto(eq(1L), any())).thenReturn(response);

        mockMvc.perform(multipart("/api/v1/demandes/1/photo").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.demandeId").value(1))
                .andExpect(jsonPath("$.type").value("photo"))
                .andExpect(jsonPath("$.fileUrl").isNotEmpty());
    }

    // ==================== Signature Upload Tests ====================

    @Test
    void uploadSignature_validFile_returns200() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "sig.png", "image/png", "sigdata".getBytes());

        FileUploadResponse response = new FileUploadResponse();
        response.setDemandeId(1L);
        response.setType("signature");
        response.setUploadDate(LocalDateTime.now());

        when(demandeService.uploadSignature(eq(1L), any())).thenReturn(response);

        mockMvc.perform(multipart("/api/v1/demandes/1/signature").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("signature"));
    }

    // ==================== Status Transition Tests ====================

    @Test
    void updateStatus_validTransition_returns200() throws Exception {
        DemandeResponse response = new DemandeResponse();
        response.setId(1L);
        response.setStatus("PHOTO_SIGNATURE_COMPLETE");

        when(demandeService.updateStatus(eq(1L), eq("PHOTO_SIGNATURE_COMPLETE")))
                .thenReturn(response);

        mockMvc.perform(patch("/api/v1/demandes/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"statusCode\":\"PHOTO_SIGNATURE_COMPLETE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PHOTO_SIGNATURE_COMPLETE"));
    }

    @Test
    void updateStatus_invalidTransition_returns422() throws Exception {
        when(demandeService.updateStatus(eq(1L), eq("VALIDEE")))
                .thenThrow(new BusinessValidationException("Transition non autorisée"));

        mockMvc.perform(patch("/api/v1/demandes/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"statusCode\":\"VALIDEE\"}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateStatus_missingStatusCode_returns400() throws Exception {
        mockMvc.perform(patch("/api/v1/demandes/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // ==================== GET with photo/signature URLs ====================

    @Test
    void findById_returnsPhotoAndSignatureUrls() throws Exception {
        DemandeResponse response = new DemandeResponse();
        response.setId(1L);
        response.setPhotoUrl("/api/v1/files/photos/1.jpg");
        response.setSignatureUrl("/api/v1/files/signatures/1.png");
        response.setStatus("PHOTO_SIGNATURE_COMPLETE");

        when(demandeService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/demandes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoUrl").value("/api/v1/files/photos/1.jpg"))
                .andExpect(jsonPath("$.signatureUrl").value("/api/v1/files/signatures/1.png"));
    }
}
