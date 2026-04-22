-- Sprint 2 : Éviter les collisions d'ID avec les données historiques masquées
-- On redémarre les séquences à 10 000 pour s'assurer que les nouvelles entrées ne touchent pas aux anciens IDs.

ALTER SEQUENCE demandes_id_seq RESTART WITH 10000;
ALTER SEQUENCE etat_civil_id_seq RESTART WITH 10000;
ALTER SEQUENCE visa_transformable_id_seq RESTART WITH 10000;
