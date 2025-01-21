# Authentification

## Status: 🟡 En cours

## Description
Système d'authentification moderne avec JWT et gestion des sessions.

## Fonctionnalités Implémentées
- ✅ Inscription utilisateur
- ✅ Connexion avec JWT
- ✅ Refresh tokens pour la gestion des sessions
- ✅ Déconnexion (simple et globale)
- ✅ Protection des routes
- ✅ Validation des tokens
- ✅ Gestion des sessions multiples

## Architecture
Le système d'authentification utilise une architecture en couches :

1. **Controllers** (`AuthController`)
   - Gestion des endpoints REST
   - Validation des requêtes
   - Gestion des réponses

2. **Services**
   - `AuthService`: Logique d'authentification
   - `TokenService`: Gestion des tokens
   - `UserService`: Gestion des utilisateurs
   - `JwtService`: Opérations JWT

3. **Security**
   - `JwtAuthenticationFilter`: Filtrage des requêtes
   - `SecurityConfig`: Configuration Spring Security

4. **Models**
   - `User`: Entité utilisateur
   - `RefreshToken`: Gestion des sessions

## Endpoints API

### Public Endpoints
```http
POST /api/auth/signup
Content-Type: application/json

{
    "username": "string",
    "email": "string",
    "password": "string"
}
```

```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "string",
    "password": "string"
}
```

### Protected Endpoints
```http
POST /api/auth/refresh-token
Refresh-Token: string
```

```http
POST /api/auth/logout
Refresh-Token: string
```

```http
POST /api/auth/logout-all
User-ID: string
```

## Solution Technique
- JWT avec algorithme HS256
- Refresh tokens stockés en base de données
- Validation par device et IP
- Limitation des sessions par appareil
- Rotation des tokens
- Blacklisting des tokens révoqués

## Reste à Faire
1. **Sécurité**
   - [ ] Implémentation de rate limiting
   - [ ] Ajout de CORS
   - [ ] Protection contre les attaques CSRF
   - [ ] Validation des mots de passe forts

2. **Fonctionnalités**
   - [ ] Récupération de mot de passe
   - [ ] Vérification d'email
   - [ ] OAuth2 (Google, GitHub)
   - [ ] Authentification 2FA

3. **Monitoring**
   - [ ] Logging des tentatives de connexion
   - [ ] Alertes de sécurité
   - [ ] Audit des connexions

4. **Tests**
   - [ ] Tests unitaires
   - [ ] Tests d'intégration
   - [ ] Tests de performance

## Diagramme
Voir [authentication.puml](./authentication.puml) pour le diagramme d'architecture.
