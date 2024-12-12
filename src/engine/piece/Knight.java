package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {

    public Knight(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.KNIGHT;
    }
}
