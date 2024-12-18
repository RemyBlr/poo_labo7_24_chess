package engine;

import engine.piece.Piece;

public class Position {
    private int x;
    private int y;
    private Piece piece; // Why does the position needs a piece, wtf ?

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public Piece getOccupant() {
        return piece;
    } // What is this ?

    public void setOccupant(Piece piece) {
        this.piece = piece;
    } // Same here.

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isInsideBoard() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
