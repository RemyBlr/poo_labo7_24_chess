package engine;

public class Move {
    private Position from, to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    /**
     * @return the starting position of the move
     */
    public Position from() {
        return from;
    }

    /**
     * @return the ending position of the move
     */
    public Position to() {
        return to;
    }

    /**
     * @return the move in the opposite direction
     */
    public Move inverse()
    {
        return new Move(to, from);
    }

    /**
     * @return a string representation of the move
     */
    public String toString() {
        return "fromX : " + from.x() +
                " fromY : " + from.y() +
                " toX : " + to.x() +
                " toY : " + to.y();
    }

    /**
     * Check if a move is a diagonal move
     * @param move the move to check
     * @return true if the move is a diagonal move, false otherwise
     */
    public static boolean isDiagonalMove(Move move) {
        Position from = move.from(), to = move.to();
        return Math.abs(to.x() - from.x()) == Math.abs(to.y() - from.y());
    }

    /**
     * Check if a move is a straight move
     * @param move the move to check
     * @return true if the move is a straight move, false otherwise
     */
    public static boolean isStraightMove(Move move) {
        return move.from().x() == move.to().x() || move.from().y() == move.to().y();
    }

    /**
     * Check if the path between two positions is clear on a straight line
     * @param move the move to check
     * @param board the board on which the move is to be executed
     * @return true if the path is clear, false otherwise
     */
    public static boolean isClearPathStraight(Move move, Board board) {
        Position from = move.from(), to = move.to();
        int incrX = Integer.compare(to.x(), from.x());
        int incrY = Integer.compare(to.y(), from.y());

        for (int x = from.x() + incrX, y = from.y() + incrY; x != to.x() || y != to.y(); x += incrX, y += incrY) {
            Position p = new Position(x, y);
            if (!p.isInsideBoard() || board.getPiece(p) != null) return false;
        }
        return true;
    }

    /**
     * Check if the path between two positions is clear on a diagonal line
     * @param move the move to check
     * @param board the board on which the move is to be executed
     * @return true if the path is clear, false otherwise
     */
    public static boolean isClearPathDiagonal(Move move, Board board) {
        Position from = move.from(), to = move.to();

        // Check if there is a piece on the path the bishop wants to go through
        int incrX = 1, incrY = 1; // Diagonal move up-right
        if (from.x() > to.x()) incrX = -1; // left
        if (from.y() > to.y()) incrY = -1; // down

        for (int x = from.x() + incrX, y = from.y() + incrY; x != to.x() && y != to.y(); x += incrX, y += incrY) {
            Position p = new Position(x, y);
            if (!p.isInsideBoard() || board.getPiece(p) != null) return false;
        }
        return true;
    }

    /**
     * Check if a move is an L move
     * @param move the move to check
     * @return true if the move is an L move, false otherwise
     */
    public static boolean isLMove(Move move) {
        return (Math.abs(move.to().x() - move.from().x()) == 2 && Math.abs(move.to().y() - move.from().y()) == 1) || (Math.abs(move.to().x() - move.from().x()) == 1 && Math.abs(move.to().y() - move.from().y()) == 2);
    }
}