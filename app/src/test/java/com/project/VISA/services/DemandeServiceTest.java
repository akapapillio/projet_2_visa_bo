package com.project.VISA.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.project.VISA.dtos.DemandeResponse;
import com.project.VISA.dtos.FileUploadResponse;
import com.project.VISA.models.*;
import com.project.VISA.repositories.*;

@ExtendWith(MockitoExtension.class)
class DemandeServiceTest {

    @Mock private DemandeRepository demandeRepository;
    @Mock private DemandeurService demandeurService;
    @Mock private TypeDemandeRepository typeDemandeRepository;
    @Mock private StatusDmRepository statusDmRepository;
    @Mock private TypeVisaRepository typeVisaRepository;
    @Mock private PieceDemandeRepository pieceDemandeRepository;
    @Mock private TypeDemandeObjetMetierObligatoireRepository objetMetierObligatoireRepository;
    @Mock private PieceRepository pieceRepository;
    @Mock private PasseportRepository passeportRepository;
    @Mock private VisaRepository visaRepository;
    @Mock private VisaTransformableRepository visaTransformableRepository;
    @Mock private EtatCivilRepository etatCivilRepository;
    @Mock private CarteResidentRepository carteResidentRepository;
    @Mock private FileStorageService fileStorageService;

    private DemandeService demandeService;

    private Demande demande;
    private Demandeur demandeur;
    private StatusDm statusCree;
    private StatusDm statusPhotoComplete;
    private StatusDm statusRefusee;
    private TypeDemande typeDemande;

    @BeforeEach
    void setUp() {
        demandeService = new DemandeService(
                demandeRepository, demandeurService, typeDemandeRepository,
                statusDmRepository, typeVisaRepository, pieceDemandeRepository,
                objetMetierObligatoireRepository, pieceRepository, passeportRepository,
                visaRepository, visaTransformableRepository, etatCivilRepository,
                carteResidentRepository, fileStorageService);

        demandeur = new Demandeur();
        demandeur.setId(1L);
        demandeur.setNom("Dupont");
        demandeur.setPrenom("Jean");

        typeDemande = new TypeDemande();
        typeDemande.setId(1L);
        typeDemande.setNom("NOUVEAU_TITRE");

        statusCree = new StatusDm();
        statusCree.setId(1L);
        statusCree.setCode("DEMANDE_CREE");

        statusPhotoComplete = new StatusDm();
        statusPhotoComplete.setId(2L);
        statusPhotoComplete.setCode("PHOTO_SIGNATURE_COMPLETE");

        statusRefusee = new StatusDm();
        statusRefusee.setId(5L);
        statusRefusee.setCode("REFUSEE");

        demande = new Demande();
        demande.setId(1L);
        demande.setDemandeur(demandeur);
        demande.setTypeDemande(typeDemande);
        demande.setStatus(statusCree);
    }

    // ==================== Upload Photo Tests ====================

    @Test
    void uploadPhoto_validFile_success() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "photo.jpg", "image/jpeg", "data".getBytes());

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(fileStorageService.storeFile(any(), eq("photos"), eq(1L)))
                .thenReturn("photos/1_uuid.jpg");
        when(fileStorageService.buildFileUrl("photos/1_uuid.jpg"))
                .thenReturn("/api/v1/files/photos/1_uuid.jpg");
        when(demandeRepository.save(any())).thenReturn(demande);

        FileUploadResponse response = demandeService.uploadPhoto(1L, file);

        assertEquals(1L, response.getDemandeId());
        assertEquals("photo", response.getType());
        assertEquals("photos/1_uuid.jpg", response.getFilePath());
        assertNotNull(response.getUploadDate());
        verify(fileStorageService).storeFile(file, "photos", 1L);
    }

    @Test
    void uploadPhoto_replacesOldFile() {
        demande.setPhotoPath("photos/old.jpg");
        MockMultipartFile file = new MockMultipartFile(
                "file", "new.jpg", "image/jpeg", "data".getBytes());

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(fileStorageService.storeFile(any(), eq("photos"), eq(1L)))
                .thenReturn("photos/1_new.jpg");
        when(fileStorageService.buildFileUrl(any())).thenReturn("/api/v1/files/photos/1_new.jpg");
        when(demandeRepository.save(any())).thenReturn(demande);

        demandeService.uploadPhoto(1L, file);

        verify(fileStorageService).deleteFile("photos/old.jpg");
    }

    // ==================== Upload Signature Tests ====================

    @Test
    void uploadSignature_validFile_success() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "sig.png", "image/png", "data".getBytes());

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(fileStorageService.storeFile(any(), eq("signatures"), eq(1L)))
                .thenReturn("signatures/1_uuid.png");
        when(fileStorageService.buildFileUrl("signatures/1_uuid.png"))
                .thenReturn("/api/v1/files/signatures/1_uuid.png");
        when(demandeRepository.save(any())).thenReturn(demande);

        FileUploadResponse response = demandeService.uploadSignature(1L, file);

        assertEquals("signature", response.getType());
        verify(fileStorageService).storeFile(file, "signatures", 1L);
    }

    // ==================== Status Transition Tests ====================

    @Test
    void updateStatus_toPhotoComplete_withBothFiles_success() {
        demande.setPhotoPath("photos/1.jpg");
        demande.setSignaturePath("signatures/1.png");

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(statusDmRepository.findByCode("PHOTO_SIGNATURE_COMPLETE"))
                .thenReturn(Optional.of(statusPhotoComplete));
        when(demandeRepository.save(any())).thenReturn(demande);

        DemandeResponse response = demandeService.updateStatus(1L, "PHOTO_SIGNATURE_COMPLETE");

        assertNotNull(response);
        verify(statusDmRepository).findByCode("PHOTO_SIGNATURE_COMPLETE");
    }

    @Test
    void updateStatus_toPhotoComplete_missingPhoto_refusesAutomatically() {
        demande.setSignaturePath("signatures/1.png");
        // photo is null

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(statusDmRepository.findByCode("REFUSEE")).thenReturn(Optional.of(statusRefusee));
        when(demandeRepository.save(any())).thenReturn(demande);

        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> demandeService.updateStatus(1L, "PHOTO_SIGNATURE_COMPLETE"));

        assertTrue(ex.getMessage().contains("Photo non fournie"));
        verify(demandeRepository).save(argThat(d -> "REFUSEE".equals(d.getStatus().getCode())));
    }

    @Test
    void updateStatus_toPhotoComplete_missingBoth_refusesAutomatically() {
        // both photo and signature are null

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(statusDmRepository.findByCode("REFUSEE")).thenReturn(Optional.of(statusRefusee));
        when(demandeRepository.save(any())).thenReturn(demande);

        BusinessValidationException ex = assertThrows(BusinessValidationException.class,
                () -> demandeService.updateStatus(1L, "PHOTO_SIGNATURE_COMPLETE"));

        assertTrue(ex.getMessage().contains("Photo et signature non fournies"));
    }

    @Test
    void updateStatus_invalidTransition_throwsException() {
        // Current: DEMANDE_CREE, target: VALIDEE (not allowed)
        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));

        assertThrows(BusinessValidationException.class,
                () -> demandeService.updateStatus(1L, "VALIDEE"));
    }

    @Test
    void updateStatus_toRefusee_setsDefaultReason() {
        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(statusDmRepository.findByCode("REFUSEE")).thenReturn(Optional.of(statusRefusee));
        when(demandeRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        demandeService.updateStatus(1L, "REFUSEE");

        verify(demandeRepository).save(argThat(d ->
                "Demande refusée manuellement".equals(d.getRaisonRefus())));
    }

    // ==================== Delete with cleanup Tests ====================

    @Test
    void delete_cleansUpFiles() {
        demande.setPhotoPath("photos/1.jpg");
        demande.setSignaturePath("signatures/1.png");

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));

        demandeService.delete(1L);

        verify(fileStorageService).deleteFile("photos/1.jpg");
        verify(fileStorageService).deleteFile("signatures/1.png");
        verify(demandeRepository).delete(demande);
    }

    // ==================== Validation Tests ====================

    @Test
    void validate_missingPhotoAndSignature_reportsThem() {
        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(pieceDemandeRepository.findByTypeDemandeId(any())).thenReturn(Collections.emptyList());
        when(objetMetierObligatoireRepository.findByTypeDemandeIdAndObligatoireTrue(any()))
                .thenReturn(Collections.emptyList());

        var result = demandeService.validate(1L);

        assertFalse(result.isValide());
        assertTrue(result.getPiecesManquantes().contains("Photo d'identité"));
        assertTrue(result.getPiecesManquantes().contains("Signature"));
    }

    @Test
    void validate_withPhotoAndSignature_doesNotReportThem() {
        demande.setPhotoPath("photos/1.jpg");
        demande.setSignaturePath("signatures/1.png");

        when(demandeRepository.findById(1L)).thenReturn(Optional.of(demande));
        when(pieceDemandeRepository.findByTypeDemandeId(any())).thenReturn(Collections.emptyList());
        when(objetMetierObligatoireRepository.findByTypeDemandeIdAndObligatoireTrue(any()))
                .thenReturn(Collections.emptyList());

        var result = demandeService.validate(1L);

        assertTrue(result.isValide());
        assertFalse(result.getPiecesManquantes().contains("Photo d'identité"));
        assertFalse(result.getPiecesManquantes().contains("Signature"));
    }
}
