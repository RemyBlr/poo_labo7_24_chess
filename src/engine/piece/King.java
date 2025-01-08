package engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

public class King extends FirstMovePiece {

    public King(PlayerColor color, Position pos) {
        super(color, pos);
    }

    @Override
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    /**
     * Valid move for a king is a move of one square in any direction
     * It can also castle if the conditions are met
     */
    @Override
    public boolean isValidMove(Move move, Board board) {
        Position from = move.from(), to = move.to();

        // Roque possible
        if(canCastle(move, board)) {
            return true;
        }

        // can only move one square
        return Math.abs(to.x() - from.x()) <= 1 && Math.abs(to.y() - from.y()) <= 1;
    }

    /**
     * Execute the king move
     * @param move the move to execute
     * @param board the board to execute the move on
     * @param view the view to update
     */
    @Override
    public void executeMove(Move move, Board board, ChessView view) {

        // check if castling move
        if (canCastle(move, board)) {

            // move king
            board.movePiece(move);
            view.removePiece(move.from().x(), move.from().y());
            view.putPiece(this.type(), this.color, move.to().x(), move.to().y());

            // move rook
            boolean isKingSide = (move.to().x() - move.from().x()) == 2;

            if(isKingSide) {
                // small castling
                Move rookMove = new Move(
                        new Position(7, move.from().y()),
                        new Position(5, move.from().y())
                );
                board.movePiece(rookMove);
                view.removePiece(7, move.from().y());
                view.putPiece(PieceType.ROOK, this.color, 5, move.from().y());
            } else {
                // queen side castling
                Move rookMove = new Move(
                        new Position(0, move.from().y()),
                        new Position(3, move.from().y())
                );
                board.movePiece(rookMove);
                view.removePiece(0, move.from().y());
                view.putPiece(PieceType.ROOK, this.color, 3, move.from().y());
            }
        } else {
            // normal king move
            super.executeMove(move, board, view);
        }
    }

    /**
     * Get the piece that checks the king
     * @param board the board to check
     * @return the piece that checks the king
     */
    public Piece getCheckerPiece(Board board) {
        Piece piece;
        for(int x = 0; x < Board.BOARD_SIZE; x++) {
            for(int y = 0; y < Board.BOARD_SIZE; y++) {
                piece = board.getPiece(new Position(x,y));
                if(piece == null || piece.color() == this.color) continue;
                if(!piece.isValidMove(new Move(piece.pos(), pos), board)) continue;
                return piece;
            }
        }
        return null;
    }

    /**
     * Check if the king is checked
     * @param board the board to check
     * @return true if the king is checked, false otherwise
     */
    public boolean isChecked(Board board) {

        return getCheckerPiece(board) != null;
    }

    /**
     * Check if the king can castle
     * @param move the move to check
     * @param board the board to check
     * @return true if the king can castle, false otherwise
     */
    @Override
    protected boolean canCastle(Move move, Board board) {
        Position from = move.from(), to = move.to();
        boolean small = to.x() - from.x() == 2;

        if (!this.hasMoved && Math.abs(to.x() - from.x()) == 2 && from.y() == to.y()) {
            Piece rook = small ? board.getPiece(new Position(7, from.y())) : board.getPiece(new Position(0, from.y()));
            if (rook != null && rook.canCastle(move, board)) {
                return true;
            }
        }
        return false;
    }
}
