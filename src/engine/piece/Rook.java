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

    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {

        // Cannot go if obstacles
        for(int i = fromX, j = fromY; i < toX && toY < fromY; ++i, ++j) {
//            if( != null) {
//
//            }
        }

        return true;
    }
}
