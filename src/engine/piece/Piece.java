package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;

/*
...
 */
public abstract class Piece {
    PlayerColor color;

    int x;
    int y;

    public Piece(PlayerColor color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;};
    public int getY() {return y;}

    public void setX(int x) {this.x = x;};
    public void setY(int y) {this.y = y;};
    public void setXY(int x, int y) {setX(x); setY(y);};

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PlayerColor color() {return color;}

    public abstract PieceType type();

    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) { return true; }
}

