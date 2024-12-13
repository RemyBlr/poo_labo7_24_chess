package engine.piece;

import chess.PlayerColor;

/*
...
 */
public abstract class MovableOncePiece extends Piece {

    private boolean hasMoved;
    
    public MovableOncePiece(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
