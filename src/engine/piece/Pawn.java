package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends MovableOncePiece {

    public Pawn(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }
}
