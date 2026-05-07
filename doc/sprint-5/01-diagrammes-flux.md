# Diagrammes de Flux - Capture Photo et Signature

## 1. Swimlane Diagram - Workflow Principal

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ UTILISATEUR           │ FRONTEND              │ BACKEND                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                       │                       │                             │
│ 1. Crée demande ──────────────────────────────────────────────┐             │
│                       │                       │            ✓ POST /demandes│
│                       │◄──────────────────────────────────────┤             │
│                       │ demande_id, status:   │  Demande créée (DRAFT)     │
│                       │ DRAFT                 │                             │
│                       │                       │                             │
│ 2. Accède Photo/Sig◄──│ GET /demandes/{id}    │                             │
│    page              │                       │                             │
│                       │                       │──────────────────────────┐  │
│                       │◄──────── Redirection Photo/Signature page ────────┤  │
│                       │                       │                          │  │
│ 3. Autorise webcam───│ requestUserMedia()     │                          │  │
│    Capture photo     │ displayPreview()       │                          │  │
│                       │                       │                          │  │
│ 4. Upload photo ─────│ POST /demandes/{id}/   │                          │  │
│                       │         photo         │                          │  │
│                       │                       │──────────────────────────┘  │
│                       │                       │ Sauvegarde photo            │
│                       │◄──────────────────────│ Retour: photo_url           │
│                       │ { photo_url }         │                             │
│                       │                       │                             │
│ 5. Signe ────────────│ drawSignature()        │                             │
│    (sur canvas)      │ validateSignature()    │                             │
│                       │                       │                             │
│ 6. Upload signature ─│ POST /demandes/{id}/   │                             │
│                       │       signature       │                             │
│                       │                       │──────────────────────────┐  │
│                       │                       │ Sauvegarde signature      │  │
│                       │◄──────────────────────│ Retour: signature_url    │  │
│                       │ { signature_url }     │                          │  │
│                       │                       │                          │  │
│ 7. Clique "Suivant"──│ PATCH /demandes/{id}/ │                          │  │
│                       │        status         │                          │  │
│                       │ (status:              │                          │  │
│                       │ PHOTO_SIG_COMPLETE)   │                          │  │
│                       │                       │──────────────────────────┘  │
│                       │                       │ Validation:                 │
│                       │                       │ - photo présente ✓          │
│                       │                       │ - signature présente ✓      │
│                       │                       │ - Mise à jour status        │
│                       │◄──────────────────────│ { status: SCAN_READY }      │
│                       │                       │                             │
│ 8. Redirige vers ────│ Scan page              │                             │
│    Scan complète     │                        │                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 2. State Machine Diagram - États de la Demande

```
                    ┌─────────────────────────────────┐
                    │    DRAFT (Créée)                │
                    │  photo: null                    │
                    │  signature: null                │
                    └────────────┬────────────────────┘
                                 │
                    Utilisateur accède    
                    à Photo/Signature page
                                 │
                                 ▼
                    ┌─────────────────────────────────┐
         ┌──────────│ CAPTURING_MEDIA                 │◄────────────┐
         │          │  photo: null                    │             │
         │          │  signature: null                │             │
         │          └────────────┬────────────────────┘             │
         │                       │                            User clique
         │                       │ Photo capturée + uploadée   "Recommencer"
         │                       ▼                                │
         │          ┌─────────────────────────────────┐           │
         │   ┌─────►│ PHOTO_CAPTURED                  │───────────┘
         │   │      │  photo: URL presente            │
         │   │      │  signature: null                │
         │   │      └────────────┬────────────────────┘
         │   │                   │
         │   │        Signature dessinée + uploadée
         │   │                   │
         │   │                   ▼
         │   │      ┌─────────────────────────────────┐
         │   │      │ PHOTO_SIGNATURE_COMPLETE        │
         │   │      │  photo: URL presente            │
         │   │      │  signature: URL presente        │
         │   │      └────────────┬────────────────────┘
         │   │                   │
         │   │      Utilisateur clique 
         │   │      "Suivant" ou "Annuler"
         │   │                   │
         │   └───────────────────┤
         │                       ▼
         │          ┌─────────────────────────────────┐
    Données│          │ SCAN_READY                      │
    manquantes        │  status "Scan terminé" ✓        │
         │          └────────────┬────────────────────┘
         │                       │
         │                       ├──────────────────────────────┐
         │                       │                              │
         │          Validation ✓ │          Validation ✗ (données manquantes)
         │                       │                              │
         │                       ▼                              ▼
         │          ┌──────────────────────┐    ┌─────────────────────────┐
         │          │ SCAN_COMPLETED ✓     │    │ REFUSED ✗               │
         │          │  Demande acceptée     │    │  Raison: Photo et/ou    │
         │          │  Passage stage suiv.  │    │  signature manquantes   │
         │          └──────────────────────┘    └─────────────────────────┘
         │                                              ▲
         │                                              │
         └──────────────────────────────────────────────┘
```

## 3. Sequence Diagram - Cas Nominal (Photo + Signature)

```
User          Browser           Server        Storage
 │              │                  │             │
 ├─ 1.Access ──>│                  │             │
 │ Page         │  2.GET /demandes │             │
 │              ├────────────────>│             │
 │              │  3.Return data   │             │
 │              |<────────────────┤             │
 │              │                  │             │
 │ 4.Authorize  │                  │             │
 │ Webcam       │                  │             │
 ├─ Permit ────>│                  │             │
 │              │ 5.getUserMedia() │             │
 │              │                  │             │
 │ 6.See        │                  │             │
 │ Preview─────>│                  │             │
 │              │                  │             │
 │ 7.Click      │                  │             │
 │ "Capture"───>│                  │             │
 │              │ 8.Canvas capture │             │
 │              │    (Blob)         │             │
 │              │                  │             │
 │              │ 9.POST /demandes │             │
 │              │      /{id}/photo │             │
 │              ├────────────────>│ 10.Validate│
 │              │                  │    & Save  │
 │              │                  ├───────────>│
 │              │  11.Return URL   │             │
 │              |<────────────────┤             │
 │              │                  │             │
 │ 12.Draw      │                  │             │
 │ Signature   ├─ Canvas draw ────>│             │
 │              │                  │             │
 │ 13.Click    │                  │             │
 │ "Submit"────>│                  │             │
 │              │ 14.POST /demandes│             │
 │              │  /{id}/signature │             │
 │              ├────────────────>│ 15.Validate│
 │              │                  │    & Save  │
 │              │                  ├───────────>│
 │              │  16.Return URL   │             │
 │              |<────────────────┤             │
 │              │                  │             │
 │ 17.Click    │                  │             │
 │ "Next"──────>│                  │             │
 │              │ 18.PATCH status  │             │
 │              │     (COMPLETE)   │             │
 │              ├────────────────>│ 19.Update  │
 │              │                  │    DB      │
 │              │  20.Status OK    │             │
 │              |<────────────────┤             │
 │              │                  │             │
 │ 21.Redirect  │                  │             │
 │ to Scan Page │                  │             │
 │<─────────────┤                  │             │
```

## 4. State Transition Table

| État Actuel | Événement | État Suivant | Condition |
|---|---|---|---|
| DRAFT | Accès page Photo/Sig | CAPTURING_MEDIA | - |
| CAPTURING_MEDIA | Photo uploadée | PHOTO_CAPTURED | photo_url != null |
| PHOTO_CAPTURED | Signature uploadée | PHOTO_SIGNATURE_COMPLETE | signature_url != null |
| PHOTO_SIGNATURE_COMPLETE | Utilisateur clique "Suivant" | SCAN_READY | Validation réussie |
| PHOTO_SIGNATURE_COMPLETE | Utilisateur clique "Annuler" | CAPTURING_MEDIA | Reset |
| CAPTURING_MEDIA | Utilisateur clique "Recommencer" | CAPTURING_MEDIA | Réinitialise photo |
| PHOTO_CAPTURED | Utilisateur clique "Recommencer" | CAPTURING_MEDIA | Réinitialise photo |
| PHOTO_SIGNATURE_COMPLETE | Données manquantes au submit | REFUSED | photo == null OR signature == null |

## 5. Error Handling Flow

```
Utilisateur essaie d'accéder Photo/Signature
         │
         ▼
    ┌─────────────┐
    │ Webcam      │
    │ accessible? │
    └─┬───────┬───┘
      │       │
      ✓ OUI   ✗ NON
      │       │
      │       ▼
      │     ┌─────────────────────────┐
      │     │ Afficher message erreur:│
      │     │ "Webcam non disponible" │
      │     │ ou "Permission refusée" │
      │     └─────────────────────────┘
      │
      ▼
 Upload Photo
      │
      ├─ Erreur réseau ─► Retry + Message erreur
      ├─ Erreur validation (taille, format) ─► Message spécifique
      └─ Succès ──► Next step

Upload Signature
      │
      ├─ Erreur réseau ─► Retry + Message erreur
      ├─ Canvas vide ─► "Signature vide, recommencer"
      └─ Succès ──► Next step

Submit (Clique "Suivant")
      │
      ├─ Photo manquante ─► Message + Reboucle
      ├─ Signature manquante ─► Message + Reboucle
      ├─ Erreur serveur ─► Message + Retry
      └─ Succès ──► Redirect Scan page
```

---

## 6. Entity-Relationship Diagram (Données)

```
┌─────────────────────────────────────┐
│         DEMANDE                     │
├─────────────────────────────────────┤
│ id (PK)                             │
│ status (ENUM)                       │ ◄─── États: DRAFT, CAPTURING_MEDIA,
│ created_at (TIMESTAMP)              │      PHOTO_CAPTURED, 
│ updated_at (TIMESTAMP)              │      PHOTO_SIGNATURE_COMPLETE,
│ photo_path (VARCHAR) [NEW]          │      SCAN_READY, REFUSED
│ photo_url (VARCHAR) [NEW]           │
│ photo_upload_date (TIMESTAMP) [NEW] │
│ signature_path (VARCHAR) [NEW]      │
│ signature_url (VARCHAR) [NEW]       │
│ signature_upload_date (TIMESTAMP)   │
│ [NEW]                               │
│ refused_reason (TEXT) [NEW]         │
│ ... (autres colonnes)               │
└─────────────────────────────────────┘
```

