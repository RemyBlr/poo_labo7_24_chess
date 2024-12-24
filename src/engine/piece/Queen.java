package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.MoveUtils;
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

    public boolean isValidMove(Move move, Board board, Move lastMove) {

        if(!MoveUtils.isDiagonalMove(move) && !MoveUtils.isStraightMove(move)) return false;

        if(!MoveUtils.isClearPathStraight(move, board)) return false;

        return true;
    }
}
