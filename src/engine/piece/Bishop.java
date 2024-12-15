package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;

public class Bishop extends Piece {

    public Bishop(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.BISHOP;
    }

    private boolean isDiagonalMove(int fromX, int fromY, int toX, int toY) {
        return Math.abs(toX - fromX) == Math.abs(toY - fromY);
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {
        // Cannot go through obstacles
        if(!isDiagonalMove(fromX, fromY, toX, toY)) {
            return false;
        }

        // Check if there is a piece on the path the rook wants to go through
        int incrX = 1, incrY = 1; // Déplacement diagonal haut droite
        if(fromX > toX) incrX = -1;
        if(fromY > toY) incrY = -1;

        for(int i = fromX, j = fromY; i < toX && toY < fromY; i += incrX, j += incrY) {
            if(board.getPiece(i, j) != null) {
                return false;
            }
        }

        return true;
    }
}
