package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Position;

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

