# Convention de Nommage des Branches

## Format Standard
```
<type>/<ID-JIRA>-<description-courte>
```

Exemple : `feature/MYPROF-123-ajout-systeme-paiement`

## Types de Branches
- `feature/` : Nouvelle fonctionnalité
- `bugfix/` : Correction de bug
- `hotfix/` : Correction urgente en production
- `release/` : Préparation d'une release

## Règles
1. **Type** : Toujours en minuscules
2. **ID JIRA** : Format `MYPROF-XXX`
3. **Description** : 
   - En minuscules
   - Mots séparés par des tirets
   - Courte mais descriptive
   - Pas d'accents ni caractères spéciaux

## Exemples Valides
- `feature/MYPROF-123-ajout-systeme-paiement`
- `bugfix/MYPROF-456-correction-login-facebook`
- `hotfix/MYPROF-789-fix-memory-leak`
- `release/MYPROF-234-version-2-0-0`

## Process Automatisé
1. Création d'une nouvelle branche
2. GitHub Action détecte la création
3. Analyse du nom de la branche
4. Création automatique d'une issue avec :
   - Titre formaté
   - Description standard
   - Labels appropriés
   - Assignation au créateur
   - Lien vers la branche

## Notes
- Une issue est créée uniquement si le format est respecté
- L'issue est automatiquement liée à la branche
- Les labels sont ajoutés selon le type de branche
- Le créateur de la branche est automatiquement assigné
