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
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.BISHOP;}

    /**
     * Valid move for a bishop is a diagonal move that does not go through obstacles
     */
    @Override
    public boolean isValidMove(Move move, Board board) {
        return Move.isClearPathDiagonal(move, board) &&
                Move.isDiagonalMove(move);
    }
}
