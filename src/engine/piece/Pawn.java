package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public class Pawn extends MovableOncePiece {

    public Pawn(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board) {
        // 1 goes up, -1 goes down
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // Move one square
        if(board.getPiece(toX, toY) == null &&  // empty space
            fromX == toX &&                     // not horizontal
            toY == fromY + direction) {         // one square
            setHasMoved(true);
            return true;
        }

        // Move two squares
        if(!hasMadeFirstMove() &&           // has not moved
            fromX == toX &&                 // not horizontal
            toY == fromY + 2*direction) {   // two squares

            Piece squareInFrontPawn = board.getPiece(fromX, fromY + direction);
            Piece destinationSquare = board.getPiece(toX, toY);

            setHasMoved(true);

            // both square in front of pawn are empty
            return squareInFrontPawn == null && destinationSquare == null;
        }

        return false;
    }
}
