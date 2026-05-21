# Documentation API - Photo et Signature

## Base URL
```
http://localhost:8080/api
```

---

## 1. GET `/demandes/{id}` - Récupérer Demande

Récupère les détails d'une demande, y compris les URLs de photo et signature.

### Request

```http
GET /api/demandes/12345 HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Accept: application/json
```

### Response (200 OK)

```json
{
  "id": 12345,
  "reference": "DEM-2026-001",
  "status": "PHOTO_SIGNATURE_COMPLETE",
  "nom": "Dupont",
  "prenom": "Jean",
  "createdAt": "2026-05-07T09:00:00Z",
  "updatedAt": "2026-05-07T10:31:25Z",
  "photo": {
    "url": "/uploads/photos/demande_12345_photo_1714993845.jpg",
    "uploadedAt": "2026-05-07T10:30:45Z",
    "fileSize": 245632,
    "isValid": true
  },
  "signature": {
    "url": "/uploads/signatures/demande_12345_sig_1714993863.png",
    "uploadedAt": "2026-05-07T10:31:15Z",
    "fileSize": 125432,
    "isValid": true
  },
  "refusedReason": null,
  "refusedAt": null
}
```

### Erreurs Possibles

| Code | Erreur |
|------|--------|
| 400 | Bad Request - ID invalide |
| 401 | Unauthorized - Token manquant/invalide |
| 403 | Forbidden - Pas d'accès à cette demande |
| 404 | Not Found - Demande inexistante |
| 500 | Internal Server Error |

---

## 2. POST `/demandes/{id}/photo` - Upload Photo

Upload une photo depuis la webcam.

### Request

```http
POST /api/demandes/12345/photo HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: multipart/form-data; boundary=----Boundary123

------Boundary123
Content-Disposition: form-data; name="photo"; filename="photo.jpg"
Content-Type: image/jpeg

[Photo JPEG data here - binary]
------Boundary123--
```

### Validations

- ✓ Fichier requis
- ✓ Format: `image/jpeg` ou `image/png`
- ✓ Taille: 0.5 MB à 5 MB
- ✓ Dimensions: 200x200px à 8000x8000px
- ✓ Header JPEG/PNG valide

### Response (200 OK)

```json
{
  "success": true,
  "photoUrl": "/uploads/photos/demande_12345_photo_1714993845.jpg",
  "photoPath": "demande_12345_photo_1714993845.jpg",
  "uploadedAt": "2026-05-07T10:30:45Z",
  "fileSize": 245632,
  "message": "Photo téléchargée avec succès"
}
```

### Erreurs Possibles

| Code | Erreur | Raison |
|------|--------|--------|
| 400 | Bad Request | Fichier vide, format invalide, taille > 5MB |
| 400 | Invalid Image | Format image invalide, dimensions invalides |
| 401 | Unauthorized | Token manquant/invalide |
| 403 | Forbidden | Pas d'accès à cette demande |
| 404 | Not Found | Demande inexistante |
| 409 | Conflict | État de la demande incompatible |
| 413 | Payload Too Large | Fichier trop volumineux |
| 500 | Internal Server Error | Erreur serveur |

### Exemple cURL

```bash
curl -X POST http://localhost:8080/api/demandes/12345/photo \
  -H "Authorization: Bearer TOKEN" \
  -F "photo=@photo.jpg"
```

### Exemple JavaScript

```javascript
async function uploadPhoto(photoBlob) {
  const formData = new FormData();
  formData.append('photo', photoBlob, 'photo.jpg');
  
  const response = await fetch('/api/demandes/12345/photo', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    body: formData
  });
  
  if (!response.ok) {
    throw new Error(`Upload échoué: ${response.statusText}`);
  }
  
  return await response.json();
}
```

---

## 3. POST `/demandes/{id}/signature` - Upload Signature

Upload une signature dessinée sur canvas.

### Request

```http
POST /api/demandes/12345/signature HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: multipart/form-data; boundary=----Boundary123

------Boundary123
Content-Disposition: form-data; name="signature"; filename="signature.png"
Content-Type: image/png

[Signature PNG data here - binary]
------Boundary123--
```

### Validations

- ✓ Fichier requis
- ✓ Format: `image/png` seulement
- ✓ Taille: 0 à 2 MB
- ✓ Canvas non vide (minimum pixels dessinés)
- ✓ Dimensions: 300x150px minimum

### Response (200 OK)

```json
{
  "success": true,
  "signatureUrl": "/uploads/signatures/demande_12345_sig_1714993863.png",
  "signaturePath": "demande_12345_sig_1714993863.png",
  "uploadedAt": "2026-05-07T10:31:15Z",
  "fileSize": 125432,
  "message": "Signature téléchargée avec succès"
}
```

### Erreurs Possibles

| Code | Erreur | Raison |
|------|--------|--------|
| 400 | Bad Request | Fichier vide, format invalide |
| 400 | Empty Signature | Canvas vide (aucune signature) |
| 400 | Invalid Canvas | Dimensions insuffisantes |
| 401 | Unauthorized | Token manquant/invalide |
| 403 | Forbidden | Pas d'accès à cette demande |
| 404 | Not Found | Demande inexistante |
| 409 | Conflict | État de la demande incompatible |
| 413 | Payload Too Large | Fichier trop volumineux |
| 500 | Internal Server Error | Erreur serveur |

### Exemple JavaScript

```javascript
async function uploadSignature(signatureBlob) {
  const formData = new FormData();
  formData.append('signature', signatureBlob, 'signature.png');
  
  const response = await fetch(`/api/demandes/${demandeId}/signature`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    body: formData
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message);
  }
  
  return await response.json();
}
```

---

## 4. PATCH `/demandes/{id}/status` - Mise à Jour Status

Met à jour le statut de la demande vers `PHOTO_SIGNATURE_COMPLETE`.

### Request

```http
PATCH /api/demandes/12345/status HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "status": "PHOTO_SIGNATURE_COMPLETE"
}
```

### Validations

- ✓ Status doit être `PHOTO_SIGNATURE_COMPLETE`
- ✓ Transition d'état doit être valide (voir state machine)
- ✓ Photo présente (`photoUrl` non null)
- ✓ Signature présente (`signatureUrl` non null)
- ✓ Les deux uploads réussis et valides

### Response (200 OK)

```json
{
  "success": true,
  "id": 12345,
  "status": "PHOTO_SIGNATURE_COMPLETE",
  "photoUrl": "/uploads/photos/demande_12345_photo_1714993845.jpg",
  "signatureUrl": "/uploads/signatures/demande_12345_sig_1714993863.png",
  "updatedAt": "2026-05-07T10:31:25Z",
  "message": "Statut mis à jour avec succès"
}
```

### Erreurs Possibles

| Code | Erreur | Raison |
|------|--------|--------|
| 400 | Bad Request | Données manquantes (photo ou signature) |
| 400 | Invalid State Transition | Transition d'état non autorisée |
| 400 | Photo Missing | Aucune photo uploadée |
| 400 | Signature Missing | Aucune signature uploadée |
| 401 | Unauthorized | Token manquant/invalide |
| 403 | Forbidden | Pas d'accès à cette demande |
| 404 | Not Found | Demande inexistante |
| 409 | Conflict | Statut actuellement incompatible |
| 500 | Internal Server Error | Erreur serveur |

### State Transitions Valides

```
DRAFT → CAPTURING_MEDIA
CAPTURING_MEDIA → PHOTO_CAPTURED
PHOTO_CAPTURED → PHOTO_SIGNATURE_COMPLETE
PHOTO_SIGNATURE_COMPLETE → SCAN_READY (via ce endpoint)
```

### Exemple JavaScript

```javascript
async function validateAndSubmit() {
  const response = await fetch(`/api/demandes/${demandeId}/status`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({
      status: 'PHOTO_SIGNATURE_COMPLETE'
    })
  });
  
  if (response.status === 400) {
    const error = await response.json();
    // Afficher erreur utilisateur
    alert(`Erreur: ${error.message}`);
  } else if (response.ok) {
    // Rediriger vers page suivante
    window.location.href = '/demandes/scan';
  }
}
```

---

## 5. DELETE `/demandes/{id}/photo` - Supprimer Photo

Supprime la photo d'une demande (optionnel).

### Request

```http
DELETE /api/demandes/12345/photo HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Response (200 OK)

```json
{
  "success": true,
  "message": "Photo supprimée avec succès"
}
```

---

## 6. DELETE `/demandes/{id}/signature` - Supprimer Signature

Supprime la signature d'une demande (optionnel).

### Request

```http
DELETE /api/demandes/12345/signature HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Response (200 OK)

```json
{
  "success": true,
  "message": "Signature supprimée avec succès"
}
```

---

## 7. Codes HTTP Standardisés

| Code | Signification |
|------|---------------|
| 200 | OK - Succès |
| 201 | Created - Ressource créée |
| 204 | No Content - Suppression réussie |
| 400 | Bad Request - Données invalides |
| 401 | Unauthorized - Authentification requise |
| 403 | Forbidden - Accès refusé |
| 404 | Not Found - Ressource inexistante |
| 409 | Conflict - État incompatible |
| 413 | Payload Too Large - Fichier trop gros |
| 500 | Internal Server Error - Erreur serveur |

---

## 8. Format Réponse d'Erreur

Tous les erreurs retournent ce format:

```json
{
  "success": false,
  "error": {
    "code": "PHOTO_SIZE_EXCEEDS_LIMIT",
    "message": "La photo dépasse la taille maximale de 5MB",
    "details": {
      "maxSize": 5242880,
      "providedSize": 8512345,
      "field": "photo"
    }
  },
  "timestamp": "2026-05-07T10:30:45Z",
  "path": "/api/demandes/12345/photo"
}
```

---

## 9. Authentification

Tous les endpoints requièrent un JWT Token:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

**Header requis**:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## 10. Rate Limiting

- Max 10 uploads par minute par utilisateur
- Max 5 transitions d'état par minute
- Response header: `X-RateLimit-Remaining`

```http
HTTP/1.1 429 Too Many Requests
X-RateLimit-Limit: 10
X-RateLimit-Remaining: 0
X-RateLimit-Reset: 1714993900

{
  "error": "Rate limit exceeded. Try again after 60 seconds."
}
```

---

## 11. CORS Configuration

```javascript
// Configuration CORS Backend
@Configuration
public class CorsConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
          .allowedOrigins("http://localhost:3000", "https://visa.example.com")
          .allowedMethods("GET", "POST", "PATCH", "DELETE")
          .allowedHeaders("*")
          .maxAge(3600);
      }
    };
  }
}
```

