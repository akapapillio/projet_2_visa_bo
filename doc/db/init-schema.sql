-- Script de création complet de la base de données visa_db
-- PostgreSQL 12+

-- ============================================================================
-- TABLES DE RÉFÉRENCE (Listes statiques)
-- ============================================================================

-- Table: nationalite
CREATE TABLE IF NOT EXISTS nationalite (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);

-- Table: situation_fam
CREATE TABLE IF NOT EXISTS situation_fam (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL UNIQUE
);

-- Table: categorie_piece
CREATE TABLE IF NOT EXISTS categorie_piece (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL UNIQUE
);

-- Table: piece
CREATE TABLE IF NOT EXISTS piece (
    id_piece BIGSERIAL PRIMARY KEY,
    id_categorie_piece BIGINT NOT NULL,
    FOREIGN KEY (id_categorie_piece) REFERENCES categorie_piece(id)
);

-- Table: type_demande
CREATE TABLE IF NOT EXISTS type_demande (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);

-- Table: type_visa
CREATE TABLE IF NOT EXISTS type_visa (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL UNIQUE
);

-- Table: status_dm
CREATE TABLE IF NOT EXISTS status_dm (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL UNIQUE,
    observation TEXT
);

-- ============================================================================
-- TABLES PRINCIPALES
-- ============================================================================

-- Table: demandeur
CREATE TABLE IF NOT EXISTS demandeur (
    id_demandeur BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50),
    date_naissance DATE,
    lieu_naissance VARCHAR(50),
    id_piece BIGINT,
    id_situation_fam BIGINT,
    id_nationalite BIGINT,
    FOREIGN KEY (id_piece) REFERENCES piece(id_piece),
    FOREIGN KEY (id_situation_fam) REFERENCES situation_fam(id),
    FOREIGN KEY (id_nationalite) REFERENCES nationalite(id)
);

-- Table: passeport
CREATE TABLE IF NOT EXISTS passeport (
    id_passeport BIGSERIAL PRIMARY KEY,
    id_demandeur BIGINT NOT NULL,
    numero_passeport VARCHAR(100),
    date_delivrance DATE,
    date_expiration DATE,
    FOREIGN KEY (id_demandeur) REFERENCES demandeur(id_demandeur)
);

-- Table: etat_civil
CREATE TABLE IF NOT EXISTS etat_civil (
    id_etat_civil BIGSERIAL PRIMARY KEY,
    id_demandeur BIGINT NOT NULL UNIQUE,
    nom VARCHAR(255),
    prenoms VARCHAR(255),
    nom_jeune_fille VARCHAR(255),
    date_naissance DATE,
    situation_famille VARCHAR(255),
    nationalite VARCHAR(255),
    domicile_habituel VARCHAR(255),
    profession VARCHAR(255),
    employeur VARCHAR(255),
    adresse_employeur VARCHAR(255),
    FOREIGN KEY (id_demandeur) REFERENCES demandeur(id_demandeur)
);

-- Table: visa_transformable
CREATE TABLE IF NOT EXISTS visa_transformable (
    id BIGSERIAL PRIMARY KEY,
    num_visa VARCHAR(255),
    date_delivrance DATE,
    date_expiration DATE,
    id_passeport BIGINT NOT NULL,
    id_demandeur BIGINT NOT NULL,
    FOREIGN KEY (id_passeport) REFERENCES passeport(id_passeport),
    FOREIGN KEY (id_demandeur) REFERENCES demandeur(id_demandeur)
);

-- Table: demande (Demandes principales)
CREATE TABLE IF NOT EXISTS demande (
    id_demande BIGSERIAL PRIMARY KEY,
    id_demandeur BIGINT NOT NULL,
    id_type_demande BIGINT NOT NULL,
    id_status BIGINT NOT NULL,
    id_passeport BIGINT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_demandeur) REFERENCES demandeur(id_demandeur),
    FOREIGN KEY (id_type_demande) REFERENCES type_demande(id),
    FOREIGN KEY (id_status) REFERENCES status_dm(id),
    FOREIGN KEY (id_passeport) REFERENCES passeport(id_passeport)
);

-- Table: visa (Visas délivrés)
CREATE TABLE IF NOT EXISTS visa (
    id_visa BIGSERIAL PRIMARY KEY,
    numero_visa VARCHAR(100) UNIQUE,
    id_demandeur BIGINT NOT NULL,
    id_type_visa BIGINT NOT NULL,
    date_delivrance DATE,
    date_expiration DATE,
    FOREIGN KEY (id_demandeur) REFERENCES demandeur(id_demandeur),
    FOREIGN KEY (id_type_visa) REFERENCES type_visa(id)
);

-- Table: carte_resident
CREATE TABLE IF NOT EXISTS carte_resident (
    id_carte BIGSERIAL PRIMARY KEY,
    numero_carte VARCHAR(100) UNIQUE,
    id_demandeur BIGINT NOT NULL,
    date_delivrance DATE,
    date_expiration DATE,
    FOREIGN KEY (id_demandeur) REFERENCES demandeur(id_demandeur)
);

-- Table: piece_demande (Association: Pieces requises par type de demande)
CREATE TABLE IF NOT EXISTS piece_demande (
    id BIGSERIAL PRIMARY KEY,
    id_type_demande BIGINT NOT NULL,
    id_piece BIGINT NOT NULL,
    FOREIGN KEY (id_type_demande) REFERENCES type_demande(id),
    FOREIGN KEY (id_piece) REFERENCES piece(id_piece),
    UNIQUE(id_type_demande, id_piece)
);

-- ============================================================================
-- INDEX POUR PERFORMANCE
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_demandeur_nom ON demandeur(nom);
CREATE INDEX IF NOT EXISTS idx_demandeur_prenom ON demandeur(prenom);
CREATE INDEX IF NOT EXISTS idx_demande_demandeur ON demande(id_demandeur);
CREATE INDEX IF NOT EXISTS idx_demande_type ON demande(id_type_demande);
CREATE INDEX IF NOT EXISTS idx_demande_status ON demande(id_status);
CREATE INDEX IF NOT EXISTS idx_demande_date_creation ON demande(date_creation);
CREATE INDEX IF NOT EXISTS idx_visa_demandeur ON visa(id_demandeur);
CREATE INDEX IF NOT EXISTS idx_carte_resident_demandeur ON carte_resident(id_demandeur);
CREATE INDEX IF NOT EXISTS idx_etat_civil_demandeur ON etat_civil(id_demandeur);
CREATE INDEX IF NOT EXISTS idx_visa_transformable_demandeur ON visa_transformable(id_demandeur);
