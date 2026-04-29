-- Migration: Ajouter la colonne lieu_naissance à la table etat_civil
ALTER TABLE etat_civil ADD COLUMN lieu_naissance VARCHAR(100);
