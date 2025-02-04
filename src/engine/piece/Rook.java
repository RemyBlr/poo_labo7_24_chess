package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Rook extends FirstMovePiece {

    public Rook(PlayerColor color, Position pos) {
        super(color,pos);
    }

    @Override
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.ROOK;}

    /**
     * Valid move for a rook is a straight move that does not go through obstacles
     */
    @Override
    public boolean isValidMove(Move move, Board board) {
        return move.isClearPathStraight(board) && move.isStraightMove();
    }

    @Override
    protected boolean canCastle(Move move, Board board) {
        return !this.hasMoved;
    }
}
