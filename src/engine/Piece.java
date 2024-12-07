package engine;

import chess.PlayerColor;

/*
...
 */
public class Piece {
    PlayerColor color;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Bishop extends Piece {

    public Bishop(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Knight extends Piece {

    public Knight(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Queen extends Piece {

    public Queen(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

/*
...
 */
class MovableOncePiece extends Piece {

    public MovableOncePiece(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Pawn extends MovableOncePiece {

    public Pawn(PlayerColor color) {
        super(color);
    }
}

class King extends MovableOncePiece {

    public King(PlayerColor color) {
        super(color);
    }
}

class Rook extends MovableOncePiece {

    public Rook(PlayerColor color) {
        super(color);
    }
}