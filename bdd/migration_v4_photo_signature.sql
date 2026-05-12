-- Migration: Ajout des colonnes photo, signature et raison de refus à la table demande
-- Sprint 5 - Gestion Photo & Signature

-- Colonnes photo
ALTER TABLE demande ADD COLUMN IF NOT EXISTS photo_path VARCHAR(500);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS photo_url VARCHAR(1000);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS photo_upload_date TIMESTAMP;

-- Colonnes signature
ALTER TABLE demande ADD COLUMN IF NOT EXISTS signature_path VARCHAR(500);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS signature_url VARCHAR(1000);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS signature_upload_date TIMESTAMP;

-- Raison de refus
ALTER TABLE demande ADD COLUMN IF NOT EXISTS raison_refus VARCHAR(1000);

-- Ajout du statut PHOTO_SIGNATURE_COMPLETE s'il n'existe pas
INSERT INTO status_dm (status_dm, observation)
SELECT 'PHOTO_SIGNATURE_COMPLETE', 'Photo et signature fournies'
WHERE NOT EXISTS (SELECT 1 FROM status_dm WHERE status_dm = 'PHOTO_SIGNATURE_COMPLETE');

-- Ajout du statut REFUSEE s'il n'existe pas
INSERT INTO status_dm (status_dm, observation)
SELECT 'REFUSEE', 'Demande refusée'
WHERE NOT EXISTS (SELECT 1 FROM status_dm WHERE status_dm = 'REFUSEE');
