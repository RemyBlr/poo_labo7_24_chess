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
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.QUEEN;}

    public boolean isValidMove(Move move, Board board) {

        if(!MoveUtils.isDiagonalMove(move) && !MoveUtils.isStraightMove(move)) return false;

        if(!MoveUtils.isClearPathStraight(move, board)) return false;

        return true;
    }
}
