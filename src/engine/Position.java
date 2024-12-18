package engine;

import engine.piece.Piece;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        setX(x);
        setY(y);
    }

    public boolean isInsideBoard() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
