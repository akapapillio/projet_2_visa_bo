CREATE TABLE type_demande(
   id_type_dm SERIAL,
   nom_type_dm VARCHAR(50) ,
   PRIMARY KEY(id_type_dm)
);

CREATE TABLE status_dm(
   id_status_dm SERIAL,
   status_dm VARCHAR(50) ,
   observation VARCHAR(250) ,
   PRIMARY KEY(id_status_dm)
);

CREATE TABLE nationalite(
   id_nationalite SERIAL,
   nationalite VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_nationalite)
);

CREATE TABLE situation_fam(
   id_situation_fam SERIAL,
   situation_fam VARCHAR(50) ,
   PRIMARY KEY(id_situation_fam)
);

CREATE TABLE type_visa(
   id_type_visa SERIAL,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type_visa)
);

CREATE TABLE categorie_piece(
   id_categorie_piece SERIAL,
   libelle VARCHAR(50) ,
   PRIMARY KEY(id_categorie_piece)
);

CREATE TABLE type_objet(
   id_type_objet SERIAL,
   nom_objet VARCHAR(50) ,
   PRIMARY KEY(id_type_objet)
);

CREATE TABLE demandeur(
   id_demandeur SERIAL,
   nom VARCHAR(50)  NOT NULL,
   prenom VARCHAR(50) ,
   date_naissance DATE,
   lieu_naissance VARCHAR(50) ,
   id_situation_fam INTEGER NOT NULL,
   id_nationalite INTEGER NOT NULL,
   PRIMARY KEY(id_demandeur),
   FOREIGN KEY(id_situation_fam) REFERENCES situation_fam(id_situation_fam),
   FOREIGN KEY(id_nationalite) REFERENCES nationalite(id_nationalite)
);

CREATE TABLE etat_civil(
   id_etat_civil SERIAL,
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id_etat_civil),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE visa(
   id_visa SERIAL,
   date_expiration DATE,
   nom VARCHAR(50)  NOT NULL,
   prenom VARCHAR(50) ,
   reference VARCHAR(50) ,
   num_visa VARCHAR(50) ,
   date_delivrance DATE,
   date_modification VARCHAR(50) ,
   id_type_visa INTEGER NOT NULL,
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id_visa),
   FOREIGN KEY(id_type_visa) REFERENCES type_visa(id_type_visa),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE passeport(
   id_passeport SERIAL,
   num_passeport INTEGER,
   date_expiration DATE,
   date_delivrance VARCHAR(50) ,
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id_passeport),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE visa_transformable(
   id_visa_transformable SERIAL,
   date_expiration DATE,
   num_visa VARCHAR(50) ,
   date_delivrance VARCHAR(50) ,
   id_passeport INTEGER NOT NULL,
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id_visa_transformable),
   FOREIGN KEY(id_passeport) REFERENCES passeport(id_passeport),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE carte_resident(
   id_carte_resident SERIAL,
   num INTEGER,
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id_carte_resident),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE piece(
   id_piece SERIAL,
   fichier_path VARCHAR(250) ,
   date_upload DATE,
   valide BOOLEAN,
   id_demandeur INTEGER NOT NULL,
   id_categorie_piece INTEGER NOT NULL,
   PRIMARY KEY(id_piece),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur),
   FOREIGN KEY(id_categorie_piece) REFERENCES categorie_piece(id_categorie_piece)
);

CREATE TABLE demande(
   id_demande SERIAL,
   created_at DATE,
   updated_at DATE,
   id_demandeur INTEGER NOT NULL,
   id_status_dm INTEGER NOT NULL,
   id_type_dm INTEGER NOT NULL,
   PRIMARY KEY(id_demande),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur),
   FOREIGN KEY(id_status_dm) REFERENCES status_dm(id_status_dm),
   FOREIGN KEY(id_type_dm) REFERENCES type_demande(id_type_dm)
);

CREATE TABLE objet_utilise_demande(
   id_objet_utilise_demande SERIAL,
   id_reference INTEGER,
   table_source VARCHAR(50) ,
   id_type_objet INTEGER NOT NULL,
   id_demande INTEGER NOT NULL,
   PRIMARY KEY(id_objet_utilise_demande),
   FOREIGN KEY(id_type_objet) REFERENCES type_objet(id_type_objet),
   FOREIGN KEY(id_demande) REFERENCES demande(id_demande)
);

CREATE TABLE visa_passeport(
   id_visa INTEGER,
   id_passeport INTEGER,
   status_liaison VARCHAR(50) ,
   PRIMARY KEY(id_visa, id_passeport),
   FOREIGN KEY(id_visa) REFERENCES visa(id_visa),
   FOREIGN KEY(id_passeport) REFERENCES passeport(id_passeport)
);

CREATE TABLE piece_demande(
   id_type_dm INTEGER,
   id_categorie_piece INTEGER,
   PRIMARY KEY(id_type_dm, id_categorie_piece),
   FOREIGN KEY(id_type_dm) REFERENCES type_demande(id_type_dm),
   FOREIGN KEY(id_categorie_piece) REFERENCES categorie_piece(id_categorie_piece)
);

CREATE TABLE piece_utilisee_demande(
   id_demande INTEGER,
   id_piece INTEGER,
   date_validation DATE,
   PRIMARY KEY(id_demande, id_piece),
   FOREIGN KEY(id_demande) REFERENCES demande(id_demande),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece)
);

CREATE TABLE type_demande_objet_metier_obligatoire(
   id_type_dm INTEGER,
   id_type_objet INTEGER,
   obligatoire BOOLEAN,
   PRIMARY KEY(id_type_dm, id_type_objet),
   FOREIGN KEY(id_type_dm) REFERENCES type_demande(id_type_dm),
   FOREIGN KEY(id_type_objet) REFERENCES type_objet(id_type_objet)
);
