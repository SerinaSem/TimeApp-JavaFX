# TimeApp — Application JavaFX
Réalisé par: SEMMANI Serina

## Fonctionnalités réalisées

### Horloge numérique
- Affichage de l’heure (heures, minutes, secondes)
- Réglage manuel de l’heure
- Affichage de la date du jour

### Horloge graphique
- Cadran analogique avec aiguilles
- Synchronisation avec l’horloge numérique
- Mise à jour en temps réel

### Réveil
- Réglage d’une heure de réveil
- Déclenchement visuel lorsque l’heure courante correspond à l’heure réglée

### Minuterie
- Compte à rebours (heures, minutes, secondes)
- Gestion des états :
    - État initial
    - Défilement
    - Pause
    - Dépassé (fond rouge, défilement croissant)

### Chronomètre
- Mesure du temps écoulé (minutes, secondes, centièmes)
- Démarrage / arrêt
- Mémorisation de temps intermédiaires
- Réinitialisation

---

## Architecture et choix de conception

- La gestion du temps est **centralisée dans une classe `TempsModel`**, partagée par :
    - l’horloge numérique
    - l’horloge graphique
    - le réveil

- Les vues JavaFX **observent le modèle** sans recréer l’état, ce qui :
    - garantit la cohérence des données
    - évite toute réinitialisation lors des changements de vue

- La **minuterie** et le **chronomètre** utilisent le **design pattern State**
  pour gérer proprement les transitions entre leurs différents états.

- Les vues sont instanciées **une seule fois** et réutilisées via un conteneur principal
  (`TimeApp`).

---

## Notions mises en œuvre

- JavaFX :
    - scènes
    - layouts
    - propriétés observables
    - `Timeline`
- Programmation orientée objet
- Design pattern **State**
- Séparation modèle / vue
- Synchronisation de plusieurs vues sur un même modèle
- Navigation par menu JavaFX

---

## Organisation du projet
```text
app/        =   interfaces JavaFX et navigation
model/      =   modèle de temps et compteurs
state/      =   gestion des états (minuterie, chronomètre)
