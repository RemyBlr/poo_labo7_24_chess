package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.MoveUtils;
import engine.Position;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color, Position pos) {
        super(color,pos);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }

    public boolean isValidMove(Move move, Board board, Move lastMove) {
        // Cannot go through obstacles
        if(!MoveUtils.isStraightMove(move)) return false;

        return MoveUtils.isClearPathStraight(move, board);
    }
}
