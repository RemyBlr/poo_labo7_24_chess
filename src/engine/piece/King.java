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

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        //samePosition deja pris en charge
        return !(Math.abs(toX - fromX) > 1 || Math.abs(toY - fromY) > 1);
    }
}
