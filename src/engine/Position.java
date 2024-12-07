package engine;

public class Position {
    private int x;
    private int y;
    private Piece piece;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
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
}
