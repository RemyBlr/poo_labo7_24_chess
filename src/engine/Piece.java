package engine;

import chess.PieceType;
import chess.PlayerColor;

/*
...
 */
public abstract class Piece {
    PlayerColor color;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PlayerColor getColor() {return color;}

    public abstract PieceType getType();
}

class Bishop extends Piece {

    public Bishop(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
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

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
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

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }
}

/*
...
 */
abstract class MovableOncePiece extends Piece {

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

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}

class King extends MovableOncePiece {

    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}

class Rook extends MovableOncePiece {

    public Rook(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }
}