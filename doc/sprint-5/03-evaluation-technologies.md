# Évaluation Technologique - Webcam & Signature

## 1. Capture Webcam

### Option 1: HTML5 MediaDevices API (RECOMMANDÉE)

| Critère | Évaluation |
|---------|-----------|
| **Support navigateur** | ✅ Chrome 21+, Firefox 25+, Safari 11+, Edge 12+ |
| **Stabilité** | ✅ Standard W3C, bien documenté, mature |
| **Facilité d'implémentation** | ✅ API simple et directe |
| **Performance** | ✅ Accès direct au hardware |
| **Sécurité** | ✅ Permissions utilisateur requises |
| **Coût** | ✅ Gratuit (standard web) |

**Code exemple**:
```javascript
async function startWebcam() {
  try {
    const stream = await navigator.mediaDevices.getUserMedia({
      video: {
        width: { ideal: 1280 },
        height: { ideal: 720 },
        facingMode: 'user'
      }
    });
    video.srcObject = stream;
  } catch (err) {
    console.error('Erreur webcam:', err);
  }
}

function capturePhoto() {
  const canvas = document.createElement('canvas');
  canvas.width = video.videoWidth;
  canvas.height = video.videoHeight;
  const ctx = canvas.getContext('2d');
  ctx.drawImage(video, 0, 0);
  return canvas.toBlob(blob => uploadPhoto(blob));
}
```

**Avantages**:
- Standard officiel W3C
- Aucune dépendance externe
- Compatible tous navigateurs modernes
- Permissions gérées automatiquement par navigateur

**Inconvénients**:
- Doit être en HTTPS (sauf localhost)
- Pas de contrôle avancé (zoom, filtre)

---

### Option 2: WebRTC avec PeerJS

| Critère | Évaluation |
|---------|-----------|
| **Support navigateur** | ✅ Excellente couverture |
| **Stabilité** | ✅ Libraire stable et mature |
| **Facilité d'implémentation** | ⚠️ Plus complexe que MediaAPI |
| **Performance** | ✅ Optimisée |
| **Sécurité** | ✅ Sécurisée |
| **Coût** | ✅ Gratuit (OSS) |

**Decision**: ✗ Overkill pour simple capture photo

---

## 2. Signature Digitale

### Option 1: <canvas> + Mouse/Touch Events (RECOMMANDÉE)

| Critère | Évaluation |
|---------|-----------|
| **Support navigateur** | ✅ Canvas: Chrome 4+, Firefox 1.5+, Safari 2+, IE 9+ |
| **Stabilité** | ✅ Standard W3C, très stable |
| **Facilité d'implémentation** | ✅ Simple à implémenter |
| **Performance** | ✅ Excellente |
| **Taille fichier | ✅ Faible (PNG 50-200KB) |
| **Sécurité** | ✅ Client-side seulement |

**Code exemple**:
```javascript
const canvas = document.getElementById('signaturePad');
const ctx = canvas.getContext('2d');
let isDrawing = false;

canvas.addEventListener('mousedown', (e) => {
  isDrawing = true;
  const rect = canvas.getBoundingClientRect();
  ctx.beginPath();
  ctx.moveTo(e.clientX - rect.left, e.clientY - rect.top);
});

canvas.addEventListener('mousemove', (e) => {
  if (!isDrawing) return;
  const rect = canvas.getBoundingClientRect();
  ctx.lineTo(e.clientX - rect.left, e.clientY - rect.top);
  ctx.stroke();
});

canvas.addEventListener('mouseup', () => isDrawing = false);

function saveSignature() {
  return canvas.toBlob(blob => uploadSignature(blob));
}
```

**Avantages**:
- Natif HTML5
- Pas de libraire externe
- Haute performance
- Signature légère (PNG)
- Support tactile facile

**Inconvénients**:
- Manque fonctionnalités avancées
- Pas de undo/redo natif

---

### Option 2: SignaturePad.js (libraire)

| Critère | Évaluation |
|---------|-----------|
| **Support navigateur** | ✅ Excellent |
| **Stabilité** | ✅ Mature et stable |
| **Facilité d'implémentation** | ✅ Très facile |
| **Performance** | ✅ Bonne |
| **Fonctionnalités | ✅ Undo/Redo, Clear, etc. |
| **Coût** | ✅ Gratuit (MIT) |
| **Taille** | ⚠️ ~30KB |

**Recommandation**: Utiliser cette libraire car elle offre UX meilleure

**NPM**: `npm install signature_pad`

```javascript
import SignaturePad from 'signature_pad';

const canvas = document.getElementById('signaturePad');
const signaturePad = new SignaturePad(canvas);

// Undo
document.getElementById('btnUndo').addEventListener('click', () => {
  const data = signaturePad.toData();
  if (data.length > 0) {
    data.pop();
    signaturePad.fromData(data);
  }
});

// Clear
document.getElementById('btnClear').addEventListener('click', () => {
  signaturePad.clear();
});

// Save
document.getElementById('btnSave').addEventListener('click', () => {
  if (signaturePad.isEmpty()) {
    alert('Veuillez signer');
  } else {
    signaturePad.toBlob(blob => uploadSignature(blob));
  }
});
```

---

### Option 3: DrawingBoard.js

| Critère | Évaluation |
|---------|-----------|
| **Facilité** | ✅ Facile |
| **Fonctionnalités** | ✅ Couleurs, tailles de pinceau |
| **Performance** | ✅ Bonne |
| **Taille libraire** | ⚠️ Plus gros que SignaturePad |

**Recommandation**: ✗ Overkill pour signature simple

---

## 3. Compression Images

### Option 1: Client-side (Canvas API)

```javascript
function compressImage(blob, maxWidth = 1280, quality = 0.85) {
  return new Promise((resolve) => {
    const img = new Image();
    img.onload = () => {
      const canvas = document.createElement('canvas');
      let width = img.width;
      let height = img.height;
      
      if (width > maxWidth) {
        height = Math.round((height * maxWidth) / width);
        width = maxWidth;
      }
      
      canvas.width = width;
      canvas.height = height;
      canvas.getContext('2d').drawImage(img, 0, 0, width, height);
      canvas.toBlob(resolve, 'image/jpeg', quality);
    };
    img.src = URL.createObjectURL(blob);
  });
}
```

**Avantages**:
- ✅ Gratuit, pas de dépendance serveur
- ✅ Réseau: Reduce payload
- ✅ Améliore UX (upload rapide)

---

### Option 2: Backend (ImageMagick / Thumbnailator)

**Thumbnailator (Java)**:
```xml
<dependency>
  <groupId>net.coobird</groupId>
  <artifactId>thumbnailator</artifactId>
  <version>0.4.19</version>
</dependency>
```

```java
Thumbnails.of(fileInputStream)
  .size(1280, 720)
  .outputQuality(0.85)
  .toFile("output.jpg");
```

**Avantages**:
- ✅ Contrôle serveur
- ✅ Qualité garantie

---

## 4. Stockage Fichiers

### Option 1: Système Fichiers Local (RECOMMANDÉE - MVP)

| Critère | Évaluation |
|---------|-----------|
| **Coût** | ✅ Gratuit |
| **Facilité setup** | ✅ Très simple |
| **Scalabilité** | ⚠️ Limitée (une machine) |
| **Haute disponibilité** | ⚠️ Nécessite NFS/backup |
| **Sécurité** | ✅ Bonne si bien configuré |

**Config**:
```
/data/
  └── uploads/
      ├── photos/
      │   ├── demande_12345_photo_1234567890.jpg
      │   └── demande_12346_photo_1234567891.jpg
      └── signatures/
          ├── demande_12345_sig_1234567890.png
          └── demande_12346_sig_1234567891.png
```

---

### Option 2: Azure Blob Storage

| Critère | Évaluation |
|---------|-----------|
| **Coût** | ⚠️ Payant (~€0.02/GB mois) |
| **Scalabilité** | ✅ Excellente |
| **Haute disponibilité** | ✅ Excellente |
| **Sécurité** | ✅ Très sécurisé |
| **Facilité** | ✅ Bonne (SDK Azure) |
| **CDN intégré** | ✅ Oui |

**Recommandation pour PROD**: ✅ Recommandé

---

### Option 3: AWS S3

| Critère | Évaluation |
|---------|-----------|
| **Coût** | ⚠️ Payant (~€0.023/GB mois) |
| **Scalabilité** | ✅ Excellente |
| **Haute disponibilité** | ✅ Excellente |
| **Facilité** | ✅ SDK AWS bien documenté |

---

## 5. Validation & Sécurité Fichiers

### Library Apache Commons Imaging

```xml
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-imaging</artifactId>
  <version>1.0.0-alpha3</version>
</dependency>
```

```java
public class ImageValidator {
  public static boolean isValidImage(InputStream is) throws IOException {
    ImageInfo info = Imaging.getImageInfo(is);
    return info != null && 
           (info.getMimeType().equals("image/jpeg") ||
            info.getMimeType().equals("image/png"));
  }
}
```

---

### ClamAV pour Scan Virus (Optionnel)

```xml
<dependency>
  <groupId>xyz.capybara</groupId>
  <artifactId>clamav-client</artifactId>
  <version>1.2.2</version>
</dependency>
```

---

## 6. Résumé Recommandations

| Composant | Technologie | Raison |
|-----------|------------|--------|
| **Webcam** | HTML5 MediaDevices API | Standard, simple, performant |
| **Signature** | SignaturePad.js | UX complète (undo/redo) |
| **Compression** | Canvas API Client-side | Réseau optimisé, UX rapide |
| **Stockage** | Local (MVP) + Azure (PROD) | Flexibilité |
| **Validation** | Apache Commons Imaging | Robustesse |
| **Antivirus** | ClamAV (optionnel) | Sécurité additionnelle |

---

## 7. Compatibilité Navigateurs

### MediaDevices API (Webcam)

| Navigateur | Version Min | Support |
|-----------|-----------|---------|
| Chrome | 21 | ✅ |
| Firefox | 25 | ✅ |
| Safari | 11 | ✅ |
| Edge | 12 | ✅ |
| Opera | 15 | ✅ |
| IE | N/A | ❌ |

**Fallback IE**: Désactiver feature ou proposer alternative (upload fichier)

### Canvas API (Signature)

| Navigateur | Version Min | Support |
|-----------|-----------|---------|
| Chrome | 4 | ✅ |
| Firefox | 1.5 | ✅ |
| Safari | 2 | ✅ |
| Edge | 12 | ✅ |
| IE | 9 | ✅ |

**Très bonne couverture**

---

## 8. Dépendances JavaScript Recommandées

```json
{
  "dependencies": {
    "signature_pad": "^4.1.5"
  },
  "devDependencies": {
    "@types/signature_pad": "^4.1.2"
  }
}
```

---

## 9. Considérations Additional

### HTTPS Obligatoire
- MediaDevices API nécessite HTTPS
- Exception: localhost pour développement

### CORS
- Permettre uploads depuis domaine frontend
- Configurer CORS serveur si domaine différent

### Permissions Utilisateur
- Navigateur demande permission webcam
- Utilisateur peut refuser → gérer gracefully

### Accessibility (A11y)
- Boutons keyboard accessible
- Labels ARIA pour lecteurs d'écran
- Fallback pour non-JavaScript

