package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends MovableOncePiece {

    public Pawn(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }
}
