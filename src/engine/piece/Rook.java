package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }
}
