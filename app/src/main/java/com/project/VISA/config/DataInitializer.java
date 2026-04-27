package com.project.VISA.config;

import com.project.VISA.models.*;
import com.project.VISA.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            PieceRepository pieceRepo,
            PieceDemandeRepository pieceDemandeRepo) {
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
            } else {
                ensureStatus(statusRepo, "CREE", "Demande créée, en attente de vérification.");
                ensureStatus(statusRepo, "EN_COURS", "Dossier en cours d'examen.");
                ensureStatus(statusRepo, "DOCUMENTS_MANQUANTS", "Certaines pièces justificatives sont manquantes.");
                ensureStatus(statusRepo, "VALIDE", "Dossier validé par le vérificateur.");
            }

            if (natRepo.count() == 0) {
                Arrays.asList("Malagasy", "Française", "Américaine", "Chinoise", "Indienne")
                        .forEach(n -> {
                            Nationalite nat = new Nationalite();
                            nat.setNom(n);
                            natRepo.save(nat);
                        });
            } else {
                ensureNationalite(natRepo, "Malgache");
                ensureNationalite(natRepo, "Française");
                ensureNationalite(natRepo, "Portugaise");
                ensureNationalite(natRepo, "Américaine");
                ensureNationalite(natRepo, "Chinoise");
                ensureNationalite(natRepo, "Indienne");
            }

            if (sitRepo.count() == 0) {
                Arrays.asList("Célibataire", "Marié(e)", "Divorcé(e)", "Veuf/Veuve")
                        .forEach(s -> {
                            SituationFam sit = new SituationFam();
                            sit.setLibelle(s);
                            sitRepo.save(sit);
                        });
            } else {
                ensureSituation(sitRepo, "Célibataire");
                ensureSituation(sitRepo, "Marié(e)");
                ensureSituation(sitRepo, "Divorcé(e)");
                ensureSituation(sitRepo, "Veuf/Veuve");
            }

            if (visaTypeRepo.count() == 0) {
                Arrays.asList("COURT SEJOUR", "LONG SEJOUR", "TRANSFORMABLE", "TRAVAIL", "ENTREPRENEUR")
                        .forEach(v -> {
                            TypeVisa tv = new TypeVisa();
                            tv.setLibelle(v);
                            visaTypeRepo.save(tv);
                        });
            } else {
                ensureTypeVisa(visaTypeRepo, "COURT SEJOUR");
                ensureTypeVisa(visaTypeRepo, "LONG SEJOUR");
                ensureTypeVisa(visaTypeRepo, "TRANSFORMABLE");
                ensureTypeVisa(visaTypeRepo, "TRAVAIL");
                ensureTypeVisa(visaTypeRepo, "ENTREPRENEUR");
            }

            if (catRepo.count() == 0) {
                Arrays.asList("Photos", "Notice de renseignement", "Demande Ministre", "Copie Visa",
                        "Copie Passeport", "Certificat de Résidence", "Casier Judiciaire", "Statut Société",
                        "Extrait RC", "Carte Fiscale", "Autorisation Emploi", "Attestation Emploi")
                        .forEach(c -> {
                            CategoriePiece cp = new CategoriePiece();
                            cp.setLibelle(c);
                            catRepo.save(cp);
                        });
            } else {
                ensureCategorie(catRepo, "Photos");
                ensureCategorie(catRepo, "Notice de renseignement");
                ensureCategorie(catRepo, "Demande Ministre");
                ensureCategorie(catRepo, "Copie Visa");
                ensureCategorie(catRepo, "Copie Passeport");
                ensureCategorie(catRepo, "Certificat de Résidence");
                ensureCategorie(catRepo, "Casier Judiciaire");
                ensureCategorie(catRepo, "Statut Société");
                ensureCategorie(catRepo, "Extrait RC");
                ensureCategorie(catRepo, "Carte Fiscale");
                ensureCategorie(catRepo, "Autorisation Emploi");
                ensureCategorie(catRepo, "Attestation Emploi");
            }

            // Créer les Pieces par défaut
            if (pieceRepo.count() == 0 && catRepo.count() > 0) {
                catRepo.findAll().forEach(categorie -> {
                    Piece piece = new Piece();
                    piece.setCategoriePiece(categorie);
                    pieceRepo.save(piece);
                });
            }

            if (pieceDemandeRepo.count() == 0 && typeRepo.count() > 0 && catRepo.count() > 0) {
                Map<String, CategoriePiece> catByLabel = catRepo.findAll().stream()
                        .collect(Collectors.toMap(CategoriePiece::getLibelle, c -> c));

                List<String> common = Arrays.asList(
                        "Photos",
                        "Notice de renseignement",
                        "Demande Ministre",
                        "Copie Visa",
                        "Copie Passeport",
                        "Certificat de Résidence",
                        "Casier Judiciaire"
                );
                List<String> travail = Arrays.asList("Autorisation Emploi", "Attestation Emploi");
                List<String> entrepreneur = Arrays.asList("Statut Société", "Extrait RC", "Carte Fiscale");

                typeRepo.findAll().forEach(type -> {
                    List<String> labels = new ArrayList<>(common);
                    if ("NOUVEAU_TITRE_TRAVAILLEUR".equals(type.getNom())) {
                        labels.addAll(travail);
                    }
                    if ("NOUVEAU_TITRE_INVESTISSEUR".equals(type.getNom())) {
                        labels.addAll(entrepreneur);
                    }

                    labels.forEach(label -> {
                        CategoriePiece cat = catByLabel.get(label);
                        if (cat != null) {
                            PieceDemande pd = new PieceDemande();
                            pd.setTypeDemande(type);
                            pd.setCategoriePiece(cat);
                            pieceDemandeRepo.save(pd);
                        }
                    });
                });
            }
        };
    }

    private void ensureNationalite(NationaliteRepository repo, String nom) {
        boolean exists = repo.findAll().stream().anyMatch(n -> n.getNom().equalsIgnoreCase(nom));
        if (!exists) {
            Nationalite nat = new Nationalite();
            nat.setNom(nom);
            repo.save(nat);
        }
    }

    private void ensureSituation(SituationFamRepository repo, String libelle) {
        boolean exists = repo.findAll().stream().anyMatch(s -> s.getLibelle().equalsIgnoreCase(libelle));
        if (!exists) {
            SituationFam sit = new SituationFam();
            sit.setLibelle(libelle);
            repo.save(sit);
        }
    }

    private void ensureCategorie(CategoriePieceRepository repo, String libelle) {
        boolean exists = repo.findAll().stream().anyMatch(c -> c.getLibelle().equalsIgnoreCase(libelle));
        if (!exists) {
            CategoriePiece cp = new CategoriePiece();
            cp.setLibelle(libelle);
            repo.save(cp);
        }
    }

    private void ensureStatus(StatusDmRepository repo, String status, String observation) {
        boolean exists = repo.findAll().stream().anyMatch(s -> s.getStatus().equalsIgnoreCase(status));
        if (!exists) {
            StatusDm st = new StatusDm();
            st.setStatus(status);
            st.setObservation(observation);
            repo.save(st);
        }
    }

    private void ensureTypeVisa(TypeVisaRepository repo, String libelle) {
        boolean exists = repo.findAll().stream().anyMatch(v -> v.getLibelle().equalsIgnoreCase(libelle));
        if (!exists) {
            TypeVisa tv = new TypeVisa();
            tv.setLibelle(libelle);
            repo.save(tv);
        }
    }
}
