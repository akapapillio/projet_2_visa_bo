-- V2: Ajouter les statuts liés au workflow photo/signature

-- Statut PHOTO_SIGNATURE_COMPLETE
INSERT INTO status_dm (status_dm, observation)
SELECT 'PHOTO_SIGNATURE_COMPLETE', 'Photo et signature fournies'
WHERE NOT EXISTS (SELECT 1 FROM status_dm WHERE status_dm = 'PHOTO_SIGNATURE_COMPLETE');

-- Statut REFUSEE
INSERT INTO status_dm (status_dm, observation)
SELECT 'REFUSEE', 'Demande refusée'
WHERE NOT EXISTS (SELECT 1 FROM status_dm WHERE status_dm = 'REFUSEE');
