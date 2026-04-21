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
- [ ] conception  (en atente d une restructuration et refractor back pour)
- [ ] données de test 
- [ ] 1er simulation sql sprint 2
- [ ] convention
- [ ] structure git
- [ ] en staging , restructuration , refractor back pour (code first ,orm first => Hybrid approach)

- [ ]GitFlow
    - [ ] Creation de la branche : sprint-2 (coté FO ET BO)
    - [ ] Merge sprint-2 (supp sprint 2) / Creation de STAGING-sprint-2
    - [ ] Creation de release ()
    - [ ] déploiement



# TO-DO BO
- [ ] crud  pour les dossier (decentraliser le dossier) rendre independant des demande (donc il peut y avoir des demandeur sans demande pour resumer consepte customer existant)
- [ ] verificateur by demande (faire un dm déclanche un verificateur pour avoir le constat dans le status ex dossier cree (constat dans la demande manque de photo , etc )
- [ ] 



# TO-DO FO
- [ ] faire une formulaire pour les crud pour les dossier decentralisé (consomation d api)
- [ ] lister les demande ou metre les demande dans des tableau (mettre en evidence les constat ) (rappelle constat correspondant au demande)
