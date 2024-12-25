package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color, Position pos) {
        super(color,pos);
    }

    @Override
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.ROOK;}

    public boolean isValidMove(Move move, Board board) {
        // Cannot go through obstacles
        return Move.isClearPathStraight(move, board) &&
                Move.isStraightMove(move);
    }
}
