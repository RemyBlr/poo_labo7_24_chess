package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece {

    public Queen(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.QUEEN;
    }
}
