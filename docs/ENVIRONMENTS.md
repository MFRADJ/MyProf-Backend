# Guide des Environnements MyProf

## 1. Environnement Local (Feature Branch)

### Prérequis
- MongoDB local
- Java 17
- Maven

### Configuration
```yaml
# application-local.yml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/myprof_local
logging:
  level:
    root: DEBUG
    com.MyProf: DEBUG
```

### Tests
- Tests Unitaires (obligatoires)
- Tests d'Intégration avec MongoDB local
- Tests de composants

## 2. Environnement Development

### Base de données
- MongoDB Atlas: myprof_dev
- Collections: users, etc.
- Données: données de test

### Secrets nécessaires
- MONGODB_URI
- JWT_SECRET
- SONAR_TOKEN

### Déploiement
- Automatique après merge dans develop
- Tous les tests doivent passer
- Analyse SonarQube obligatoire

## 3. Environnement Staging

### Base de données
- MongoDB Atlas: myprof_staging
- Collections: identiques à prod
- Données: subset de prod

### Secrets nécessaires
- MONGODB_STAGING_URI
- JWT_SECRET
- Autres secrets de prod

### Déploiement
- Semi-automatique (après approbation)
- Tous les tests doivent passer
- Tests de performance obligatoires

## 4. Environnement Production

### Base de données
- MongoDB Atlas: myprof_prod
- Collections: users, etc.
- Données: données réelles

### Secrets nécessaires
- MONGODB_PROD_URI
- JWT_SECRET
- Tous les secrets de production

### Déploiement
- Manuel avec approbation
- Tous les tests doivent passer
- Tests de charge obligatoires
- Backup automatique avant déploiement

## Workflow de Développement

1. **Feature Branch**
   ```bash
   git checkout -b feature/ma-feature develop
   # Développement local avec MongoDB local
   # Exécuter les tests locaux
   git push origin feature/ma-feature
   ```

2. **Pull Request vers Develop**
   - Les workflows GitHub Actions s'exécutent
   - Tests avec MongoDB dev
   - Review de code obligatoire

3. **Merge vers Develop**
   - Déploiement automatique vers dev
   - Tests complets sur l'environnement dev

4. **Release vers Staging**
   - Création d'une PR develop → main
   - Tests sur l'environnement staging
   - Validation fonctionnelle

5. **Production**
   - Déploiement manuel
   - Validation finale
   - Monitoring post-déploiement
