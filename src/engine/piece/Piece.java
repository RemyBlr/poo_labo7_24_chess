package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import chess.ChessView;

/**
 * Represents a chess piece.
 */
public abstract class Piece implements ChessView.UserChoice{
    PlayerColor color;
    Position pos;

    public Piece(PlayerColor color, Position pos) {
        this.color = color;
        this.pos = pos;
    }

    /**
     * @return the position of the piece
     */
    public Position pos() { return this.pos; }

    /**
     * Set the position of the piece
     * @param to the new position
     */
    public void setPos(Position to) {
        this.pos = to;
    }

    /**
     * @return the color of the piece
     */
    public PlayerColor color() {return color;}

    /**
     * @return the type of the piece
     */
    public abstract PieceType type();

    /**
     * Check if the move is valid for the piece.
     * @param move the move to be checked
     * @param board the board on which the move is to be executed
     * @return true if the move is valid, false otherwise
     */
    public abstract boolean isValidMove(Move move, Board board);

    /**
     * Execute the move on the board and update the view.
     * @param move the move to be executed
     * @param board the board on which the move is to be executed
     * @param view the view to be updated
     */
    public void executeMove(Move move, Board board, ChessView view) {
        // default move
        board.movePiece(move);

        // update view
        view.removePiece(move.from().x(), move.from().y());
        view.putPiece(this.type(), this.color(), move.to().x(), move.to().y());
    }

    /**
     * Called right after the piece has executed a valid move on the board.
     * Default implementation does nothing.
     */
    public void afterMove() {}

    /**
     * Flag to check if the piece can be captured en passant.
     * @return true if the piece can be captured en passant, false otherwise
     */
    public boolean canBeCapturedEnPassant() {
        return false; // every piece except pawn
    }
}
