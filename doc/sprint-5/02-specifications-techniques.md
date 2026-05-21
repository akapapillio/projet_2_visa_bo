# Spécifications Techniques - Photo et Signature

## 1. Vue d'Ensemble Architecture

```
┌────────────────────────────────────────────────────────┐
│            FRONTEND (Thymeleaf/HTML5)                  │
├────────────────────────────────────────────────────────┤
│  ┌──────────────────┐  ┌──────────────────────────┐   │
│  │ WebcamCapture    │  │ SignaturePad             │   │
│  │ Component        │  │ (Canvas-based)           │   │
│  └────────┬─────────┘  └────────────┬─────────────┘   │
│           │                         │                 │
│           └──────────┬──────────────┘                 │
│                      │                                │
│                ┌─────┴──────┐                         │
│                │ Validators │                         │
│                │ & Services │                         │
│                └─────┬──────┘                         │
└─────────────────────┼──────────────────────────────────┘
                      │
         HTTP REST API (Multipart/Form-Data)
                      │
┌─────────────────────┴──────────────────────────────────┐
│            BACKEND (Spring Boot)                       │
├────────────────────────────────────────────────────────┤
│  ┌────────────────────────────────────────────────┐   │
│  │ PhotoSignatureController                       │   │
│  │ - POST /api/demandes/{id}/photo                │   │
│  │ - POST /api/demandes/{id}/signature            │   │
│  │ - PATCH /api/demandes/{id}/status              │   │
│  └────────┬───────────────────────────────────────┘   │
│           │                                            │
│  ┌────────▼──────────────────────────────────────────┐ │
│  │ PhotoSignatureService                            │ │
│  │ - validateFile()                                 │ │
│  │ - saveFile()                                     │ │
│  │ - generateUniqueFileName()                       │ │
│  │ - updateDemandStatus()                           │ │
│  └────────┬──────────────────────────────────────────┘ │
│           │                                            │
│  ┌────────▼──────────────────────┬───────────────────┐ │
│  │ File Storage System            │ Database (JPA)    │ │
│  │ - Local FS                     │ - Demande Entity  │ │
│  │ - Cloud (Azure Blob / S3)      │ - PhotoSignature  │ │
│  └────────────────────────────────┴───────────────────┘ │
└────────────────────────────────────────────────────────┘
           │
           ▼
    Data Store / Database
```

## 2. Technologies à Utiliser

### Frontend
- **HTML5 MediaDevices API**: Accès à la webcam (`getUserMedia`)
- **Canvas API**: Signature pad + capture photo
- **JavaScript Fetch API**: Requêtes HTTP
- **Form Data API**: Envoi fichiers multipart

### Backend
- **Spring Boot 2.x/3.x**
- **Spring Web MVC**: Contrôleurs REST
- **Spring Data JPA**: Persistance
- **Hibernate**: ORM
- **Commons FileUpload**: Gestion upload
- **Image Processing**: 
  - Apache Commons Imaging / ImageIO
  - ou Thumbnailator (compression images)

### Stockage Fichiers
- **Option 1**: Système fichiers local (`/uploads/photos/`, `/uploads/signatures/`)
- **Option 2**: Azure Blob Storage
- **Option 3**: AWS S3

### Base de Données
- PostgreSQL 13+ (existant)
- Migration Flyway/Liquibase

## 3. Endpoints API

### 3.1 Upload Photo

**Endpoint**: `POST /api/demandes/{id}/photo`

**Headers**:
```
Content-Type: multipart/form-data
Authorization: Bearer {JWT_TOKEN}
```

**Body**:
```
Field: photo (File, required)
  - Type: image/jpeg, image/png
  - Max size: 5 MB
  - Min dimension: 200x200px
```

**Response Success (200 OK)**:
```json
{
  "success": true,
  "photo_url": "/uploads/photos/demande_12345_photo_1699999999.jpg",
  "photo_path": "demande_12345_photo_1699999999.jpg",
  "uploaded_at": "2026-05-07T10:30:45Z",
  "file_size": 245632
}
```

**Response Errors**:
- `400 Bad Request`: Fichier vide, format invalide, taille > 5MB
- `401 Unauthorized`: Token manquant/invalide
- `404 Not Found`: Demande introuvable
- `500 Internal Server Error`: Erreur serveur

---

### 3.2 Upload Signature

**Endpoint**: `POST /api/demandes/{id}/signature`

**Headers**:
```
Content-Type: multipart/form-data
Authorization: Bearer {JWT_TOKEN}
```

**Body**:
```
Field: signature (File, required)
  - Type: image/png
  - Max size: 2 MB
```

**Response Success (200 OK)**:
```json
{
  "success": true,
  "signature_url": "/uploads/signatures/demande_12345_sig_1699999999.png",
  "signature_path": "demande_12345_sig_1699999999.png",
  "uploaded_at": "2026-05-07T10:31:15Z",
  "file_size": 125432
}
```

**Response Errors**: Même que photo

---

### 3.3 Mise à Jour Status

**Endpoint**: `PATCH /api/demandes/{id}/status`

**Headers**:
```
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}
```

**Body**:
```json
{
  "status": "PHOTO_SIGNATURE_COMPLETE"
}
```

**Response Success (200 OK)**:
```json
{
  "id": 12345,
  "status": "PHOTO_SIGNATURE_COMPLETE",
  "photo_url": "/uploads/photos/demande_12345_photo_1699999999.jpg",
  "signature_url": "/uploads/signatures/demande_12345_sig_1699999999.png",
  "updated_at": "2026-05-07T10:31:25Z"
}
```

**Response Errors**:
- `400 Bad Request`: Transition d'état invalide, données manquantes
- `404 Not Found`: Demande introuvable
- `409 Conflict`: Status actuel incompatible

---

### 3.4 Récupérer Demande (Modifié)

**Endpoint**: `GET /api/demandes/{id}`

**Response Success (200 OK)**:
```json
{
  "id": 12345,
  "status": "PHOTO_SIGNATURE_COMPLETE",
  "created_at": "2026-05-07T09:00:00Z",
  "photo": {
    "url": "/uploads/photos/demande_12345_photo_1699999999.jpg",
    "uploaded_at": "2026-05-07T10:30:45Z",
    "size": 245632
  },
  "signature": {
    "url": "/uploads/signatures/demande_12345_sig_1699999999.png",
    "uploaded_at": "2026-05-07T10:31:15Z",
    "size": 125432
  },
  "refused_reason": null,
  "... autres champs"
}
```

## 4. Modèle de Données Détaillé

### Table `demande` - Colonnes Nouvelles

```sql
-- Colonnes existantes
id BIGINT PRIMARY KEY
nom VARCHAR(255)
prenom VARCHAR(255)
statut VARCHAR(50) -- DRAFT, CAPTURING_MEDIA, PHOTO_CAPTURED, PHOTO_SIGNATURE_COMPLETE, etc.
created_at TIMESTAMP
updated_at TIMESTAMP

-- NOUVELLES COLONNES
photo_path VARCHAR(500) NULL          -- "demande_12345_photo_1699999999.jpg"
photo_url VARCHAR(1000) NULL          -- "/uploads/photos/demande_12345_photo_1699999999.jpg"
photo_uploaded_at TIMESTAMP NULL      -- Date d'upload de la photo
photo_is_valid BOOLEAN DEFAULT FALSE  -- Flag validation photo

signature_path VARCHAR(500) NULL      -- "demande_12345_sig_1699999999.png"
signature_url VARCHAR(1000) NULL      -- "/uploads/signatures/demande_12345_sig_1699999999.png"
signature_uploaded_at TIMESTAMP NULL  -- Date d'upload de la signature
signature_is_valid BOOLEAN DEFAULT FALSE -- Flag validation signature

refused_reason TEXT NULL              -- Raison du refus: "Photo manquante", "Signature manquante"
refused_at TIMESTAMP NULL             -- Date du refus

-- Indices
INDEX idx_demande_status (statut)
INDEX idx_demande_photo_path (photo_path)
INDEX idx_demande_signature_path (signature_path)
INDEX idx_demande_created_at (created_at)
```

## 5. Configurations Application

### Application.properties

```properties
# Configuration stockage fichiers
file.upload.dir=/data/uploads
file.upload.temp=/data/uploads/temp
file.photos.dir=/data/uploads/photos
file.signatures.dir=/data/uploads/signatures

# Limites fichiers
file.max.size=5242880  # 5 MB
file.photo.max.size=5242880
file.signature.max.size=2097152  # 2 MB

# Types MIME autorisés
file.allowed.mimes=image/jpeg,image/png

# Stockage
file.storage.type=local  # local, azure, s3
file.storage.azure.container=demande-files
file.storage.s3.bucket=visa-demandes
file.storage.s3.region=eu-west-1

# Compression images
image.compression.enabled=true
image.compression.quality=0.85
image.compression.format=jpeg
```

### application-azure.properties (Alternative Cloud)

```properties
file.storage.type=azure
azure.storage.account-name=${AZURE_STORAGE_ACCOUNT}
azure.storage.account-key=${AZURE_STORAGE_KEY}
azure.storage.connection-string=${AZURE_STORAGE_CONNECTION_STRING}
```

## 6. Validation Fichiers

### Validations Photo
- ✓ Format: JPEG, PNG seulement
- ✓ Taille: 0.5 MB à 5 MB
- ✓ Dimensions: Minimum 200x200px, Maximum 8000x8000px
- ✓ MIME type correct
- ✓ Fichier non corrompu (header valide)
- ✓ Pas de code exécutable (scan virus optionnel)

### Validations Signature
- ✓ Format: PNG seulement (support transparence)
- ✓ Taille: 0 à 2 MB
- ✓ Contenu: Canvas non vide (au moins quelques pixels)
- ✓ Dimensions: 300x150px minimum

## 7. Sécurité

### Authentification & Autorisation
- JWT Token obligatoire pour tous les endpoints
- Vérifier que l'utilisateur possède la demande avant upload
- Role-based access control (RBAC)

### Validation Input
- Whitelist extensions fichiers
- Valider MIME types
- Vérifier tailles
- Scan virus (ClamAV optionnel)

### Stockage Sécurisé
- Renommer fichiers (éviter injection path)
- Stocker hors racine web
- Permissions fichiers 600 (lecture propriétaire)
- Chiffrer les chemins en BDD

### Headers Sécurité
```
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
Content-Security-Policy: default-src 'self'
```

## 8. Performance & Scalabilité

### Optimisations
- Compression images côté client (avant upload)
- Lazy loading des images
- CDN pour servir les images (optionnel)
- Cache database queries

### Limites
- Max 10 demandes par minute par utilisateur
- Rate limiting sur endpoints upload
- Timeout uploads: 30 secondes

