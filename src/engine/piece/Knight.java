package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Knight extends Piece {

    public Knight(PlayerColor color, Position pos) {
        super(color, pos);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.KNIGHT;
    }

    // Check if the move is a L move
    private boolean isLMove(Position from, Position to) {
        return (Math.abs(to.x() - from.x()) == 2 && Math.abs(to.y() - from.y()) == 1) || (Math.abs(to.x() - from.x()) == 1 && Math.abs(to.y() - from.y()) == 2);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Board board, Move lastMove) {
        if(!isLMove(from, to)) {
            return false;
        }
        return true;
    }
}
