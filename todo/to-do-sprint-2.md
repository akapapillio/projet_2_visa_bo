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



# TO-DO FO
- [ ] faire une formulaire pour les crud pour les dossier decentralisé (consomation d api)
- [ ] lister les demande ou metre les demande dans des tableau (mettre en evidence les constat ) (rappelle constat correspondant au demande)
