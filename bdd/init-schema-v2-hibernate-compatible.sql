-- =============================================================================
--  VISA TRANSFORMABLE — Base de données complète (Version 2.0 - Hibernate Compatible)
--  SGBD           : PostgreSQL 14+
--  Projet         : projet_2_visa_bo
--  Schéma         : public (par défaut)
--  Compatibility  : Spring Boot 3.2.4 + Hibernate 6.4.4 + JPA
--
--  CHANGEMENTS V2.0 :
--  ✓ Tous les ID utilisent BIGINT (compatible JPA @Id Long)
--  ✓ Types de données standardisés
--  ✓ Clés étrangères explicites avec CASCADE/RESTRICT
--  ✓ Gestion des vues automatique (DROP CASCADE)
--  ✓ Validations et contraintes robustes
-- =============================================================================

-- =============================================================================
--  NETTOYAGE PROPRE (ordre inverse des FK + CASCADE)
-- =============================================================================

-- Supprimer les vues d'abord (elles dépendent des tables)
DROP VIEW IF EXISTS v_avancement_demande CASCADE;

-- Supprimer les tables en cascade (ordre inverse des dépendances)
DROP TABLE IF EXISTS piece_demande           CASCADE;
DROP TABLE IF EXISTS visa_passeport          CASCADE;
DROP TABLE IF EXISTS demande                 CASCADE;
DROP TABLE IF EXISTS carte_resident          CASCADE;
DROP TABLE IF EXISTS visa_transformable      CASCADE;
DROP TABLE IF EXISTS visa                    CASCADE;
DROP TABLE IF EXISTS passeport               CASCADE;
DROP TABLE IF EXISTS etat_civil              CASCADE;
DROP TABLE IF EXISTS demandeur               CASCADE;
DROP TABLE IF EXISTS piece                   CASCADE;
DROP TABLE IF EXISTS categorie_piece         CASCADE;
DROP TABLE IF EXISTS type_visa               CASCADE;
DROP TABLE IF EXISTS situation_fam           CASCADE;
DROP TABLE IF EXISTS nationalite             CASCADE;
DROP TABLE IF EXISTS status_dm               CASCADE;
DROP TABLE IF EXISTS type_demande            CASCADE;

-- Supprimer les séquences
DROP SEQUENCE IF EXISTS type_demande_id_seq CASCADE;
DROP SEQUENCE IF EXISTS status_dm_id_seq CASCADE;
DROP SEQUENCE IF EXISTS nationalite_id_seq CASCADE;
DROP SEQUENCE IF EXISTS situation_fam_id_seq CASCADE;
DROP SEQUENCE IF EXISTS type_visa_id_seq CASCADE;
DROP SEQUENCE IF EXISTS categorie_piece_id_seq CASCADE;
DROP SEQUENCE IF EXISTS piece_id_seq CASCADE;
DROP SEQUENCE IF EXISTS demandeur_id_seq CASCADE;
DROP SEQUENCE IF EXISTS passeport_id_seq CASCADE;
DROP SEQUENCE IF EXISTS etat_civil_id_seq CASCADE;
DROP SEQUENCE IF EXISTS visa_id_seq CASCADE;
DROP SEQUENCE IF EXISTS carte_resident_id_seq CASCADE;
DROP SEQUENCE IF EXISTS visa_transformable_id_seq CASCADE;
DROP SEQUENCE IF EXISTS demande_id_seq CASCADE;

-- =============================================================================
--  CRÉATION DES SÉQUENCES (BIGINT pour JPA compatibility)
-- =============================================================================

CREATE SEQUENCE type_demande_id_seq START 1;
CREATE SEQUENCE status_dm_id_seq START 1;
CREATE SEQUENCE nationalite_id_seq START 1;
CREATE SEQUENCE situation_fam_id_seq START 1;
CREATE SEQUENCE type_visa_id_seq START 1;
CREATE SEQUENCE categorie_piece_id_seq START 1;
CREATE SEQUENCE piece_id_seq START 1;
CREATE SEQUENCE demandeur_id_seq START 1;
CREATE SEQUENCE passeport_id_seq START 1;
CREATE SEQUENCE etat_civil_id_seq START 1;
CREATE SEQUENCE visa_id_seq START 1;
CREATE SEQUENCE carte_resident_id_seq START 1;
CREATE SEQUENCE visa_transformable_id_seq START 1;
CREATE SEQUENCE demande_id_seq START 10000;

-- =============================================================================
--  TABLES DE RÉFÉRENCE (lookups) - BIGINT IDs
-- =============================================================================

CREATE TABLE type_demande (
    id BIGINT PRIMARY KEY DEFAULT nextval('type_demande_id_seq'),
    nom VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uk_type_demande_nom UNIQUE(nom)
);
COMMENT ON TABLE type_demande IS 'Types de demandes (NOUVEAU, RENOUVELLEMENT, TRANSFORMATION)';

CREATE TABLE status_dm (
    id BIGINT PRIMARY KEY DEFAULT nextval('status_dm_id_seq'),
    status VARCHAR(50) NOT NULL UNIQUE,
    observation VARCHAR(250),
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uk_status_dm_status UNIQUE(status)
);
COMMENT ON TABLE status_dm IS 'Statuts d''une demande (CREE, EN_COURS, VALIDE, REJETE)';

CREATE TABLE nationalite (
    id BIGINT PRIMARY KEY DEFAULT nextval('nationalite_id_seq'),
    nom VARCHAR(50) NOT NULL UNIQUE,
    code_iso CHAR(2),
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uk_nationalite_nom UNIQUE(nom)
);
COMMENT ON TABLE nationalite IS 'Nationalités des demandeurs';

CREATE TABLE situation_fam (
    id BIGINT PRIMARY KEY DEFAULT nextval('situation_fam_id_seq'),
    libelle VARCHAR(50) NOT NULL UNIQUE,
    code VARCHAR(20),
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uk_situation_fam_libelle UNIQUE(libelle)
);
COMMENT ON TABLE situation_fam IS 'Situations familiales (CELIBATAIRE, MARIE, DIVORCE, VEUF)';

CREATE TABLE type_visa (
    id BIGINT PRIMARY KEY DEFAULT nextval('type_visa_id_seq'),
    libelle VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uk_type_visa_libelle UNIQUE(libelle)
);
COMMENT ON TABLE type_visa IS 'Types de visa (COURT_SEJOUR, LONG_SEJOUR, TRAVAIL, ENTREPRENEUR)';

CREATE TABLE categorie_piece (
    id BIGINT PRIMARY KEY DEFAULT nextval('categorie_piece_id_seq'),
    libelle VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uk_categorie_piece_libelle UNIQUE(libelle)
);
COMMENT ON TABLE categorie_piece IS 'Catégories de pièces justificatives';

-- =============================================================================
--  PIÈCES (rattachement aux catégories)
-- =============================================================================

CREATE TABLE piece (
    id BIGINT PRIMARY KEY DEFAULT nextval('piece_id_seq'),
    id_categorie_piece BIGINT NOT NULL REFERENCES categorie_piece(id) ON DELETE RESTRICT,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE piece IS 'Liste des pièces justificatives';
CREATE INDEX idx_piece_categorie ON piece(id_categorie_piece);

-- =============================================================================
--  DEMANDEUR
-- =============================================================================

CREATE TABLE demandeur (
    id BIGINT PRIMARY KEY DEFAULT nextval('demandeur_id_seq'),
    -- Identité
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(100),
    nom_jeune_fille VARCHAR(50),
    date_naissance DATE,
    lieu_naissance VARCHAR(100),
    -- Situation personnelle (FK avec RESTRICT pour protéger les données de référence)
    id_situation_fam BIGINT NOT NULL REFERENCES situation_fam(id) ON DELETE RESTRICT,
    id_nationalite BIGINT NOT NULL REFERENCES nationalite(id) ON DELETE RESTRICT,
    -- Domicile & activité professionnelle
    adresse_domicile VARCHAR(255),
    profession VARCHAR(100),
    nom_employeur VARCHAR(100),
    adresse_employeur VARCHAR(255),
    -- Pièce d'identité principale
    id_piece BIGINT NOT NULL REFERENCES piece(id) ON DELETE RESTRICT,
    -- Audit
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT ck_nom_not_empty CHECK (nom <> '')
);
COMMENT ON TABLE demandeur IS 'Demandeurs de visa';
CREATE INDEX idx_demandeur_nom ON demandeur(nom);
CREATE INDEX idx_demandeur_prenom ON demandeur(prenom);
CREATE INDEX idx_demandeur_nationalite ON demandeur(id_nationalite);

-- =============================================================================
--  ÉTAT CIVIL
-- =============================================================================

CREATE TABLE etat_civil (
    id BIGINT PRIMARY KEY DEFAULT nextval('etat_civil_id_seq'),
    id_demandeur BIGINT NOT NULL UNIQUE REFERENCES demandeur(id) ON DELETE CASCADE,
    -- Champs civils additionnels
    nom VARCHAR(50),
    prenoms VARCHAR(100),
    nom_jeune_fille VARCHAR(50),
    date_naissance DATE,
    situation_familiale VARCHAR(50),
    nationalite VARCHAR(50),
    domicile_habituel VARCHAR(255),
    profession VARCHAR(100),
    employeur VARCHAR(100),
    adresse_employeur VARCHAR(255),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE etat_civil IS 'Informations d''état civil des demandeurs';
CREATE INDEX idx_etat_civil_demandeur ON etat_civil(id_demandeur);

-- =============================================================================
--  PASSEPORT
-- =============================================================================

CREATE TABLE passeport (
    id BIGINT PRIMARY KEY DEFAULT nextval('passeport_id_seq'),
    id_demandeur BIGINT NOT NULL REFERENCES demandeur(id) ON DELETE CASCADE,
    numero_passeport VARCHAR(20),
    date_delivrance DATE,
    date_expiration DATE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    CONSTRAINT ck_passeport_numero_not_empty CHECK (numero_passeport <> '')
);
COMMENT ON TABLE passeport IS 'Passeports des demandeurs';
CREATE INDEX idx_passeport_demandeur ON passeport(id_demandeur);
CREATE INDEX idx_passeport_numero ON passeport(numero_passeport);

-- =============================================================================
--  VISA (visas accordés)
-- =============================================================================

CREATE TABLE visa (
    id BIGINT PRIMARY KEY DEFAULT nextval('visa_id_seq'),
    id_demandeur BIGINT NOT NULL REFERENCES demandeur(id) ON DELETE CASCADE,
    id_type_visa BIGINT NOT NULL REFERENCES type_visa(id) ON DELETE RESTRICT,
    numero_visa VARCHAR(50) UNIQUE,
    date_delivrance DATE,
    date_expiration DATE,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    reference VARCHAR(50),
    date_modification TIMESTAMP,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE visa IS 'Visas accordés aux demandeurs';
CREATE INDEX idx_visa_demandeur ON visa(id_demandeur);
CREATE INDEX idx_visa_type ON visa(id_type_visa);
CREATE INDEX idx_visa_numero ON visa(numero_visa);

-- =============================================================================
--  CARTE DE RÉSIDENT
-- =============================================================================

CREATE TABLE carte_resident (
    id BIGINT PRIMARY KEY DEFAULT nextval('carte_resident_id_seq'),
    id_demandeur BIGINT NOT NULL REFERENCES demandeur(id) ON DELETE CASCADE,
    numero_carte VARCHAR(50),
    date_delivrance DATE,
    date_expiration DATE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE carte_resident IS 'Cartes de résident';
CREATE INDEX idx_carte_resident_demandeur ON carte_resident(id_demandeur);
CREATE INDEX idx_carte_resident_numero ON carte_resident(numero_carte);

-- =============================================================================
--  VISA TRANSFORMABLE (visa précédent à transformer)
-- =============================================================================

CREATE TABLE visa_transformable (
    id BIGINT PRIMARY KEY DEFAULT nextval('visa_transformable_id_seq'),
    id_demandeur BIGINT NOT NULL REFERENCES demandeur(id) ON DELETE CASCADE,
    id_passeport BIGINT NOT NULL REFERENCES passeport(id) ON DELETE RESTRICT,
    numero_visa VARCHAR(50),
    date_delivrance DATE,
    date_expiration DATE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE visa_transformable IS 'Visas précédents transformables';
CREATE INDEX idx_visa_transformable_demandeur ON visa_transformable(id_demandeur);
CREATE INDEX idx_visa_transformable_passeport ON visa_transformable(id_passeport);

-- =============================================================================
--  DEMANDE (table centrale — porte tout le DemandeDTO)
-- =============================================================================

CREATE TABLE demande (
    id BIGINT PRIMARY KEY DEFAULT nextval('demande_id_seq'),
    -- Clés étrangères organisationnelles
    id_demandeur BIGINT NOT NULL REFERENCES demandeur(id) ON DELETE CASCADE,
    id_status_dm BIGINT NOT NULL REFERENCES status_dm(id) ON DELETE RESTRICT,
    id_type_dm BIGINT NOT NULL REFERENCES type_demande(id) ON DELETE RESTRICT,
    -- Type de visa demandé
    type_visa VARCHAR(50),
    -- -------------------------------------------------------------------------
    --  Pièces du dossier (13 booléens du DemandeDTO)
    -- -------------------------------------------------------------------------
    a_fourni_photos BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_notice_renseignement BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_demande_ministre BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_copie_visa BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_copie_passeport BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_copie_carte_resident BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_certificat_residence BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_extrait_casier_judiciaire BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_statut_societe BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_extrait_rc BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_carte_fiscale BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_autorisation_emploi BOOLEAN NOT NULL DEFAULT FALSE,
    a_fourni_attestation_emploi BOOLEAN NOT NULL DEFAULT FALSE,
    -- Audit
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);
COMMENT ON TABLE demande IS 'Demandes de visa';
CREATE INDEX idx_demande_demandeur ON demande(id_demandeur);
CREATE INDEX idx_demande_status ON demande(id_status_dm);
CREATE INDEX idx_demande_type ON demande(id_type_dm);
CREATE INDEX idx_demande_created ON demande(created_at DESC);
CREATE INDEX idx_demande_updated ON demande(updated_at DESC);

-- Trigger : mise à jour automatique de updated_at
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_demande_updated_at
BEFORE UPDATE ON demande
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_etat_civil_updated_at
BEFORE UPDATE ON etat_civil
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_passeport_updated_at
BEFORE UPDATE ON passeport
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_visa_updated_at
BEFORE UPDATE ON visa
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

-- =============================================================================
--  TABLES D'ASSOCIATION
-- =============================================================================

CREATE TABLE visa_passeport (
    id_visa BIGINT NOT NULL REFERENCES visa(id) ON DELETE CASCADE,
    id_passeport BIGINT NOT NULL REFERENCES passeport(id) ON DELETE CASCADE,
    status_liaison VARCHAR(50),
    PRIMARY KEY (id_visa, id_passeport)
);
COMMENT ON TABLE visa_passeport IS 'Liaison entre visas et passeports';

CREATE TABLE piece_demande (
    id_type_dm BIGINT NOT NULL REFERENCES type_demande(id) ON DELETE CASCADE,
    id_categorie_piece BIGINT NOT NULL REFERENCES categorie_piece(id) ON DELETE CASCADE,
    PRIMARY KEY (id_type_dm, id_categorie_piece)
);
COMMENT ON TABLE piece_demande IS 'Pièces requises pour chaque type de demande';

-- =============================================================================
--  DONNÉES DE RÉFÉRENCE (INSERT)
-- =============================================================================

-- Types de demande
INSERT INTO type_demande (nom, description) VALUES
    ('TRANSFORMATION', 'Transformation d''un visa existant'),
    ('RENOUVELLEMENT', 'Renouvellement d''un visa'),
    ('PREMIERE_DEMANDE', 'Première demande de visa');

-- Statuts
INSERT INTO status_dm (status, observation) VALUES
    ('CREE', 'Demande créée'),
    ('EN_COURS', 'Demande en cours d''instruction'),
    ('DOCUMENTS_MANQUANTS', 'Documents manquants'),
    ('VALIDE', 'Demande validée'),
    ('REJETE', 'Demande rejetée');

-- Nationalités
INSERT INTO nationalite (nom, code_iso) VALUES
    ('Malgache', 'MG'),
    ('Française', 'FR'),
    ('Américaine', 'US'),
    ('Britannique', 'GB'),
    ('Allemande', 'DE'),
    ('Chinoise', 'CN'),
    ('Indienne', 'IN'),
    ('Italienne', 'IT'),
    ('Espagnole', 'ES'),
    ('Canadienne', 'CA'),
    ('Autre', 'XX');

-- Situations familiales
INSERT INTO situation_fam (libelle, code) VALUES
    ('CELIBATAIRE', 'C'),
    ('MARIE', 'M'),
    ('DIVORCE', 'D'),
    ('VEUF', 'V');

-- Types de visa
INSERT INTO type_visa (libelle, description) VALUES
    ('COURT_SEJOUR', 'Visa de court séjour'),
    ('LONG_SEJOUR', 'Visa de long séjour'),
    ('TRAVAIL', 'Visa travail'),
    ('ENTREPRENEUR', 'Visa entrepreneur'),
    ('ETUDES', 'Visa études');

-- Catégories de pièces
INSERT INTO categorie_piece (libelle, description) VALUES
    ('Identité', 'Documents d''identité'),
    ('Résidence', 'Certificats de résidence'),
    ('Professionnel', 'Documents professionnels'),
    ('Judiciaire', 'Extraits judiciaires'),
    ('Fiscal', 'Documents fiscaux'),
    ('Société', 'Documents de société');

-- Pièces rattachées aux catégories
INSERT INTO piece (id_categorie_piece, description) VALUES
    (1, 'Photos d''identité'),
    (1, 'Notice de renseignement'),
    (2, 'Demande au Ministre'),
    (1, 'Copie du visa'),
    (1, 'Copie du passeport'),
    (2, 'Copie carte de résident'),
    (2, 'Certificat de résidence'),
    (4, 'Casier judiciaire'),
    (6, 'Statuts de société'),
    (6, 'Extrait RC'),
    (5, 'Carte fiscale'),
    (3, 'Autorisation d''emploi'),
    (3, 'Attestation d''emploi');

-- Association pièces ↔ type de demande (TRANSFORMATION)
INSERT INTO piece_demande (id_type_dm, id_categorie_piece)
SELECT 1, id FROM categorie_piece;

-- =============================================================================
--  VUE UTILITAIRE : avancement d'un dossier
-- =============================================================================

CREATE OR REPLACE VIEW v_avancement_demande AS
SELECT
    d.id AS id_demande,
    dem.nom AS nom,
    dem.prenom AS prenom,
    dem.adresse_domicile,
    dem.profession,
    dem.nom_employeur,
    tv.libelle AS type_visa,
    td.nom AS type_demande,
    sd.status AS statut,
    d.type_visa AS visa_cible,
    d.created_at,
    d.updated_at,
    -- Calcul automatique de l'avancement
    (
      (d.a_fourni_photos::int)
    + (d.a_fourni_notice_renseignement::int)
    + (d.a_fourni_demande_ministre::int)
    + (d.a_fourni_copie_visa::int)
    + (d.a_fourni_copie_passeport::int)
    + (d.a_fourni_copie_carte_resident::int)
    + (d.a_fourni_certificat_residence::int)
    + (d.a_fourni_extrait_casier_judiciaire::int)
    + (d.a_fourni_statut_societe::int)
    + (d.a_fourni_extrait_rc::int)
    + (d.a_fourni_carte_fiscale::int)
    + (d.a_fourni_autorisation_emploi::int)
    + (d.a_fourni_attestation_emploi::int)
    ) AS nb_pieces_fournies,
    13 AS nb_pieces_total,
    ROUND(
      (
        (d.a_fourni_photos::int)
      + (d.a_fourni_notice_renseignement::int)
      + (d.a_fourni_demande_ministre::int)
      + (d.a_fourni_copie_visa::int)
      + (d.a_fourni_copie_passeport::int)
      + (d.a_fourni_copie_carte_resident::int)
      + (d.a_fourni_certificat_residence::int)
      + (d.a_fourni_extrait_casier_judiciaire::int)
      + (d.a_fourni_statut_societe::int)
      + (d.a_fourni_extrait_rc::int)
      + (d.a_fourni_carte_fiscale::int)
      + (d.a_fourni_autorisation_emploi::int)
      + (d.a_fourni_attestation_emploi::int)
      ) * 100.0 / 13, 1
    ) AS pct_avancement,
    -- Booléens individuels
    d.a_fourni_photos,
    d.a_fourni_notice_renseignement,
    d.a_fourni_demande_ministre,
    d.a_fourni_copie_visa,
    d.a_fourni_copie_passeport,
    d.a_fourni_copie_carte_resident,
    d.a_fourni_certificat_residence,
    d.a_fourni_extrait_casier_judiciaire,
    d.a_fourni_statut_societe,
    d.a_fourni_extrait_rc,
    d.a_fourni_carte_fiscale,
    d.a_fourni_autorisation_emploi,
    d.a_fourni_attestation_emploi
FROM demande d
JOIN demandeur dem ON dem.id = d.id_demandeur
JOIN status_dm sd ON sd.id = d.id_status_dm
JOIN type_demande td ON td.id = d.id_type_dm
LEFT JOIN visa v ON v.id_demandeur = dem.id
LEFT JOIN type_visa tv ON tv.id = v.id_type_visa;

-- =============================================================================
--  VÉRIFICATION DE COHÉRENCE (exécution optionnelle)
-- =============================================================================

SELECT
    'Schema validation' AS check_type,
    COUNT(*) AS table_count,
    'OK' AS status
FROM information_schema.tables
WHERE table_schema = 'public'
    AND table_type = 'BASE TABLE'
    AND table_name IN (
        'type_demande', 'status_dm', 'nationalite', 'situation_fam',
        'type_visa', 'categorie_piece', 'piece', 'demandeur',
        'etat_civil', 'passeport', 'visa', 'carte_resident',
        'visa_transformable', 'demande', 'visa_passeport', 'piece_demande'
    );

-- Vérifier que tous les ID sont BIGINT
SELECT
    t.table_name,
    c.column_name,
    c.data_type,
    CASE
        WHEN c.data_type = 'bigint' THEN '✓ OK'
        WHEN c.data_type IN ('serial', 'integer') THEN '✗ ERROR - Should be BIGINT'
        ELSE '? UNKNOWN'
    END AS validation
FROM information_schema.tables t
JOIN information_schema.columns c ON t.table_name = c.table_name
WHERE t.table_schema = 'public'
    AND c.column_name LIKE 'id%'
    AND c.column_name NOT LIKE 'id_%'
    AND t.table_type = 'BASE TABLE'
ORDER BY t.table_name, c.column_name;
