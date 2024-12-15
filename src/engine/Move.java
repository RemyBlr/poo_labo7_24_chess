package engine;

import engine.piece.Piece;

public class Move {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private boolean isCastling;
    private boolean isEnPassant;
    private boolean isPromotion;
    private boolean doublePawnMove;
    private Piece capturedPiece;

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.isCastling = false;
        this.isEnPassant = false;
        this.isPromotion = false;
        this.doublePawnMove = false;
        this.capturedPiece = null;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public void setEnPassant(boolean enPassant) {
        isEnPassant = enPassant;
    }

    public boolean wasDoublePawnMove() {
        return doublePawnMove;
    }

    public void setDoublePawnMove(boolean doublePawnMove) {
        this.doublePawnMove = doublePawnMove;
    }

    public String toString() {
        return "fromX : " + getFromX() +
                " fromY : " + getFromY() +
                " toX : " + getToX() +
                " toY : " + getToY();
    }
}