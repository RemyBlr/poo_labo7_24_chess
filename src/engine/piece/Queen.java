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
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.QUEEN;}

    /**
     * Valid move for a queen is a straight or diagonal move that does not go through obstacles
     */
    @Override
    public boolean isValidMove(Move move, Board board) {
        if (move.isStraightMove()) {
            return move.isClearPathStraight(board);
        }
        if (move.isDiagonalMove()) {
            return move.isClearPathDiagonal(board);
        }
        return false;
    }
}
