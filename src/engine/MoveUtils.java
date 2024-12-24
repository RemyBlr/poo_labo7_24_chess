package engine;

public class MoveUtils {
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
            if (board.getPiece(new Position(x, y)) != null) return false;
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
            if (board.getPiece(new Position(x, y)) != null) {
                return false;
            }
        }
        return true;
    }
}
