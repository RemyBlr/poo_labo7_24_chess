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

    // Check if the move is a L move
    private boolean isLMove(Move move) {
        return (Math.abs(move.to().x() - move.from().x()) == 2 && Math.abs(move.to().y() - move.from().y()) == 1) || (Math.abs(move.to().x() - move.from().x()) == 1 && Math.abs(move.to().y() - move.from().y()) == 2);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        if(!isLMove(move)) {
            return false;
        }
        return true;
    }
}
