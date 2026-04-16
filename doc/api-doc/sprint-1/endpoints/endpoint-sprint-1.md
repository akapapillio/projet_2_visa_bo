# API Documentation — Sprint 1
# Base URL : http://localhost:8080/api/visa

---

## Demande

| Méthode | Endpoint                         | Description                                    | Body (JSON)     |
|---------|----------------------------------|------------------------------------------------|-----------------|
| POST    | `/api/visa/demandes`             | Créer une nouvelle demande (EtatCivil + Visa)  | `DemandeDTO`    |
| GET     | `/api/visa/demandes`             | Lister toutes les demandes                     | —               |
| GET     | `/api/visa/demandes/{id}`        | Obtenir une demande par ID                     | —               |
| PUT     | `/api/visa/demandes/{id}`        | Modifier une demande existante                 | `DemandeDTO`    |
| DELETE  | `/api/visa/demandes/{id}`        | Supprimer une demande                          | —               |
| POST    | `/api/visa/demande_transformation` | **Alias rétro-compat FO** (≡ POST /demandes) | `DemandeDTO`    |

### DemandeDTO (body)
```json
{
  "typeDemande": "NOUVEAU_TITRE",
  "categorieVisa": "INVESTISSEUR",
  "lastName": "RAKOTO",
  "firstNames": "Jean Pierre",
  "maidenName": null,
  "birthDate": "1985-06-15",
  "maritalStatus": "MARIE",
  "nationality": "Malagasy",
  "homeAddress": "Lot IVK 123 Antananarivo",
  "occupation": "Directeur",
  "employerName": "SocGen Madagascar",
  "employerAddress": "Analakely, Antananarivo",
  "numeroVisaPrcd": "VIS-2022-001",
  "dateDelivranceVisaPrcd": "2022-01-10",
  "dateExpirationVisaPrcd": "2024-01-10",
  "aFourniPhotos": true,
  "aFourniNoticeRenseignement": true,
  "aFourniDemandeMinistre": false,
  "aFourniCopieVisa": true,
  "aFourniCopiePasseport": true,
  "aFourniCopieCarteResident": false,
  "aFourniCertificatResidence": false,
  "aFourniExtraitCasierJudiciaire": true,
  "aFourniStatutSociete": true,
  "aFourniExtraitRC": true,
  "aFourniCarteFiscale": true,
  "aFourniAutorisationEmploi": false,
  "aFourniAttestationEmploi": false
}
```

---

## EtatCivil

| Méthode | Endpoint                       | Description                    | Body (JSON)    |
|---------|--------------------------------|--------------------------------|----------------|
| POST    | `/api/visa/etat-civil`         | Créer un état civil            | `EtatCivilDTO` |
| GET     | `/api/visa/etat-civil`         | Lister tous les états civils   | —              |
| GET     | `/api/visa/etat-civil/{id}`    | Obtenir un état civil par ID   | —              |
| PUT     | `/api/visa/etat-civil/{id}`    | Modifier un état civil         | `EtatCivilDTO` |
| DELETE  | `/api/visa/etat-civil/{id}`    | Supprimer un état civil        | —              |

### EtatCivilDTO (body)
```json
{
  "nom": "RAKOTO",
  "prenoms": "Jean Pierre",
  "nomJeuneFille": null,
  "dateNaissance": "1985-06-15",
  "situationFamille": "MARIE",
  "nationalite": "Malagasy",
  "domicileHabituel": "Lot IVK 123 Antananarivo",
  "profession": "Directeur",
  "employeur": "SocGen Madagascar",
  "adresseEmployeur": "Analakely, Antananarivo"
}
```

---

## VisaTransformable

| Méthode | Endpoint                              | Description                         | Body (JSON)            |
|---------|---------------------------------------|-------------------------------------|------------------------|
| POST    | `/api/visa/visa-transformable`        | Enregistrer un visa précédent       | `VisaTransformableDTO` |
| GET     | `/api/visa/visa-transformable`        | Lister tous les visas précédents    | —                      |
| GET     | `/api/visa/visa-transformable/{id}`   | Obtenir un visa précédent par ID    | —                      |
| PUT     | `/api/visa/visa-transformable/{id}`   | Modifier un visa précédent          | `VisaTransformableDTO` |
| DELETE  | `/api/visa/visa-transformable/{id}`   | Supprimer un visa précédent         | —                      |

### VisaTransformableDTO (body)
```json
{
  "numeroVisa": "VIS-2022-001",
  "dateDelivrance": "2022-01-10",
  "dateExpiration": "2024-01-10"
}
```

---

## Codes HTTP retournés

| Code | Signification                         |
|------|---------------------------------------|
| 200  | OK — succès GET / PUT                 |
| 201  | Created — succès POST                 |
| 204  | No Content — succès DELETE            |
| 404  | Not Found — ressource inexistante     |
| 500  | Internal Server Error — erreur serveur|

---

## TypeDemande (enum)
- `NOUVEAU_TITRE`
- `RENOUVELLEMENT`
- `DUPLICATA`
- `TRANSFERT_VISA`

## CategorieVisa (enum)
- `INVESTISSEUR`
- `TRAVAILLEUR`
- `ETUDIANT`
- `MISSIONNAIRE`
