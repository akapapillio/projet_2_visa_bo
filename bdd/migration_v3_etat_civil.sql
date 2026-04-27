BEGIN;

-- 1. Ajouter la colonne id_etat_civil dans demandeur
ALTER TABLE demandeur ADD COLUMN id_etat_civil BIGINT;

-- 2. Mettre à jour demandeur.id_etat_civil avec les etat_civil existants
UPDATE demandeur d
SET id_etat_civil = ec.id_etat_civil
FROM etat_civil ec
WHERE ec.id_demandeur = d.id_demandeur;

-- 3. Supprimer la FK ancienne etat_civil.id_demandeur
ALTER TABLE etat_civil DROP CONSTRAINT IF EXISTS fk_etat_civil_demandeur;

-- 4. Supprimer l'unique constraint sur etat_civil.id_demandeur
ALTER TABLE etat_civil DROP CONSTRAINT IF EXISTS etat_civil_id_demandeur_key;

-- 5. Supprimer la colonne etat_civil.id_demandeur
ALTER TABLE etat_civil DROP COLUMN id_demandeur CASCADE;

-- 6. Ajouter la nouvelle FK demandeur.id_etat_civil
ALTER TABLE demandeur
ADD CONSTRAINT fk_demandeur_etat_civil
FOREIGN KEY (id_etat_civil) REFERENCES etat_civil(id_etat_civil) ON DELETE CASCADE;

COMMIT;