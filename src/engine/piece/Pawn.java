package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import chess.ChessView;

public class Pawn extends FirstMovePiece {
    private boolean isEnPassant;
    private boolean doublePawnMove;
    private boolean isPromotion;

    public Pawn(PlayerColor color, Position pos) {
        super(color,pos);
        this.isEnPassant = false;
        this.doublePawnMove = false;
        this.isPromotion = false;
    }

    /**
     * Set the en passant flag
     * @param enPassant true if the pawn can be captured en passant
     */
    public void setEnPassant(boolean enPassant) {
        isEnPassant = enPassant;
    }

    /**
     * @return true if the pawn moved two squares
     */
    public boolean wasDoublePawnMove() {
        return doublePawnMove;
    }

    /**
     * Set the double pawn move flag
     * @param doublePawnMove true if the pawn moved two squares
     */
    public void setDoublePawnMove(boolean doublePawnMove) {
        this.doublePawnMove = doublePawnMove;
    }

    /**
     * Set the promotion flag
     * @param promotion true if the pawn is at the end of the board
     */
    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    /**
     * Check if pawn is at the end of the board
     * @param to the position of the pawn
     */
    private void setPromotionIfEndOfBoard(Position to) {
        if (color() == PlayerColor.WHITE && to.y() == 7 ||
            color() == PlayerColor.BLACK && to.y() == 0)
            setPromotion(true);
    }

    @Override
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {return PieceType.PAWN;}

    /**
     * We want this method to call wasDoublePawnMove() only if it's a Pawn.
     * Every piece can call this method, but only Pawn has the method wasDoublePawnMove().
     * @return true if the pawn can be captured en passant
     */
    @Override
    protected boolean canBeCapturedEnPassant() {
        return wasDoublePawnMove();
    }

    /**
     * Valid move for a pawn is one square forward, two squares forward on first move,
     * or one square diagonally to capture an enemy piece.
     */
    @Override
    public boolean isValidMove(Move move, Board board) {
        Move lastMove = board.getLastMove();
        Position from = move.from(), to = move.to();
        Piece destination = board.getPiece(to);

        // 1 go up, -1 go down
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // Move one square
        if (destination == null &&              // empty space
            from.x() == to.x() &&               // not horizontal
            to.y() == from.y() + direction) {   // one square
            return true;
        }

        // Move two squares
        if (!hasMoved() &&                          // has not moved
            from.x() == to.x() &&                   // not horizontal
            to.y() == from.y() + 2 * direction) {   // two squares

            Position temp = new Position(from.x(), from.y() + direction);
            Piece squareInFrontPawn = board.getPiece(temp);
            Piece destinationSquare = board.getPiece(to);

            // both square in front of pawn are empty
            return squareInFrontPawn == null && destinationSquare == null;
        }

        // Capture diagonal piece
        if ((to.x() == from.x() + 1 || to.x() == from.x() - 1) &&   // one square to left or right
            to.y() == from.y() + direction) {                       // one forward

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

    /**
     * Execute the pawn move
     * @param move the move to execute
     * @param board the board to execute the move on
     * @param view the view to update
     */
    @Override
    public void executeMove(Move move, Board board, ChessView view) {
        Position from = move.from();
        Position to   = move.to();
        int direction = (color() == PlayerColor.WHITE) ? 1 : -1;

        // if two-square move
        setDoublePawnMove(!hasMoved() && Math.abs(to.y() - from.y()) == 2);

        // basic movement on board
        board.movePiece(move);
        view.removePiece(move.from().x(), move.from().y());
        view.putPiece(this.type(), this.color, move.to().x(), move.to().y());

        // handle en passant
        boolean enPassantCapture = false;
        if ((to.x() == from.x() + 1 || to.x() == from.x() - 1) && board.getPiece(to) == this) {
            // captured piece is behind "to"
            Position behindTo = new Position(to.x(), to.y() - direction);

            // en passant is valid, remove captured pawn
            Piece behindPiece = board.getPiece(behindTo);
            if (behindPiece != null && behindPiece.color() != this.color) {
                enPassantCapture = true;
                board.removePiece(behindTo);
                view.removePiece(behindTo.x(), behindTo.y());
            }
        }

        // set en passant flag
        setEnPassant(enPassantCapture);
        // check if pawn is at the end of the board and set promotion flag if true
        setPromotionIfEndOfBoard(to);

        // handle promotion
        if (isPromotion) {
            // ask user
            Piece userChoice = null;
            while(userChoice == null) {
                userChoice = view.askUser(
                        "Promotion",
                        "Choose new piece to promote to:",
                        new Queen(color(), move.to()),
                        new Bishop(color(), move.to()),
                        new Knight(color(), move.to()),
                        new Rook(color(), move.to())
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
