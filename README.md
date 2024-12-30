# MyProf Backend

## Environnements

### Development (dev)
- **Branche**: develop
- **URL**: https://dev.myprof.com
- **Base de données**: MongoDB Dev
- **Usage**: Développement et tests quotidiens
- **Caractéristiques**:
  - Logs détaillés
  - Swagger UI activé
  - Outils de développement activés
  - Données de test

### Staging (staging)
- **Branche**: main
- **URL**: https://staging.myprof.com
- **Base de données**: MongoDB Staging
- **Usage**: Tests d'intégration et validation
- **Caractéristiques**:
  - Configuration proche de la production
  - Tests automatisés
  - Données de test contrôlées

### Production (prod)
- **Branche**: main (avec tag de version)
- **URL**: https://myprof.com
- **Base de données**: MongoDB Production
- **Usage**: Environnement de production
- **Caractéristiques**:
  - Haute sécurité
  - Logs optimisés
  - Performance maximale
  - Données réelles

## Configuration des Environnements

### Prérequis
- JDK 17
- MongoDB
- Docker

### Variables d'Environnement
Chaque environnement nécessite les variables suivantes :
- `MONGODB_URI`: URI de connexion MongoDB
- `SPRING_PROFILES_ACTIVE`: Profil Spring actif (dev/staging/prod)
- `JWT_SECRET`: Clé secrète pour JWT

### Protection des Branches
- `develop`: Tests de base requis
- `main`: Tous les tests et revues de code requis

## Déploiement
Le déploiement est automatisé via GitHub Actions avec des workflows spécifiques pour chaque environnement.

### Pipeline de Déploiement
1. Tests et build
2. Analyse de qualité
3. Tests de sécurité
4. Déploiement
5. Vérification post-déploiement