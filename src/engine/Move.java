package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.MovableOncePiece;
import engine.piece.Piece;

public class Move {
    private Position from, to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public String toString() {
        return "fromX : " + from.x() +
                " fromY : " + from.y() +
                " toX : " + to.x() +
                " toY : " + to.y();
    }
}