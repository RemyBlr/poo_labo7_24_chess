package engine.piece;

import chess.PlayerColor;
import engine.Board;
import engine.Position;

/*
...
 */
public abstract class MovableOncePiece extends Piece {

    private boolean hasMoved;

    public MovableOncePiece(PlayerColor color, Position pos) {
        super(color, pos);
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
