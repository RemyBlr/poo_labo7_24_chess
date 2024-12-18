package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Bishop extends Piece {

    public Bishop(PlayerColor color, Position pos) {
        super(color, pos);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.BISHOP;
    }

    private boolean isDiagonalMove(Move move) {
        Position from = move.from(), to = move.to();
        return Math.abs(to.x() - from.x()) == Math.abs(to.y() - from.y());
    }

    public boolean isValidMove(Move move, Board board, Move lastMove) {
        // Cannot go through obstacles
        if (!isDiagonalMove(move)) {
            return false;
        }

        Position from = move.from(), to = move.to();

        // Check if there is a piece on the path the bishop wants to go through
        int incrX = 1, incrY = 1; // Diagonal move up-right
        if (from.x() > to.x()) incrX = -1; // left
        if (from.y() > to.y()) incrY = -1; // down

        for (int x = from.x() + incrX, y = from.y() + incrY; x != to.x() && y != to.y(); x += incrX, y += incrY) {
            if (board.getPiece(new Position(x, y)) != null) {
                return false;
            }
        }

        return true;
    }
}
