package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.MovableOncePiece;
import engine.piece.Piece;

public class Move {
    private Position from, to;
    private Piece pieceFrom, pieceTo;

    private boolean isCastling;
    private boolean isEnPassant;
    private boolean isPromotion;
    private boolean doublePawnMove;
    private Piece capturedPiece; // Some unused attributes...

    public Move(Position from, Position to, Piece pieceFrom, Piece pieceTo) {
        this.from = from;
        this.to = to;
        this.pieceFrom = pieceFrom;
        this.pieceTo = pieceTo;
        this.isCastling = false;
        this.isEnPassant = false;
        this.isPromotion = false;
        this.doublePawnMove = false;
        this.capturedPiece = null;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
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

    // Move validation
    public boolean samePosition() {
        return from.equals(to);
    }

    public boolean pieceSelected() {
        return pieceFrom != null;
    }

    public boolean isInsideBoard() {
        return from.isInsideBoard() && to.isInsideBoard();
    }

    public boolean isPlayerTurn(PlayerColor currentPlayerColor) {
        return pieceFrom.color().equals(currentPlayerColor);
    }

    public boolean isRoque() {
        return pieceFrom.type() == PieceType.KING && pieceTo != null && pieceTo.type() == PieceType.ROOK && !((MovableOncePiece) pieceFrom).hasMoved() && !((MovableOncePiece) pieceTo).hasMoved();
    }

    public boolean isSameColor() {
        if(pieceTo == null) return false;
        return pieceFrom.color().equals(pieceTo.color());
    }

    public boolean isValidMove(Board board, Move lastMove) {
        return pieceFrom.isValidMove(this, board, lastMove);
    }

    public boolean isFinish(Board board) {
        return board.isCheckMate() || board.isStaleMate();
    }

    public String toString() {
        return "fromX : " + from.x() +
                " fromY : " + from.y() +
                " toX : " + to.x() +
                " toY : " + to.y();
    }
}