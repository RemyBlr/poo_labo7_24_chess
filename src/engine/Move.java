package engine;

public class Move {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private boolean isCastling;
    private boolean isEnPassant;
    private boolean isPromotion;
    private Piece capturedPiece;

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.isCastling = false;
        this.isEnPassant = false;
        this.isPromotion = false;
        this.capturedPiece = null;
    }
}