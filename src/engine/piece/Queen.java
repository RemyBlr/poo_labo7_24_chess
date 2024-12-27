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

    public boolean isValidMove(Move move, Board board) {
        return Move.isClearPathDiagonal(move, board) && Move.isStraightMove(move) ||
        Move.isClearPathStraight(move, board) && Move.isDiagonalMove(move);
    }
}
