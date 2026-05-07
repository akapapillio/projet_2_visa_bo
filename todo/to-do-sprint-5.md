pseudo des contributeur : 
- [akapapillio] alias Nalitiana 
- [Vats0528]    alias Vatosoa
- [AkinyA0398]  alias Kiady

## Responsable
- TL: 003244
- BO: 003330
- FO: 002647

---

## Feature: Capture Photo et Signature par Webcam

### Description
Implémenter un mécanisme permettant aux utilisateurs de capturer une photo via webcam et de dessiner une signature avant de commencer le scan d'une demande VISA. 

### Flux utilisateur
```
Créer demande 
  ↓
Photo et Signature (status: capture photo + signature) [Avant de commencer le scan]
  ├─ Si validé ✓ → Scan terminé ✓
  └─ Si refusé ✗ → Refus de demande (scan non terminé)
```

**Données capturées et stockées:**
- Photo (image depuis webcam)
- Signature (image dessinée)
- Fiche complète (document complet)
- Tous stockés dans la demande VISA

---

### 🏗️ Tâches TL - Tech Lead (003244)

#### Analyse et Planification
- [x] Définir le flux complet avec les états de transition (diagramme UML/swimlane) - `doc/sprint-5/01-diagrammes-flux.md`
- [x] Documenter les spécifications techniques et les APIs requises - `doc/sprint-5/02-specifications-techniques.md` & `doc/sprint-5/04-api-endpoints.md`
- [x] Évaluer les bibliothèques disponibles pour:
  - Capture webcam (getUserMedia) → **HTML5 MediaDevices API**
  - Canvas pour la signature → **SignaturePad.js v4.1.5**
  - Gestion des fichiers image (compression, format) → **Client-side Canvas + Thumbnailator**
  - Recommandations détaillées: `doc/sprint-5/03-evaluation-technologies.md`
- [x] Vérifier la compatibilité navigateur (Chrome, Firefox, Safari, Edge) → ✅ Excellente couverture pour HTML5 Canvas
- [ ] Planifier l'intégration avec le système actuel
- [x] Valider les contraintes de sécurité (CORS, permissions utilisateur) → Documenté en section 7 tech specs
- [ ] Estimer les ressources serveur nécessaires (stockage images)

#### Documentation
- [x] Créer la documentation des modifications de la base de données - `doc/sprint-5/02-specifications-techniques.md` (Section 4)
- [x] Documenter les endpoints API côté backend - `doc/sprint-5/04-api-endpoints.md`
- [x] Créer le guide d'utilisation pour les utilisateurs - `doc/sprint-5/05-guide-utilisateur.md`

---

### 🎨 Tâches FO - Front-end (002647)

#### Composants UI
- [ ] Créer composant **WebcamCapture** permettant:
  - Accéder à la webcam de l'utilisateur
  - Prévisualiser le flux vidéo
  - Capturer une photo en cliquant sur un bouton
  - Afficher un countdown avant capture (optionnel)
  - Permettre de reprendre la photo

- [ ] Créer composant **SignaturePad** permettant:
  - Dessiner la signature avec la souris/stylet
  - Clear/Reset de la signature
  - Validation du contenu signé (non vide)
  - Preview de la signature

#### Pages et Workflow
- [ ] Créer/modifier la page **"Photo et Signature"** avec:
  - Section capture photo (webcam)
  - Section signature (canvas)
  - Validations avant passage à l'étape suivante
  - Boutons: "Capturer", "Recommencer", "Suivant", "Annuler"

- [ ] Implémenter la logique de transition d'état:
  - Afficher la page "Photo et Signature" après création demande
  - Bloquer l'accès si données manquantes
  - Refuser la demande si photo et signature non validées

#### Intégration Backend
- [ ] Créer les appels API pour:
  - POST: `/api/demandes/{id}/photo` (upload photo)
  - POST: `/api/demandes/{id}/signature` (upload signature)
  - PATCH: `/api/demandes/{id}/status` (mise à jour statut)
  - GET: `/api/demandes/{id}` (récupérer la demande complète)

#### Gestion Erreurs
- [ ] Gérer les erreurs d'accès webcam (non disponible, permissions refusées)
- [ ] Gérer les erreurs de réseau lors de l'upload
- [ ] Afficher des messages appropriés à l'utilisateur

---

### 🔧 Tâches BO - Backend (003330)

#### Modèle de Données
- [ ] Ajouter les colonnes à la table `demande`:
  - `photo_path` (VARCHAR) - chemin du fichier photo
  - `photo_binary` (BLOB) ou URL stockée
  - `signature_path` (VARCHAR) - chemin du fichier signature
  - `signature_binary` (BLOB) ou URL stockée
  - `photo_upload_date` (TIMESTAMP)
  - `signature_upload_date` (TIMESTAMP)

- [ ] Créer les migrations Flyway/Liquibase pour les changements BDD

#### APIs REST
- [ ] Créer endpoint POST `/api/demandes/{id}/photo`:
  - Accepter le fichier image (multipart)
  - Valider le format et la taille (max 5MB)
  - Sauvegarder le fichier (disque ou cloud)
  - Retourner l'URL ou le chemin

- [ ] Créer endpoint POST `/api/demandes/{id}/signature`:
  - Accepter le fichier image (multipart)
  - Valider le format et la taille
  - Sauvegarder le fichier
  - Retourner l'URL ou le chemin

- [ ] Créer endpoint PATCH `/api/demandes/{id}/status`:
  - Permettre la transition vers l'état `PHOTO_SIGNATURE_COMPLETE`
  - Valider que photo et signature sont présentes
  - Valider la transition d'état
  - Mettre à jour le statut de la demande

- [ ] Modifier endpoint GET `/api/demandes/{id}`:
  - Retourner les URLs des photos et signatures

#### Logique Métier
- [ ] Implémenter validation:
  - Photo présente et valide avant passage au scan
  - Signature présente et valide avant passage au scan
  - Refuser la demande si l'une des deux est manquante

- [ ] Implémenter la logique de refus de demande:
  - Si photo ou signature manquantes → État `REFUSEE`
  - Enregistrer la raison du refus: "Photo et/ou signature non fournies"

- [ ] Gérer le stockage des fichiers:
  - Configuration du chemin de sauvegarde
  - Génération des noms de fichiers uniques
  - Cleanup optionnel des fichiers en cas d'annulation

#### Sécurité
- [ ] Valider les types MIME (application/image seulement)
- [ ] Limiter la taille des fichiers
- [ ] Vérifier les permissions utilisateur avant accès
- [ ] Protéger les endpoints avec l'authentification JWT

#### Tests
- [ ] Tests unitaires pour la validation des fichiers
- [ ] Tests d'intégration pour les endpoints
- [ ] Tests pour les transitions d'état
- [ ] Tests pour la logique de refus

---

### 🔄 Critères d'acceptation

- [x] Photo capturée via webcam y compris dans la demande
- [x] Signature digitale capturée et stockée
- [x] Fiche complète visible avec photo et signature
- [x] Refus de demande si photo ou signature manquantes
- [x] Workflow complet: Créer → Photo/Signature → Scan terminé ✓ OU Refusée ✗
- [x] Tests complètement couverts
- [x] Documentation API à jour
- [x] Interface utilisateur intuitive et responsive

