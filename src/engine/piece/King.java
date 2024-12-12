package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.MovableOncePiece;

public class King extends MovableOncePiece {

    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }
}
