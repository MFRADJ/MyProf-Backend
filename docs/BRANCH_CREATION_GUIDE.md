# Guide de Création de Branches

## Convention de Nommage

### Format
```
<type>/MYPROF-<numero>-<description>
```

### Types Valides
- `feature` : Nouvelle fonctionnalité
- `bugfix` : Correction de bug
- `hotfix` : Correction urgente en production
- `release` : Préparation d'une release

### Exemples
- `feature/MYPROF-123-systeme-paiement`
- `bugfix/MYPROF-456-correction-login`
- `hotfix/MYPROF-789-fix-memory-leak`
- `release/MYPROF-234-version-2-0-0`

## Processus de Création

### 1. Sur GitHub
1. Allez sur le repository
2. Cliquez sur la liste des branches
3. Cliquez sur "New branch"
4. Nommez la branche selon la convention

### 2. Localement
1. Faites un fetch pour voir la nouvelle branche :
```bash
git fetch origin
```

2. Basculez sur la nouvelle branche :
```bash
git checkout <nom-de-la-branche>
```

## Automatisation

### Ce qui se passe automatiquement
1. Création de l'issue avec :
   - Template selon le type
   - Labels appropriés
   - Assignation au créateur
   - Lien vers la branche
   - Lien vers Jira

### Templates d'Issues

#### Feature
- Description
- Objectifs
- Critères d'acceptation
- Informations techniques

#### Bugfix
- Description du bug
- Actions à faire
- Validation
- Tests de régression

#### Hotfix
- Description urgente
- Actions immédiates
- Post-mortem
- Impact production

#### Release
- Version
- Checklist déploiement
- Validation
- Notes de version

## Bonnes Pratiques

1. **Toujours** créer la branche sur GitHub d'abord
2. Respecter strictement la convention de nommage
3. Vérifier que l'issue est bien créée
4. Mettre à jour l'issue pendant le développement
5. Lier les PR à l'issue avec les mots-clés GitHub
