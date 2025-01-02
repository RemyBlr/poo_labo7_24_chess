package engine;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coordinate
     */
    public int x() {
        return x;
    }

    /**
     * @return the y coordinate
     */
    public int y() {
        return y;
    }

    /**
     * Set the x coordinate
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate
     * @param y the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Check if the position is inside the board
     * @return true if the position is inside the board, false otherwise
     */
    public boolean isInsideBoard() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    /**
     * Add two positions
     * @param other the other position to add
     * @return the sum of the two positions
     */
    public Position add(Position other){
        return new Position( this.x + other.x(), this.y + other.y());
    }

    /**
     * Multiply a position by a scalar
     * @param scalar the scalar to multiply
     * @return the position multiplied by the scalar
     */
    public Position mul(int scalar){
        return new Position( this.x * scalar, this.y * scalar);
    }

    /**
     * Get the direction from this position to another position
     * @param other the other position
     * @return the direction from this position to the other position
     */
    public Position directionTo(Position other){
        int dx = Integer.compare(other.x(), this.x); // can be -1, 0 ou 1
        int dy = Integer.compare(other.y(), this.y); // can be -1, 0 ou 1
        return new Position(dx, dy);
    }

    /**
     * Get the distance between this position and another position
     * @param other the other position
     * @return the distance between this position and the other position
     */
    public int distance(Position other) {
        return Math.max(Math.abs(other.x() - x),Math.abs(other.y() - y));
    }

    /**
     * For testing purposes
     * @return a string representation of the position
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
