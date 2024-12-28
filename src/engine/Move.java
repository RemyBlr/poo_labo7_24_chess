package engine;

public class Move {
    private Position from, to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public Move inverse()
    {
        return new Move(to, from);
    }

    public String toString() {
        return "fromX : " + from.x() +
                " fromY : " + from.y() +
                " toX : " + to.x() +
                " toY : " + to.y();
    }

    public static boolean isDiagonalMove(Move move) {
        Position from = move.from(), to = move.to();
        return Math.abs(to.x() - from.x()) == Math.abs(to.y() - from.y());
    }

    public static boolean isStraightMove(Move move) {
        return move.from().x() == move.to().x() || move.from().y() == move.to().y();
    }

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

    public static boolean isLMove(Move move) {
        return (Math.abs(move.to().x() - move.from().x()) == 2 && Math.abs(move.to().y() - move.from().y()) == 1) || (Math.abs(move.to().x() - move.from().x()) == 1 && Math.abs(move.to().y() - move.from().y()) == 2);
    }
}