package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import chess.ChessView;

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
    private void checkPromotion(Position to) {
        if (color() == PlayerColor.WHITE && to.y() == 7 ||
            color() == PlayerColor.BLACK && to.y() == 0)
            setPromotion(true);
    }

    private Piece getPieceIfInside(Board board, int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        return board.getPiece(new Position(x, y));
    }

    @Override
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.PAWN;}

    @Override
    public boolean canBeCapturedEnPassant() {
        // after promotion, will return false
        return wasDoublePawnMove();
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        Move lastMove = board.getLastMove();
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
            if(lastMove != null) {

                Piece lastMoved = board.getPiece(lastMove.to());

                // true if pawn next to landing position
                //boolean hasEnemyPawnNext = board.getPiece(new Position(lastMove.to().x() - 1, lastMove.to().y())) != null ||
                //        board.getPiece(new Position(lastMove.to().x() + 1, lastMove.to().y())) != null;
                Piece leftPiece = getPieceIfInside(board, lastMove.to().x() - 1, lastMove.to().y());
                Piece rightPiece = getPieceIfInside(board, lastMove.to().x() + 1, lastMove.to().y());
                boolean hasEnemyPawnNext = (leftPiece != null) || (rightPiece != null);

                // last move from enemy pawn has to be 2 squares and land right next to our pawn
                if (board.getPiece(to) == null &&
                        lastMoved.canBeCapturedEnPassant() &&
                        hasEnemyPawnNext) {

                    Piece moved = board.getPiece(new Position(lastMove.to().x(), lastMove.to().y()));
                    setEnPassant(true);
                    // enemy pawn
                    return moved.type() == PieceType.PAWN && moved.color() != color() && lastMove.to().x() == to.x() && lastMove.to().y() == from.y();
                }
            }
        }

        return false;
    }

    @Override
    public void executeMove(Move move, Board board, ChessView view, Move lastMove) {

        // basic movement on board
        board.movePiece(move);
        view.removePiece(move.from().x(), move.from().y());
        view.putPiece(this.type(), this.color(), move.to().x(), move.to().y());

        // handle en passant
        if (isEnPassant) {
            int direction = (color() == PlayerColor.WHITE) ? 1 : -1;
            Position capturedPos = new Position(move.to().x(), move.to().y() - direction);

            board.removePiece(capturedPos);
            view.removePiece(capturedPos.x(), capturedPos.y());

            setEnPassant(false); // reset
        }

        // handle promotion
        if (isPromotion) {
            // ask user
            Piece userChoice = null;
            while(userChoice == null) {
                userChoice = view.askUser(
                        "Promotion",
                        "Choose a piece to promote to:",
                        new Bishop(color(), move.to()),
                        new Knight(color(), move.to()),
                        new Rook(color(), move.to()),
                        new Queen(color(), move.to())
                );
            }

            // remove pawn from board
            board.removePiece(move.to());
            view.removePiece(move.to().x(), move.to().y());

            // place new piece
            board.setPiece(userChoice, move.to());
            view.putPiece(userChoice.type(), userChoice.color(), move.to().x(), move.to().y());
        }
    }
}
