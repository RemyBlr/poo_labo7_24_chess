package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;

public class Pawn extends MovableOncePiece {

    public Pawn(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {
        // 1 go up, -1 go down
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

        // Capture diagonal peice
        if((toX == fromX + 1 || toX == fromX - 1) &&    // one square to left or right
            toY == fromY + direction ) {                // one forward

            // true if enemy piece and not empty
            if(board.getPiece(toX, toY) != null && board.getPiece(toX, toY).color() != color()) {
                return true;
            }

            // en passant
            // true if pawn next to landing position
            boolean hasEnemyPawnNext = board.getPiece(lastMove.getToX() - 1, lastMove.getToY()) != null || board.getPiece(lastMove.getToX() + 1, lastMove.getToY()) != null;

            // last move from enemy pawn has to be 2 squares and land right next to our pawn
            if(board.getPiece(toX, toY) == null &&
                lastMove.wasDoublePawnMove() &&
                hasEnemyPawnNext) {

                Piece moved = board.getPiece(lastMove.getToX(), lastMove.getToY());
                lastMove.setEnPassant(true);
                // enemy pawn
               return moved.type() == PieceType.PAWN && moved.color() != color() && lastMove.getToX() == toX && lastMove.getToY() == fromY;
            }
        }

        return false;
    }
}
