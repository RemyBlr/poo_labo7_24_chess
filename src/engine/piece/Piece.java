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

    public void setX(int x) { pos.setX(x); };
    public void setY(int y) { pos.setY(y); };
    public void setXY(int x, int y) {setX(x); setY(y);};

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PlayerColor color() {return color;}

    public abstract PieceType type();

    public abstract boolean isValidMove(Move move, Board board, Move lastMove);
}

