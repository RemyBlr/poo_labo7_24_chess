package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color, Position pos) {
        super(color,pos);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }

    private boolean isStraightMove(Position from, Position to) {
        return from.x() == to.x() || from.y() == to.y();
    }

    public boolean isValidMove(Position from, Position to, Board board, Move lastMove) {
        // Cannot go through obstacles
        if(!isStraightMove(from, to)) return false;

        // Check if there is a piece on the path the rook wants to go through
        int incrX = Integer.compare(to.x(), from.x());
        int incrY = Integer.compare(to.y(), from.y());

        for (int x = from.x() + incrX, y = from.y() + incrY; x != to.x() || y != to.y(); x += incrX, y += incrY) {
            if (board.getPiece(new Position(x, y)) != null) return false;
        }
        return true;
    }
}
