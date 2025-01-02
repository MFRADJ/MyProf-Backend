# Workflow de Création de Branches

## Méthode 1 : Création Locale

1. Créez votre branche localement :
```bash
git checkout -b feature/MYPROF-123-ma-feature
```

2. Faites vos premiers changements et commit :
```bash
git add .
git commit -m "feat: initialisation de la feature"
```

3. Poussez la branche :
```bash
git push -u origin feature/MYPROF-123-ma-feature
```

4. L'action GitHub détectera automatiquement la nouvelle branche et créera une issue

## Méthode 2 : Création sur GitHub

1. Allez sur GitHub dans votre repository
2. Cliquez sur la liste des branches
3. Cliquez sur "New branch"
4. Nommez la branche selon la convention : `feature/MYPROF-123-ma-feature`
5. L'action GitHub créera automatiquement une issue
6. Faites un pull localement :
```bash
git fetch
git checkout feature/MYPROF-123-ma-feature
```

## Configuration Requise

1. Installez le hook pre-push :
```bash
# Depuis la racine du projet
cp .github/hooks/pre-push .git/hooks/
chmod +x .git/hooks/pre-push
```

2. Configurez votre token GitHub :
```bash
git config --global github.token "votre-token-github"
```

## Notes Importantes

- Le format de la branche doit être exact : `type/MYPROF-XXX-description`
- Types valides : feature, bugfix, hotfix, release
- L'issue sera créée une seule fois, lors du premier push
- L'issue sera automatiquement assignée au créateur de la branche
- Les labels seront ajoutés selon le type de branche
