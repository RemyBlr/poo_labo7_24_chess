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

    @Override
    public boolean isValidMove(Move move, Board board) {
        return Move.isLMove(move);
    }
}
