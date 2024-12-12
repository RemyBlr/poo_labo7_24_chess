package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {

    public Bishop(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.BISHOP;
    }
}
