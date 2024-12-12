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

    public PlayerColor color() {return color;}

    public abstract PieceType type();

    public boolean isValidMove(int fromX, int fromY, int toX, int toY) { return true; }
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
    public PieceType type() {
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
    public PieceType type() {
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
    public PieceType type() {
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
    public PieceType type() {
        return PieceType.PAWN;
    }
}

class King extends MovableOncePiece {

    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }
}

class Rook extends MovableOncePiece {

    public Rook(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }
}