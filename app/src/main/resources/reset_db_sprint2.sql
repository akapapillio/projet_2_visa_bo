-- Script de nettoyage pour transition Sprint 2
-- Supprime les tables qui ont changé de structure pour permettre à Hibernate de les recréer proprement.

DROP TABLE IF EXISTS visa_transformable CASCADE;
DROP TABLE IF EXISTS etat_civil CASCADE;
DROP TABLE IF EXISTS demande CASCADE;
DROP TABLE IF EXISTS visa_passeport CASCADE;
DROP TABLE IF EXISTS piece_demande CASCADE;
DROP TABLE IF EXISTS passeport CASCADE;
DROP TABLE IF EXISTS visa CASCADE;
DROP TABLE IF EXISTS piece CASCADE;
DROP TABLE IF EXISTS demandeur CASCADE;

-- Nettoyage des tables de référence si nécessaire (Hibernate les repeupler via DataInitializer)
DROP TABLE IF EXISTS status_dm CASCADE;
DROP TABLE IF EXISTS type_demande CASCADE;
DROP TABLE IF EXISTS nationalite CASCADE;
DROP TABLE IF EXISTS situation_fam CASCADE;
DROP TABLE IF EXISTS type_visa CASCADE;
DROP TABLE IF EXISTS categorie_piece CASCADE;
