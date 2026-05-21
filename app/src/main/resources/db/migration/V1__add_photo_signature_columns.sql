-- V1: Ajouter les colonnes photo et signature à la table demande
ALTER TABLE demande ADD COLUMN IF NOT EXISTS photo_path VARCHAR(500);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS photo_url VARCHAR(1000);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS signature_path VARCHAR(500);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS signature_url VARCHAR(1000);
ALTER TABLE demande ADD COLUMN IF NOT EXISTS photo_upload_date TIMESTAMP;
ALTER TABLE demande ADD COLUMN IF NOT EXISTS signature_upload_date TIMESTAMP;
ALTER TABLE demande ADD COLUMN IF NOT EXISTS raison_refus VARCHAR(1000);
