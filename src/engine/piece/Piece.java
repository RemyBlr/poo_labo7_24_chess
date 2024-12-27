package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import chess.ChessView;

/*
...
 */
public abstract class Piece implements ChessView.UserChoice{
    PlayerColor color;
    Position pos;

    public Piece(PlayerColor color, Position pos) {
        this.color = color;
        this.pos = pos;
    }

    public Position pos() { return this.pos; }

    public PlayerColor color() {return color;}

    public abstract PieceType type();

    public abstract boolean isValidMove(Move move, Board board);

    public void executeMove(Move move, Board board, ChessView view, Move lastMove) {
        // default move
        board.movePiece(move);

        // update view
        view.removePiece(move.from().x(), move.from().y());
        view.putPiece(this.type(), this.color(), move.to().x(), move.to().y());
    }

    public void setPos(Position to) {
        this.pos = to;
    }

    /**
     * Called right after the piece has executed a valid move on the board.
     * Default implementation does nothing.
     */
    public void afterMove() {}

    public boolean canBeCapturedEnPassant() {
        return false; // every piece except pawn
    }
}
