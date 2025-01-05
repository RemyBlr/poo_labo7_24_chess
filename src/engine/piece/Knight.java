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
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {
        return PieceType.KNIGHT;
    }

    /**
     * Valid move for a knight is an L move (2 squares in one direction and 1 square in another)
     */
    @Override
    public boolean isValidMove(Move move, Board board) {
        return move.isLMove();
    }
}
