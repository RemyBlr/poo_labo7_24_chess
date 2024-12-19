package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Queen extends Piece {

    public Queen(PlayerColor color, Position pos) {
        super(color, pos);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.QUEEN;
    }

    private boolean isDiagonalMove(Move move) {
        Position from = move.from(), to = move.to();
        return Math.abs(to.x() - from.x()) == Math.abs(to.y() - from.y());
    }

    private boolean isStraightMove(Move move) {
        Position from = move.from(), to = move.to();
        return from.x() == to.x() || from.y() == to.y();
    }

    private boolean isClearPath(Move move, Board board) {
        Position from = move.from(), to = move.to();
        int incrX = Integer.compare(to.x(), from.x());
        int incrY = Integer.compare(to.y(), from.y());

        for (int x = from.x() + incrX, y = from.y() + incrY; x != to.x() || y != to.y(); x += incrX, y += incrY) {
            if (board.getPiece(new Position(x, y)) != null) return false;
        }

        return true;
    }

    public boolean isValidMove(Move move, Board board, Move lastMove) {
        Position from = move.from(), to = move.to();

        if(!isDiagonalMove(move) && !isStraightMove(move)) {
            return false;
        }

        if(!isClearPath(move, board))
            return false;

        return true;
    }
}
