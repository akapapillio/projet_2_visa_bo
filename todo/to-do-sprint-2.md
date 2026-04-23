pseudo des contributeur : 
- [akapapillio] alias Nalitiana
- [Vats0528]    alias Vatosoa
- [AkinyA0398]  alias Kiady

# sprint-2: enregistrement (saisi) pour des demandeur sans donnee interieur [toute_sorte_de_dm]
- ( exemple de utilité , un nouveau demandeur avec visa non transformable non expiré peut faire leur carte de residence )

## Responsable
- TL: 002647
- BO: 003244
- FO: 003330

## tâches communes : 

- [ ] simulation en env dev (staging)
- [ ] tests finaux pour le sprint 2 (release)

# TO-DO TL :
- [x] conception  (en atente d une restructuration et refractor back pour)
- [x] données de test 
- [x] 1er simulation sql sprint 2
- [x] structure git
- [x] en staging , restructuration , refractor back pour (code first ,orm first => Hybrid approach)

- [ ]GitFlow
    - [ ] Creation de la branche : sprint-2 (coté FO ET BO)
    - [ ] Merge sprint-2 (supp sprint 2) / Creation de STAGING-sprint-2
    - [ ] Creation de release ()
    - [ ] déploiement



# TO-DO BO
- [x] Désactiver les éventuels outils ou endpoints de récupération d'anciennes données pour auto-complétion.
- [x] Modifier les traitements pour accepter un duplicata / un visa transformable comme un **nouvel enregistrement** complet.
- [x] Vérifier le pipeline de sauvegarde (Back-end) pour s'assurer d'aucune collision avec des IDs bloqués ou masqués.
- [x] Pièce justificatif (Modélisé via CategoriePiece et Piece).
- [x] Type de demandes (recupération, duplicata, travailleur, investisseur, transformable).
- [x] crud pour les dossier (decentraliser le dossier) rendre independant des demandes (concept client existant)
    - [x] choix de demandeur (idDemandeur dans le DTO)
- [x] verificateur by demande (déclanché à la création avec constat dans l'observation)



# TO-DO FO :
- [x] formulaire (demande-visa-long-sejour.html)
  - [x] Phase 1 : Fix field mapping (categorieVisa → typeVisa)
  - [x] Phase 2 : Ajout des champs manquants (maidenName, maritalStatus, etc.)
  - [x] Phase 5 : Lien de navigation vers accueil + listing

- [x] gestion-demandes.html
  - [x] Phase 3 : Fonctionnalité edit/update complète
    - [x] Fix bug switchTab (sans event)
    - [x] Fix mapping retour API (demandeur.nom, dateNaissance array, createdAt)
    - [x] Harmonisation CSS status badges (status-documents-manquants)
    - [x] Ajout formatStatus()
    - [x] Auto-chargement depuis URL ?edit=ID
  - [x] Phase 5 : Lien de navigation vers listing-demandes

- [x] listing-demandes.html
  - [x] Phase 4 : Bouton "Modifier" (✏️) par ligne → redirect vers gestion-demandes.html?edit=ID
  - [x] Phase 5 : Fix date (createdAt), formatDate() pour LocalDate array, lien vers gestion-demandes


