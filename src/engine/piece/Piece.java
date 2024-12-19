package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

/*
...
 */
public abstract class Piece {
    PlayerColor color;
    Position pos;

    public Piece(PlayerColor color, Position pos) {
        this.color = color;
        this.pos = pos;
    }

    public Position pos() { return this.pos; }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PlayerColor color() {return color;}

    public abstract PieceType type();

    public abstract boolean isValidMove(Move move, Board board, Move lastMove);

    public void setPos(Position to) {
        this.pos = to;
    }
}
