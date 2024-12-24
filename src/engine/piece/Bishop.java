package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import engine.MoveUtils;

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

    public boolean isValidMove(Move move, Board board, Move lastMove) {
        // Cannot go through obstacles
        if (!MoveUtils.isDiagonalMove(move)) return false;

        return MoveUtils.isClearPathDiagonal(move, board);
    }
}
