package engine.piece;

public abstract interface straightMovement {
    abstract boolean isStraightMove(int fromX, int fromY, int toX, int toY);
}
