package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }

    private boolean isStraightMove(int fromX, int fromY, int toX, int toY) {
        return fromX == toX || fromY == toY;
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {
        // Cannot go through obstacles
        if(!isStraightMove(fromX, fromY, toX, toY)) return false;

        // Check if there is a piece on the path the rook wants to go through
        int incrX = Integer.compare(toX, fromX);
        int incrY = Integer.compare(toY, fromY);

        for (int x = fromX + incrX, y = fromY + incrY; x != toX || y != toY; x += incrX, y += incrY) {
            if (board.getPiece(x, y) != null) return false;
        }
        return true;
    }
}
