# Rapport - Échecs
>Auteur: Bleuet Rémy, Changanaqui Yoann & Duruz Florian
>
## 1. Introduction
Le but de ce laboratoire est d'implémenter un jeu d'échecs fonctionnel.
Ce rapport résume les choix d'implémentation effectués pour la réalisation
de ce projet.

## 2. Architecture générale
- ChessGame (contrôleur) :
  - Met en place la logique centrale du jeu.
  - Stocke le plateau (`Board`), le joueur courant (`currentPlayerColor`), le dernier coup joué (`lastMove`)
    et l'état de la partie (`isGameOver`).
  - Gère la méthode `move()` et relègue les mouvements spécifiques aux pièces concernées.
- Board :
  - Contient un tableau de `Piece` de taille 8x8.
  - Initialise le plateau avec les pièces à leur position initiale.
  - Méthodes pour manipuler les pièces (`movePiece()` et `removePiece()`).
- Position :
  - Gère les coordonnées d'une case sur le plateau `(x, y)`
  - Vérifie si une position se trouve dans les limites du plateau.
- Move :
  - Encapsule deux positions : `from` et `to`.
- Piece (abstract) :
  - Représente une pièce d'échec avec une couleur (`PlayerColor`) et une position (`Position`).
  - Méthodes communes à toutes les pièces : `isValidMove()`, `executeMove()`.
  - Sous-classes : `Pawn`, `Rook`, `Knight`, `Bishop`, `Queen`, `King`.

## 3. Gestions des mouvements

### 3.1 Méthodes isValidMove()
- Polymorphisme : chaque pièce implémente sa propre logique de déplacement.
- Pièces linéaires (fou, tour, dame) : vérifient la trajectoire (pas d’obstacles).
- Roi : autorise un déplacement d’une case et la logique de roque.
- Pion : gère l’avance d’une ou deux cases, la capture diagonale, la prise en passant et la promotion.

### 3.2 Méthode executeMove(...)
- Chaque pièce dispose d’une méthode `executeMove(Move, Board, ChessView, Move lastMove)` qui réalise le déplacement.
- Roi : gère le roque.
- Pion : gère la prise en passant et la promotion.

### 3.2.1 Roque
- On vérifie si le roi veut se déplacer de deux case à droite ou à gauche de sa position (sans qu'il y ait de pièce entre le roi et la tour)
- Lorsque les deux pièces concernées n'ont jusqu'alors pas bougé de la partie elles sont autorisées à exécuter le roque

### 3.2.2 Promotion
- Teste si le pion atteint la dernière ligne.
- Utilise `view.askUser()` pour demander la pièce choisie au joueur.
- Remplace le pion par une nouvelle pièce dans le `Board` et met à jour la vue.

### 3.2.3 Prise en passant
- Flag `doublePawnMove` pour signaler qu’un pion a avancé de 2 cases.
- `isEnPassant` est mis à true si le déplacement est validé en diagonale vers une case vide et que le dernier coup
  était un double pas d’un pion adverse qui se place à côté du pion allié.
- Après le déplacement, on retire le pion adverse juste derrière la case d’arrivée.

## 4. Tests
Nous avons réalisé chaque cas de figure spécifique demandé dans l'énoncé du laboratoire du coup classique bouger un cavalier au coup plus complexe comme le roque ou la prise en passant.

## 5. Conclusion
L’implémentation utilise une architecture orientée objet, où :
1. `ChessGame` gère le jeu et appelle les méthodes nécessaires,
2. `Board` pour déplacer/manipuler les pièces, et
3. Chaque `Piece` gère son déplacement et son exécution spécifique.

### Difficultés rencontrées
- L'encapsulation de chaque mouvement spécifique dans les classes des pièces a été un défi pour garantir la modularité et la lisibilité du code.
- L'echec et mat a été un défi pour nous car il a fallu vérifier si le roi était en echec et si il pouvait bouger pour sortir de cette situation d'après toutes les pièces adverses.
- La prise en passant a été un défi pour nous car il a fallu vérifier si le pion adverse avait bougé de deux cases et si le pion allié pouvait le prendre.

### Améliorations possibles
TODO