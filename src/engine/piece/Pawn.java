package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Pawn extends MovableOncePiece {

    public Pawn(PlayerColor color, Position pos) {
        super(color,pos);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(Position from, Position to, Board board, Move lastMove) {
        // 1 go up, -1 go down
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // Move one square
        if (board.getPiece(to) == null &&  // empty space
                from.x() == to.x() &&          // not horizontal
                to.y() == from.y() + direction) {  // one square
            setHasMoved(true);
            return true;
        }

        // Move two squares
        if (!hasMadeFirstMove() &&         // has not moved
                from.x() == to.x() &&          // not horizontal
                to.y() == from.y() + 2 * direction) {  // two squares

            Position temp = new Position(from.x(), from.y() + direction);
            Piece squareInFrontPawn = board.getPiece(temp);
            Piece destinationSquare = board.getPiece(to);

            setHasMoved(true);

            // both square in front of pawn are empty
            return squareInFrontPawn == null && destinationSquare == null;
        }

        // Capture diagonal piece
        if ((to.x() == from.x() + 1 || to.x() == from.x() - 1) &&  // one square to left or right
                to.y() == from.y() + direction) {  // one forward

            // true if enemy piece and not empty
            if (board.getPiece(to) != null && board.getPiece(to).color() != color()) {
                return true;
            }

            // en passant
            // true if pawn next to landing position
            boolean hasEnemyPawnNext = board.getPiece(new Position(lastMove.getToX() - 1, lastMove.getToY())) != null ||
                    board.getPiece(new Position(lastMove.getToX() + 1, lastMove.getToY())) != null;

            // last move from enemy pawn has to be 2 squares and land right next to our pawn
            if (board.getPiece(to) == null &&
                    lastMove.wasDoublePawnMove() &&
                    hasEnemyPawnNext) {

                Piece moved = board.getPiece(new Position(lastMove.getToX(), lastMove.getToY()));
                lastMove.setEnPassant(true);
                // enemy pawn
                return moved.type() == PieceType.PAWN && moved.color() != color() && lastMove.getToX() == to.x() && lastMove.getToY() == from.y();
            }
        }

        return false;
    }
}
