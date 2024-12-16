package engine.piece;

import chess.PlayerColor;
import engine.Board;

/*
...
 */
public abstract class MovableOncePiece extends Piece {

    private boolean hasMoved;

    public MovableOncePiece(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    public boolean hasMadeFirstMove() {
        return hasMoved;
    }

    public void setHasMoved(boolean state) {
        hasMoved = state;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
