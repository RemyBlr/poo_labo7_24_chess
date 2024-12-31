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

    public void setEnPassant(boolean enPassant) {
        isEnPassant = enPassant;
    }

    public boolean wasDoublePawnMove() {
        return doublePawnMove;
    }

    public void setDoublePawnMove(boolean doublePawnMove) {
        this.doublePawnMove = doublePawnMove;
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
        Piece destination = board.getPiece(to);

        // 1 go up, -1 go down
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // Move one square
        if (destination == null &&      // empty space
                from.x() == to.x() &&   // not horizontal
                to.y() == from.y() + direction) {  // one square
            return true;
        }

        // Move two squares
        if (!hasMoved() &&         // has not moved
                from.x() == to.x() &&          // not horizontal
                to.y() == from.y() + 2 * direction) {  // two squares

            Position temp = new Position(from.x(), from.y() + direction);
            Piece squareInFrontPawn = board.getPiece(temp);
            Piece destinationSquare = board.getPiece(to);

            // both square in front of pawn are empty
            return squareInFrontPawn == null && destinationSquare == null;
        }

        // Capture diagonal piece
        if ((to.x() == from.x() + 1 || to.x() == from.x() - 1) &&  // one square to left or right
                to.y() == from.y() + direction) {  // one forward

            // true if enemy piece and not empty
            if (destination != null && destination.color() != color()) {
                return true;
            }

            // en passant
            if(lastMove != null) {

                Piece lastMoved = board.getPiece(lastMove.to());

                // last move from enemy pawn has to be 2 squares and land right next to our pawn
                if (board.getPiece(to) == null &&
                        lastMoved.canBeCapturedEnPassant() &&
                        lastMoved.color() != color()){

                    // must be next to us
                    // check if lastMove.to() is exactly behind the "to" spot
                    return lastMove.to().x() == to.x() && lastMove.to().y() == from.y();
                }
            }
        }

        return false;
    }

    @Override
    public void executeMove(Move move, Board board, ChessView view, Move lastMove) {
        Position from = move.from();
        Position to   = move.to();
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // if two-square move
        setDoublePawnMove(!hasMoved() && Math.abs(to.y() - from.y()) == 2);

        // basic movement on board
        board.movePiece(move);
        view.removePiece(move.from().x(), move.from().y());
        view.putPiece(this.type(), this.color(), move.to().x(), move.to().y());

        // handle en passant
        boolean enPassantCapture = false;
        if ((to.x() == from.x() + 1 || to.x() == from.x() - 1) && board.getPiece(to) == this) {
            // captured piece is behind "to"
            Position behindTo = new Position(to.x(), to.y() - direction);

            // en passant is valid, remove captured pawn
            Piece behindPiece = board.getPiece(behindTo);
            if (behindPiece != null && behindPiece.color() != this.color()) {
                enPassantCapture = true;
                board.removePiece(behindTo);
                view.removePiece(behindTo.x(), behindTo.y());
            }
        }
        setEnPassant(enPassantCapture);

        checkPromotion(to);

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
        // has moved in executeMove and not in isValidMove anymore
        setHasMoved();
    }
}
