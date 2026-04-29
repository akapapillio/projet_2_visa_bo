CREATE TABLE type_demande(
   id_type_dm COUNTER,
   nom_type_dm VARCHAR(50),
   PRIMARY KEY(id_type_dm)
);

CREATE TABLE status_dm(
   id_status_dm COUNTER,
   status_dm VARCHAR(50),
   observation VARCHAR(250),
   PRIMARY KEY(id_status_dm)
);

CREATE TABLE nationalite(
   id_nationalite COUNTER,
   nationalite VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_nationalite)
);

CREATE TABLE situation_fam(
   id_situation_fam COUNTER,
   situation_fam VARCHAR(50),
   PRIMARY KEY(id_situation_fam)
);

CREATE TABLE type_visa(
   id_type_visa COUNTER,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_type_visa)
);

CREATE TABLE categorie_piece(
   id_categorie_piece COUNTER,
   libelle VARCHAR(50),
   PRIMARY KEY(id_categorie_piece)
);

CREATE TABLE piece(
   id_piece COUNTER,
   id_categorie_piece INT NOT NULL,
   PRIMARY KEY(id_piece),
   FOREIGN KEY(id_categorie_piece) REFERENCES categorie_piece(id_categorie_piece)
);

CREATE TABLE demandeur(
   id_demandeur COUNTER,
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50),
   date_naissance DATE,
   lieu_naissance VARCHAR(50),
   id_piece INT NOT NULL,
   id_situation_fam INT NOT NULL,
   id_nationalite INT NOT NULL,
   PRIMARY KEY(id_demandeur),
   FOREIGN KEY(id_piece) REFERENCES piece(id_piece),
   FOREIGN KEY(id_situation_fam) REFERENCES situation_fam(id_situation_fam),
   FOREIGN KEY(id_nationalite) REFERENCES nationalite(id_nationalite)
);

CREATE TABLE etat_civil(
   id_etat_civil COUNTER,
   id_demandeur INT NOT NULL,
   PRIMARY KEY(id_etat_civil),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE visa(
   id_visa COUNTER,
   date_expiration DATE,
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50),
   reference VARCHAR(50),
   num_visa VARCHAR(50),
   date_delivrance DATE,
   date_modification VARCHAR(50),
   id_type_visa INT NOT NULL,
   id_demandeur INT NOT NULL,
   PRIMARY KEY(id_visa),
   FOREIGN KEY(id_type_visa) REFERENCES type_visa(id_type_visa),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE passeport(
   id_passeport COUNTER,
   num_passeport INT,
   date_expiration DATE,
   date_delivrance VARCHAR(50),
   id_demandeur INT NOT NULL,
   PRIMARY KEY(id_passeport),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE visa_transformable(
   id_visa_transformable COUNTER,
   num_visa VARCHAR(50),
   id_passeport INT NOT NULL,
   id_demandeur INT NOT NULL,
   PRIMARY KEY(id_visa_transformable),
   FOREIGN KEY(id_passeport) REFERENCES passeport(id_passeport),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE carte_resident(
   id_carte_resident COUNTER,
   num INT,
   id_demandeur INT NOT NULL,
   PRIMARY KEY(id_carte_resident),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur)
);

CREATE TABLE demande(
   id_demande COUNTER,
   created_at DATE,
   updated_at DATE,
   id_demandeur INT NOT NULL,
   id_status_dm INT NOT NULL,
   id_type_dm INT NOT NULL,
   PRIMARY KEY(id_demande),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id_demandeur),
   FOREIGN KEY(id_status_dm) REFERENCES status_dm(id_status_dm),
   FOREIGN KEY(id_type_dm) REFERENCES type_demande(id_type_dm)
);

CREATE TABLE visa_passeport(
   id_visa INT,
   id_passeport INT,
   status_liaison VARCHAR(50),
   PRIMARY KEY(id_visa, id_passeport),
   FOREIGN KEY(id_visa) REFERENCES visa(id_visa),
   FOREIGN KEY(id_passeport) REFERENCES passeport(id_passeport)
);

CREATE TABLE piece_demande(
   id_type_dm INT,
   id_categorie_piece INT,
   PRIMARY KEY(id_type_dm, id_categorie_piece),
   FOREIGN KEY(id_type_dm) REFERENCES type_demande(id_type_dm),
   FOREIGN KEY(id_categorie_piece) REFERENCES categorie_piece(id_categorie_piece)
);
