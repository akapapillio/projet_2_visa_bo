CREATE TABLE type_demande(
   id_type_dm SERIAL,
   nom_type_dm VARCHAR(50) ,
   PRIMARY KEY(id_type_dm)
);

CREATE TABLE data_identification(
   id_data_identification SERIAL,
   PRIMARY KEY(id_data_identification)
);

CREATE TABLE status_dm(
   id_status_dm SERIAL,
   status_dm VARCHAR(50) ,
   PRIMARY KEY(id_status_dm)
);

CREATE TABLE dm(
   id_dm SERIAL,
   last_names VARCHAR(50) ,
   first_name VARCHAR(50) ,
   maiden_name VARCHAR(50) ,
   birth_date DATE,
   marital_status VARCHAR(50) ,
   nationality VARCHAR(50) ,
   home_address VARCHAR(50) ,
   occupation VARCHAR(50) ,
   employer_name VARCHAR(50) ,
   employer_address VARCHAR(50) ,
   id_status_dm INTEGER NOT NULL,
   id_type_dm INTEGER NOT NULL,
   PRIMARY KEY(id_dm),
   FOREIGN KEY(id_status_dm) REFERENCES status_dm(id_status_dm),
   FOREIGN KEY(id_type_dm) REFERENCES type_demande(id_type_dm)
);
