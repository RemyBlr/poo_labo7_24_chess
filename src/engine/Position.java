package engine;

import engine.piece.Piece;

public class Position {
    private int x;
    private int y;
    private Piece piece;

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
    }

    public void setOccupant(Piece piece) {
        this.piece = piece;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
