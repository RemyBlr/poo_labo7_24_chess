package engine.piece;

import chess.PlayerColor;
import engine.Position;

/**
 * Represents pieces that move differently on their first move.
 */
public abstract class FirstMovePiece extends Piece {
    private boolean hasMoved;

    public FirstMovePiece(PlayerColor color, Position pos) {
        super(color, pos);
    }

    /**
     * Set the hasMoved flag to true.
     */
    public void setHasMoved() {
        hasMoved = true;
    }

    /**
     * @return true if the piece has moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Called after the piece has moved to update the hasMoved flag.
     * We want to call setHasMoved() only if it's a FirstMovePiece.
     */
    @Override
    public void afterMove() {
        setHasMoved();
    }
}