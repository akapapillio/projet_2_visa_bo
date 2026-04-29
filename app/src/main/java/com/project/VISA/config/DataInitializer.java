package com.project.VISA.config;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.VISA.models.CategoriePiece;
import com.project.VISA.models.Nationalite;
import com.project.VISA.models.PieceDemande;
import com.project.VISA.models.PieceDemandeId;
import com.project.VISA.models.SituationFam;
import com.project.VISA.models.StatusDm;
import com.project.VISA.models.TypeDemande;
import com.project.VISA.models.TypeDemandeObjetMetierObligatoire;
import com.project.VISA.models.TypeDemandeObjetMetierObligatoireId;
import com.project.VISA.models.TypeObjet;
import com.project.VISA.models.TypeVisa;
import com.project.VISA.repositories.CategoriePieceRepository;
import com.project.VISA.repositories.NationaliteRepository;
import com.project.VISA.repositories.PieceDemandeRepository;
import com.project.VISA.repositories.SituationFamRepository;
import com.project.VISA.repositories.StatusDmRepository;
import com.project.VISA.repositories.TypeDemandeObjetMetierObligatoireRepository;
import com.project.VISA.repositories.TypeDemandeRepository;
import com.project.VISA.repositories.TypeObjetRepository;
import com.project.VISA.repositories.TypeVisaRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedReferenceData(
            TypeDemandeRepository typeDemandeRepository,
            StatusDmRepository statusDmRepository,
            NationaliteRepository nationaliteRepository,
            SituationFamRepository situationFamRepository,
            TypeVisaRepository typeVisaRepository,
            CategoriePieceRepository categoriePieceRepository,
            TypeObjetRepository typeObjetRepository,
            PieceDemandeRepository pieceDemandeRepository,
            TypeDemandeObjetMetierObligatoireRepository objetObligatoireRepository) {
        return args -> {
            seedTypeDemandes(typeDemandeRepository);
            seedStatus(statusDmRepository);
            seedNationalites(nationaliteRepository);
            seedSituations(situationFamRepository);
            seedTypeVisa(typeVisaRepository);
            seedCategoriesPiece(categoriePieceRepository);
            seedTypesObjet(typeObjetRepository);
            seedRules(typeDemandeRepository, typeObjetRepository, categoriePieceRepository,
                    pieceDemandeRepository, objetObligatoireRepository);
        };
    }

    private void seedTypeDemandes(TypeDemandeRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createTypeDemande(repository, "NOUVEAU_TITRE");
        createTypeDemande(repository, "CARTE_RESIDENT");
        createTypeDemande(repository, "TRANSFERT_VISA");
    }

    private void seedStatus(StatusDmRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createStatus(repository, "DEMANDE_CREE", "Demande creee par agent");
        createStatus(repository, "EN_COURS_ANALYSE", "Analyse des documents en cours");
        createStatus(repository, "DOCUMENTS_MANQUANTS", "Pieces manquantes");
        createStatus(repository, "DOCUMENTS_VALIDES", "Pieces valides");
        createStatus(repository, "REFUSEE", "Demande refusee");
        createStatus(repository, "APPROUVEE", "Demande approuvee");
    }

    private void seedNationalites(NationaliteRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createNationalite(repository, "MALGACHE");
        createNationalite(repository, "FRANCAISE");
        createNationalite(repository, "INDIENNE");
        createNationalite(repository, "CHINOISE");
        createNationalite(repository, "SUD_AFRICAINE");
    }

    private void seedSituations(SituationFamRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createSituation(repository, "CELIBATAIRE");
        createSituation(repository, "MARIE");
        createSituation(repository, "DIVORCE");
        createSituation(repository, "VEUF");
    }

    private void seedTypeVisa(TypeVisaRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createTypeVisa(repository, "VISA_TRAVAIL");
        createTypeVisa(repository, "VISA_ETUDIANT");
        createTypeVisa(repository, "VISA_TOURISTIQUE");
        createTypeVisa(repository, "VISA_INVESTISSEUR");
    }

    private void seedCategoriesPiece(CategoriePieceRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createCategoriePiece(repository, "PHOTO_IDENTITE");
        createCategoriePiece(repository, "FORMULAIRE_DEMANDE");
        createCategoriePiece(repository, "COPIE_PASSEPORT");
        createCategoriePiece(repository, "COPIE_VISA");
        createCategoriePiece(repository, "JUSTIFICATIF_DOMICILE");
        createCategoriePiece(repository, "CONTRAT_TRAVAIL");
        createCategoriePiece(repository, "ATTESTATION_EMPLOYEUR");
        createCategoriePiece(repository, "CASIER_JUDICIAIRE");
        createCategoriePiece(repository, "ACTE_NAISSANCE");
        createCategoriePiece(repository, "CERTIFICAT_MARIAGE");
    }

    private void seedTypesObjet(TypeObjetRepository repository) {
        if (repository.count() > 0) {
            return;
        }
        createTypeObjet(repository, "PASSEPORT");
        createTypeObjet(repository, "VISA");
        createTypeObjet(repository, "VISA_TRANSFORMABLE");
        createTypeObjet(repository, "ETAT_CIVIL");
        createTypeObjet(repository, "CARTE_RESIDENT");
    }

    private void seedRules(
            TypeDemandeRepository typeDemandeRepository,
            TypeObjetRepository typeObjetRepository,
            CategoriePieceRepository categoriePieceRepository,
            PieceDemandeRepository pieceDemandeRepository,
            TypeDemandeObjetMetierObligatoireRepository objetObligatoireRepository) {

        Map<String, TypeDemande> typeMap = typeDemandeRepository.findAll().stream()
                .collect(Collectors.toMap(TypeDemande::getNom, Function.identity()));
        Map<String, TypeObjet> objetMap = typeObjetRepository.findAll().stream()
                .collect(Collectors.toMap(TypeObjet::getNom, Function.identity()));
        Map<String, CategoriePiece> pieceMap = categoriePieceRepository.findAll().stream()
                .collect(Collectors.toMap(CategoriePiece::getLibelle, Function.identity()));

        if (objetObligatoireRepository.count() == 0) {
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "NOUVEAU_TITRE", "PASSEPORT");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "NOUVEAU_TITRE", "VISA");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "NOUVEAU_TITRE", "VISA_TRANSFORMABLE");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "NOUVEAU_TITRE", "ETAT_CIVIL");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "TRANSFERT_VISA", "PASSEPORT");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "TRANSFERT_VISA", "VISA");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "CARTE_RESIDENT", "PASSEPORT");
            createObjetRule(objetObligatoireRepository, typeMap, objetMap, "CARTE_RESIDENT", "ETAT_CIVIL");
        }

        if (pieceDemandeRepository.count() == 0) {
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "NOUVEAU_TITRE", "PHOTO_IDENTITE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "NOUVEAU_TITRE", "FORMULAIRE_DEMANDE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "NOUVEAU_TITRE", "COPIE_PASSEPORT");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "NOUVEAU_TITRE", "COPIE_VISA");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "NOUVEAU_TITRE", "JUSTIFICATIF_DOMICILE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "NOUVEAU_TITRE", "CASIER_JUDICIAIRE");

            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "TRANSFERT_VISA", "PHOTO_IDENTITE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "TRANSFERT_VISA", "FORMULAIRE_DEMANDE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "TRANSFERT_VISA", "COPIE_PASSEPORT");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "TRANSFERT_VISA", "COPIE_VISA");

            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "CARTE_RESIDENT", "PHOTO_IDENTITE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "CARTE_RESIDENT", "FORMULAIRE_DEMANDE");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "CARTE_RESIDENT", "COPIE_PASSEPORT");
            createPieceRule(pieceDemandeRepository, typeMap, pieceMap, "CARTE_RESIDENT", "COPIE_VISA");
        }
    }

    private void createObjetRule(
            TypeDemandeObjetMetierObligatoireRepository repository,
            Map<String, TypeDemande> typeMap,
            Map<String, TypeObjet> objetMap,
            String typeDemande,
            String typeObjet) {
        TypeDemande td = typeMap.get(typeDemande);
        TypeObjet to = objetMap.get(typeObjet);
        if (td == null || to == null) {
            return;
        }
        TypeDemandeObjetMetierObligatoire item = new TypeDemandeObjetMetierObligatoire();
        item.setId(new TypeDemandeObjetMetierObligatoireId(td.getId(), to.getId()));
        item.setTypeDemande(td);
        item.setTypeObjet(to);
        item.setObligatoire(Boolean.TRUE);
        repository.save(item);
    }

    private void createPieceRule(
            PieceDemandeRepository repository,
            Map<String, TypeDemande> typeMap,
            Map<String, CategoriePiece> pieceMap,
            String typeDemande,
            String categoriePiece) {
        TypeDemande td = typeMap.get(typeDemande);
        CategoriePiece cp = pieceMap.get(categoriePiece);
        if (td == null || cp == null) {
            return;
        }
        PieceDemande item = new PieceDemande();
        item.setId(new PieceDemandeId(td.getId(), cp.getId()));
        item.setTypeDemande(td);
        item.setCategoriePiece(cp);
        repository.save(item);
    }

    private void createTypeDemande(TypeDemandeRepository repository, String nom) {
        TypeDemande item = new TypeDemande();
        item.setNom(nom);
        repository.save(item);
    }

    private void createStatus(StatusDmRepository repository, String code, String observation) {
        StatusDm item = new StatusDm();
        item.setCode(code);
        item.setObservation(observation);
        repository.save(item);
    }

    private void createNationalite(NationaliteRepository repository, String libelle) {
        Nationalite item = new Nationalite();
        item.setLibelle(libelle);
        repository.save(item);
    }

    private void createSituation(SituationFamRepository repository, String libelle) {
        SituationFam item = new SituationFam();
        item.setLibelle(libelle);
        repository.save(item);
    }

    private void createTypeVisa(TypeVisaRepository repository, String libelle) {
        TypeVisa item = new TypeVisa();
        item.setLibelle(libelle);
        repository.save(item);
    }

    private void createCategoriePiece(CategoriePieceRepository repository, String libelle) {
        CategoriePiece item = new CategoriePiece();
        item.setLibelle(libelle);
        repository.save(item);
    }

    private void createTypeObjet(TypeObjetRepository repository, String nom) {
        TypeObjet item = new TypeObjet();
        item.setNom(nom);
        repository.save(item);
    }
}
