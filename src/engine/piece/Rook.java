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

    private boolean isStraightMove(Move move) {
        return move.from().x() == move.to().x() || move.from().y() == move.to().y();
    }

    public boolean isValidMove(Move move, Board board, Move lastMove) {
        // Cannot go through obstacles
        if(!isStraightMove(move)) return false;

        // Check if there is a piece on the path the rook wants to go through
        int incrX = Integer.compare(move.to().x(), move.from().x());
        int incrY = Integer.compare(move.to().y(), move.from().y());

        for (int x = move.from().x() + incrX, y = move.from().y() + incrY; x != move.to().x() || y != move.to().y(); x += incrX, y += incrY) {
            if (board.getPiece(new Position(x, y)) != null) return false;
        }
        return true;
    }
}
