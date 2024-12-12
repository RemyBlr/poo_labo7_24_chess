package engine.piece;

import chess.PlayerColor;

/*
...
 */
public abstract class MovableOncePiece extends Piece {

    private boolean hasMoved;
    
    public MovableOncePiece(PlayerColor color) {
        super(color);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
