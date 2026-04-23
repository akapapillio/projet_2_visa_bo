package com.project.VISA.config;

import com.project.VISA.models.*;
import com.project.VISA.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            TypeDemandeRepository typeRepo,
            StatusDmRepository statusRepo,
            NationaliteRepository natRepo,
            SituationFamRepository sitRepo,
            TypeVisaRepository visaTypeRepo,
            CategoriePieceRepository catRepo,
            PieceRepository pieceRepo) {
        return args -> {
            if (typeRepo.count() == 0) {
                Arrays.asList("NOUVEAU_TITRE", "RENOUVELLEMENT", "DUPLICATA", "TRANSFERT_VISA", "RECUPERATION",
                        "NOUVEAU_TITRE_TRAVAILLEUR", "NOUVEAU_TITRE_INVESTISSEUR", "NOUVEAU_VISA_TRANSFORMABLE")
                        .forEach(name -> {
                            TypeDemande t = new TypeDemande();
                            t.setNom(name);
                            typeRepo.save(t);
                        });
            }

            if (statusRepo.count() == 0) {
                StatusDm s1 = new StatusDm();
                s1.setStatus("CREE");
                s1.setObservation("Demande créée, en attente de vérification.");
                statusRepo.save(s1);

                StatusDm s2 = new StatusDm();
                s2.setStatus("EN_COURS");
                s2.setObservation("Dossier en cours d'examen.");
                statusRepo.save(s2);

                StatusDm s3 = new StatusDm();
                s3.setStatus("DOCUMENTS_MANQUANTS");
                s3.setObservation("Certaines pièces justificatives sont manquante.");
                statusRepo.save(s3);

                StatusDm s4 = new StatusDm();
                s4.setStatus("VALIDE");
                s4.setObservation("Dossier validé par le vérificateur.");
                statusRepo.save(s4);
            }

            if (natRepo.count() == 0) {
                Arrays.asList("Malagasy", "Française", "Américaine", "Chinoise", "Indienne")
                        .forEach(n -> {
                            Nationalite nat = new Nationalite();
                            nat.setNom(n);
                            natRepo.save(nat);
                        });
            }

            if (sitRepo.count() == 0) {
                Arrays.asList("Célibataire", "Marié(e)", "Divorcé(e)", "Veuf/Veuve")
                        .forEach(s -> {
                            SituationFam sit = new SituationFam();
                            sit.setLibelle(s);
                            sitRepo.save(sit);
                        });
            }

            if (visaTypeRepo.count() == 0) {
                Arrays.asList("COURT SEJOUR", "LONG SEJOUR", "TRANSFORMABLE")
                        .forEach(v -> {
                            TypeVisa tv = new TypeVisa();
                            tv.setLibelle(v);
                            visaTypeRepo.save(tv);
                        });
            }

            if (catRepo.count() == 0) {
                Arrays.asList("Photos", "Notice de renseignement", "Demande Ministre", "Copie Visa",
                        "Copie Passeport", "Certificat de Résidence", "Casier Judiciaire", "Statut Société",
                        "Extrait RC", "Carte Fiscale", "Autorisation Emploi")
                        .forEach(c -> {
                            CategoriePiece cp = new CategoriePiece();
                            cp.setLibelle(c);
                            catRepo.save(cp);
                        });
            }

            // Créer les Pieces par défaut
            if (pieceRepo.count() == 0 && catRepo.count() > 0) {
                catRepo.findAll().forEach(categorie -> {
                    Piece piece = new Piece();
                    piece.setCategoriePiece(categorie);
                    pieceRepo.save(piece);
                });
            }
        };
    }
}
