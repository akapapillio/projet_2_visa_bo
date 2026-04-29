INSERT INTO type_demande (nom_type_dm) VALUES
('NOUVEAU_TITRE'),
('CARTE_RESIDENT'),
('TRANSFERT_VISA');
-- ('RENOUVELLEMENT_TITRE'),
-- ('CHANGEMENT_STATUT'),
-- ('CARTE_RESIDENT');


INSERT INTO status_dm (status_dm, observation) VALUES
('DEMANDE_CREE', 'Demande créée par l''agent'),
('EN_COURS_ANALYSE', 'Analyse des documents en cours'),
('DOCUMENTS_MANQUANTS', 'Des pièces sont manquantes'),
('DOCUMENTS_VALIDES', 'Toutes les pièces sont validées'),
('REFUSEE', 'Demande refusée'),
('APPROUVEE', 'Demande approuvée');


INSERT INTO nationalite (nationalite) VALUES
('MALGACHE'),
('FRANCAISE'),
('INDIENNE'),
('CHINOISE'),
('SUD_AFRICAINE');

INSERT INTO situation_fam (situation_fam) VALUES
('CELIBATAIRE'),
('MARIE'),
('DIVORCE'),
('VEUF');

INSERT INTO type_visa (libelle) VALUES
('VISA_TRAVAIL'),
('VISA_ETUDIANT'),
('VISA_TOURISTIQUE'),
('VISA_INVESTISSEUR');

INSERT INTO categorie_piece (libelle) VALUES
('PHOTO_IDENTITE'),
('FORMULAIRE_DEMANDE'),
('COPIE_PASSEPORT'),
('COPIE_VISA'),
('JUSTIFICATIF_DOMICILE'),
('CONTRAT_TRAVAIL'),
('ATTESTATION_EMPLOYEUR'),
('CASIER_JUDICIAIRE'),
('ACTE_NAISSANCE'),
('CERTIFICAT_MARIAGE');

INSERT INTO type_objet (nom_objet) VALUES
('PASSEPORT'),
('VISA'),
('VISA_TRANSFORMABLE'),
('ETAT_CIVIL'),
('CARTE_RESIDENT');



-- NOUVEAU TITRE (id_type_dm = 1)
INSERT INTO type_demande_objet_metier_obligatoire VALUES
(1, 1, TRUE), -- PASSEPORT
(1, 2, TRUE), -- VISA
(1, 3, TRUE), -- VISA_TRANSFORMABLE
(1, 4, TRUE); -- ETAT_CIVIL

-- TRANSFERT VISA (id_type_dm = 3)
INSERT INTO type_demande_objet_metier_obligatoire VALUES
(3, 1, TRUE),
(3, 2, TRUE);

-- CARTE RESIDENT (id_type_dm = 2)
INSERT INTO type_demande_objet_metier_obligatoire VALUES
(2, 1, TRUE),
(2, 4, TRUE);




-- Pièces obligatoires par type de demande
-- NOUVEAU TITRE
INSERT INTO piece_demande VALUES
(1,1), -- photo
(1,2), -- formulaire
(1,3), -- copie passeport
(1,4), -- copie visa
(1,5), -- justificatif domicile
(1,8); -- casier judiciaire




-- Exemple pour TRANSFERT VISA
INSERT INTO piece_demande VALUES
(3,1),
(3,2),
(3,3),
(3,4);


-- Exemple pour duplicata cart resident
INSERT INTO piece_demande VALUES
(2,1),
(2,2),
(2,3),
(2,4);