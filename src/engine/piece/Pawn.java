package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class Pawn extends MovableOncePiece {
    private boolean isEnPassant;
    private boolean doublePawnMove;
    private boolean isPromotion;

    public Pawn(PlayerColor color, Position pos) {
        super(color,pos);
        this.isEnPassant = false;
        this.doublePawnMove = false;
        this.isPromotion = false;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public void setEnPassant(boolean enPassant) {
        isEnPassant = enPassant;
    }

    public boolean wasDoublePawnMove() {
        return doublePawnMove;
    }

    public void setDoublePawnMove(boolean doublePawnMove) {
        this.doublePawnMove = doublePawnMove;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    /**
     * Check if pawn is at the end of the board
     */
    public void checkPromotion(Position to) {
        if (color() == PlayerColor.WHITE && to.y() == 7 ||
            color() == PlayerColor.BLACK && to.y() == 0)
            setPromotion(true);
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    //TODO
    @Override
    public boolean isValidMove(Move move, Board board, Move lastMove) {
        Position from = move.from(), to = move.to();

        // 1 go up, -1 go down
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // Move one square
        if (board.getPiece(to) == null &&  // empty space
                from.x() == to.x() &&          // not horizontal
                to.y() == from.y() + direction) {  // one square
            checkPromotion(to); // could be promotion on normal move
            setHasMoved();
            return true;
        }

        // Move two squares
        if (!hasMoved() &&         // has not moved
                from.x() == to.x() &&          // not horizontal
                to.y() == from.y() + 2 * direction) {  // two squares

            Position temp = new Position(from.x(), from.y() + direction);
            Piece squareInFrontPawn = board.getPiece(temp);
            Piece destinationSquare = board.getPiece(to);

            setHasMoved();
            setDoublePawnMove(true);

            // both square in front of pawn are empty
            return squareInFrontPawn == null && destinationSquare == null;
        }

        // Capture diagonal piece
        if ((to.x() == from.x() + 1 || to.x() == from.x() - 1) &&  // one square to left or right
                to.y() == from.y() + direction) {  // one forward

            // true if enemy piece and not empty
            if (board.getPiece(to) != null && board.getPiece(to).color() != color()) {
                checkPromotion(to); // could be promotion on capture
                return true;
            }

            // en passant
            // true if pawn next to landing position
            if(lastMove != null || lastMove.to() != null && lastMove.from() != null) { // Ajout check null

                boolean hasEnemyPawnNext = board.getPiece(new Position(lastMove.to().x() - 1, lastMove.to().y())) != null ||
                        board.getPiece(new Position(lastMove.to().x() + 1, lastMove.to().y())) != null;
                Position lastMoveFrom = lastMove.from();
                Position lastMoveTo = lastMove.to();
                Piece lastMoved = board.getPiece(lastMoveTo);

                // last move from enemy pawn has to be 2 squares and land right next to our pawn
                if (board.getPiece(to) == null &&
                        ((Pawn) lastMoved).wasDoublePawnMove() &&
                        hasEnemyPawnNext) {

                    Piece moved = board.getPiece(new Position(lastMove.to().x(), lastMove.to().y()));
                    this.setEnPassant(true);
                    // enemy pawn
                    return moved.type() == PieceType.PAWN && moved.color() != color() && lastMove.to().x() == to.x() && lastMove.to().y() == from.y();
                }
            }
        }

        return false;
    }
}
