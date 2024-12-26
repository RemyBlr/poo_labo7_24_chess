# 1. Introduction
Le but de ce laboratoire est d'implémenter un jeu d'échec fonctionnel.
Ce rapport résume les choix d'implémentation effectués pour la réalisation
de ce projet.

# 2. Architecture générale
- ChessGame (contrôleur) :
  - Met en place la logique centrale du jeu.
  - Stock le plateau (`Board`), le joueur courrant (`currentPlayerColor`), le dernier coup joué (`lastMove`)
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

# 3. Gestions des mouvements

## 3.1 Méthodes isValidMove()
- Polymorphisme : chaque pièce implémente sa propre logique de déplacement.
- Pièces linéaires (fou, tour, dame) : vérifient la trajectoire (pas d’obstacles).
- Roi : autorise un déplacement d’une case et la logique de roque.
- Pion : gère l’avance d’une ou deux cases, la capture diagonale, la prise en passant et la promotion.

## 3.2 Méthode executeMove(...)
- Chaque pièce dispose d’une méthode `executeMove(Move, Board, ChessView, Move lastMove)` qui réalise le déplacement.
- Roi : gère le roque.
- Pion : gère la prise en passant et la promotion.

### 3.2.1 Roque
- TODO

### 3.2.2 Promotion
- Teste si le pion atteint la dernière ligne.
- Utilise `view.askUser()` pour demander la pièce choisie au joueur.
- Remplace le pion par une nouvelle pièce dans le `Board` et met à jour la vue.

### 3.2.3 Prise en passant
- Flag `doublePawnMove` pour signaler qu’un pion a avancé de 2 cases.
- `isEnPassant` est mis à true si le déplacement est validé en diagonale vers une case vide et que le dernier coup 
était un double pas d’un pion adverse qui se place à côté du pion allié.
- Après le déplacement, on retire le pion adverse juste derrière la case d’arrivée.

# 4. Tests
TODO

# 5. Conclusion
L’implémentation utilise une architecture orientée objet, où :
1. `ChessGame` gère le jeu et appelle
2. `Board` pour déplacer/manipuler les pièces, et
3. Chaque `Piece` gère son déplacement et son exécution spécifique.

TODO : Difficultés rencontrées, améliorations possibles, ???

TODO : Parler du check, checkMate et staleMate