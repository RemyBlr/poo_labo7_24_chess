package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }
}
